package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.LevelProgressEntity
import com.example.data.PackCategory
import com.example.data.PackProgressSummary
import com.example.data.QuizDatabase
import com.example.data.QuizLevel
import com.example.data.QuizPackData
import com.example.data.QuizRepository
import com.example.data.UserProfileEntity
import com.example.ui.components.AdPurpose
import com.example.ui.components.BankTile
import com.example.ui.components.SlotItem
import com.example.ui.components.SlotState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Collections
import kotlin.random.Random

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuizRepository

    init {
        val database = QuizDatabase.getDatabase(application)
        repository = QuizRepository(database.quizDao())
        viewModelScope.launch {
            repository.initializeDefaultsIfNeeded()
        }
    }

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allProgress: StateFlow<List<LevelProgressEntity>> = repository.allProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Pack summaries computed reactively
    val packSummaries: StateFlow<List<PackProgressSummary>> = allProgress.combine(MutableStateFlow(Unit)) { progressList, _ ->
        PackCategory.entries.map { pack ->
            val packLevels = QuizPackData.getLevelsForPack(pack.id)
            val packProgress = progressList.filter { it.packId == pack.id }
            val unlockedCount = packProgress.count { it.isUnlocked }
            val completedCount = packProgress.count { it.isCompleted }
            val starsCount = packProgress.sumOf { it.stars }
            PackProgressSummary(
                pack = pack,
                totalLevels = packLevels.size,
                unlockedLevels = unlockedCount,
                completedLevels = completedCount,
                totalStars = starsCount
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation and screen state
    private val _currentPackId = MutableStateFlow<String?>(null)
    val currentPackId = _currentPackId.asStateFlow()

    private val _currentLevelId = MutableStateFlow<String?>(null)
    val currentLevelId = _currentLevelId.asStateFlow()

    // Active gameplay state
    private val _activeLevel = MutableStateFlow<QuizLevel?>(null)
    val activeLevel = _activeLevel.asStateFlow()

    private val _slots = MutableStateFlow<List<SlotItem>>(emptyList())
    val slots = _slots.asStateFlow()

    private val _bankTiles = MutableStateFlow<List<BankTile>>(emptyList())
    val bankTiles = _bankTiles.asStateFlow()

    private val _slotState = MutableStateFlow(SlotState.DEFAULT)
    val slotState = _slotState.asStateFlow()

    private val _shakeTrigger = MutableStateFlow(0)
    val shakeTrigger = _shakeTrigger.asStateFlow()

    private val _freeHintUsedOnLevel = MutableStateFlow(false)
    val freeHintUsedOnLevel = _freeHintUsedOnLevel.asStateFlow()

    // Dialogs
    private val _showHintDialog = MutableStateFlow(false)
    val showHintDialog = _showHintDialog.asStateFlow()

    private val _showAdPrompt = MutableStateFlow(false)
    val showAdPrompt = _showAdPrompt.asStateFlow()

    private val _pendingAdPurpose = MutableStateFlow(AdPurpose.UNLOCK_LEVEL)
    val pendingAdPurpose = _pendingAdPurpose.asStateFlow()

    private val _pendingUnlockLevel = MutableStateFlow<QuizLevel?>(null)
    val pendingUnlockLevel = _pendingUnlockLevel.asStateFlow()

    private val _showAdPlayer = MutableStateFlow(false)
    val showAdPlayer = _showAdPlayer.asStateFlow()

    private val _showLevelComplete = MutableStateFlow(false)
    val showLevelComplete = _showLevelComplete.asStateFlow()

    private val _advanceAfterAd = MutableStateFlow(false)

    fun promptEarnCoinsAd() {
        _pendingAdPurpose.value = AdPurpose.EARN_COINS
        _showAdPlayer.value = true
    }

    fun selectPack(packId: String?) {
        _currentPackId.value = packId
        _currentLevelId.value = null
    }

    fun openLevel(levelId: String) {
        val level = QuizPackData.getLevelById(levelId) ?: return
        val progress = allProgress.value.find { it.id == levelId }
        val isUnlocked = progress?.isUnlocked == true || level.levelNumber <= 5

        if (isUnlocked) {
            setupLevel(level)
            _currentLevelId.value = levelId
        } else {
            // Trigger Rewarded Video Ad Gate
            _pendingUnlockLevel.value = level
            _pendingAdPurpose.value = AdPurpose.UNLOCK_LEVEL
            _showAdPrompt.value = true
        }
    }

    private fun setupLevel(level: QuizLevel) {
        _activeLevel.value = level
        _slotState.value = SlotState.DEFAULT
        _freeHintUsedOnLevel.value = false
        _showLevelComplete.value = false

        // Initialize empty slots based on answer length
        val initialSlots = List(level.answer.length) { index ->
            SlotItem(index = index, char = null, bankTileId = null)
        }
        _slots.value = initialSlots

        // Prepare scrambled tile bank: answer letters + distractors to reach 12 or 14
        val targetLetters = level.answer.toList()
        val totalBankSize = if (level.answer.length >= 8) 14 else 12
        val neededDistractors = maxOf(0, totalBankSize - targetLetters.size)

        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val distractors = (1..neededDistractors).map {
            alphabet.random()
        }

        val allLetters = (targetLetters + distractors).toMutableList()
        allLetters.shuffle()

        val tiles = allLetters.mapIndexed { index, char ->
            BankTile(id = index, char = char, isUsed = false, isRemovedByHint = false)
        }
        _bankTiles.value = tiles
    }

    fun onBankTileClick(tileId: Int) {
        if (_slotState.value == SlotState.CORRECT) return

        val tiles = _bankTiles.value.toMutableList()
        val tileIndex = tiles.indexOfFirst { it.id == tileId }
        if (tileIndex == -1 || tiles[tileIndex].isUsed || tiles[tileIndex].isRemovedByHint) return

        val currentSlots = _slots.value.toMutableList()
        val firstEmptyIndex = currentSlots.indexOfFirst { it.char == null }
        if (firstEmptyIndex == -1) return

        // Place letter into slot
        val tappedChar = tiles[tileIndex].char
        currentSlots[firstEmptyIndex] = currentSlots[firstEmptyIndex].copy(
            char = tappedChar,
            bankTileId = tileId
        )
        tiles[tileIndex] = tiles[tileIndex].copy(isUsed = true)

        _slots.value = currentSlots
        _bankTiles.value = tiles
        _slotState.value = SlotState.DEFAULT

        // Check if all slots filled
        if (currentSlots.all { it.char != null }) {
            validateAnswer(currentSlots)
        }
    }

    fun onSlotClick(slotIndex: Int) {
        if (_slotState.value == SlotState.CORRECT) return

        val currentSlots = _slots.value.toMutableList()
        if (slotIndex !in currentSlots.indices) return
        val slot = currentSlots[slotIndex]
        if (slot.char == null || slot.isRevealedByHint) return

        val bankTileId = slot.bankTileId
        if (bankTileId != null) {
            val tiles = _bankTiles.value.toMutableList()
            val tileIdx = tiles.indexOfFirst { it.id == bankTileId }
            if (tileIdx != -1) {
                tiles[tileIdx] = tiles[tileIdx].copy(isUsed = false)
                _bankTiles.value = tiles
            }
        }

        currentSlots[slotIndex] = slot.copy(char = null, bankTileId = null)
        _slots.value = currentSlots
        _slotState.value = SlotState.DEFAULT
    }

    fun onShuffleBank() {
        val currentTiles = _bankTiles.value
        val unusedTiles = currentTiles.filter { !it.isUsed && !it.isRemovedByHint }.shuffled()
        var unusedIdx = 0

        val newTiles = currentTiles.map { tile ->
            if (!tile.isUsed && !tile.isRemovedByHint) {
                unusedTiles[unusedIdx++]
            } else {
                tile
            }
        }
        _bankTiles.value = newTiles
    }

    fun onClearAll() {
        if (_slotState.value == SlotState.CORRECT) return

        val currentSlots = _slots.value.toMutableList()
        val currentTiles = _bankTiles.value.toMutableList()

        currentSlots.forEachIndexed { index, slot ->
            if (!slot.isRevealedByHint && slot.bankTileId != null) {
                val tileIdx = currentTiles.indexOfFirst { it.id == slot.bankTileId }
                if (tileIdx != -1) {
                    currentTiles[tileIdx] = currentTiles[tileIdx].copy(isUsed = false)
                }
                currentSlots[index] = slot.copy(char = null, bankTileId = null)
            }
        }

        _slots.value = currentSlots
        _bankTiles.value = currentTiles
        _slotState.value = SlotState.DEFAULT
    }

    private fun validateAnswer(filledSlots: List<SlotItem>) {
        val level = _activeLevel.value ?: return
        val enteredWord = filledSlots.map { it.char }.joinToString("")

        if (enteredWord.equals(level.answer, ignoreCase = true)) {
            // Correct Answer!
            _slotState.value = SlotState.CORRECT
            viewModelScope.launch {
                repository.completeLevel(level.id, stars = 3, coinsAwarded = 50, xpAwarded = 100)
                _showLevelComplete.value = true
            }
        } else {
            // Wrong Answer
            _slotState.value = SlotState.ERROR
            _shakeTrigger.value += 1
        }
    }

    // Hint Actions
    fun openHintMenu() {
        _showHintDialog.value = true
    }

    fun closeHintMenu() {
        _showHintDialog.value = false
    }

    fun useFreeHint() {
        if (_freeHintUsedOnLevel.value) return
        revealNextCorrectLetter(isFree = true)
        _freeHintUsedOnLevel.value = true
        _showHintDialog.value = false
    }

    fun promptAdHintRemoveLetters() {
        _showHintDialog.value = false
        _pendingAdPurpose.value = AdPurpose.REMOVE_WRONG_LETTERS
        _showAdPlayer.value = true
    }

    fun promptAdHintRevealLetter() {
        _showHintDialog.value = false
        _pendingAdPurpose.value = AdPurpose.REVEAL_LETTER
        _showAdPlayer.value = true
    }

    fun useCoinHint() {
        val profile = userProfile.value ?: return
        if (profile.coins < 40) return

        viewModelScope.launch {
            val success = repository.spendCoins(40)
            if (success) {
                revealNextCorrectLetter(isFree = false)
                _showHintDialog.value = false
            }
        }
    }

    private fun revealNextCorrectLetter(isFree: Boolean) {
        val level = _activeLevel.value ?: return
        val currentSlots = _slots.value.toMutableList()
        val currentTiles = _bankTiles.value.toMutableList()

        // Find first slot that does not match correct letter
        val targetSlotIndex = currentSlots.indexOfFirst { slot ->
            slot.char != level.answer[slot.index]
        }
        if (targetSlotIndex == -1) return

        val correctChar = level.answer[targetSlotIndex]

        // Free up existing tile if any was in this slot
        val oldBankId = currentSlots[targetSlotIndex].bankTileId
        if (oldBankId != null) {
            val oldTileIdx = currentTiles.indexOfFirst { it.id == oldBankId }
            if (oldTileIdx != -1) {
                currentTiles[oldTileIdx] = currentTiles[oldTileIdx].copy(isUsed = false)
            }
        }

        // Find a matching bank tile that isn't already hint-locked
        val matchingTileIdx = currentTiles.indexOfFirst { it.char == correctChar && !it.isRemovedByHint }
        val bankId = if (matchingTileIdx != -1) {
            currentTiles[matchingTileIdx] = currentTiles[matchingTileIdx].copy(isUsed = true)
            currentTiles[matchingTileIdx].id
        } else null

        currentSlots[targetSlotIndex] = currentSlots[targetSlotIndex].copy(
            char = correctChar,
            bankTileId = bankId,
            isRevealedByHint = true
        )

        _slots.value = currentSlots
        _bankTiles.value = currentTiles
        _slotState.value = SlotState.DEFAULT

        if (currentSlots.all { it.char != null }) {
            validateAnswer(currentSlots)
        }
    }

    // Ad Flow Execution
    fun onConfirmWatchAd() {
        _showAdPrompt.value = false
        _showAdPlayer.value = true
    }

    fun onDismissAdPrompt() {
        _showAdPrompt.value = false
        _pendingUnlockLevel.value = null
    }

    fun onAdPlayerFinished(purpose: AdPurpose? = null) {
        _showAdPlayer.value = false
        val activePurpose = purpose ?: _pendingAdPurpose.value
        when (activePurpose) {
            AdPurpose.UNLOCK_LEVEL -> {
                val level = _pendingUnlockLevel.value
                if (level != null) {
                    viewModelScope.launch {
                        repository.unlockLevel(level.id)
                        setupLevel(level)
                        _currentLevelId.value = level.id
                        _pendingUnlockLevel.value = null
                    }
                }
            }
            AdPurpose.REMOVE_WRONG_LETTERS -> {
                removeWrongLetters()
            }
            AdPurpose.REVEAL_LETTER -> {
                revealNextCorrectLetter(isFree = false)
            }
            AdPurpose.EARN_COINS -> {
                viewModelScope.launch {
                    repository.rewardAdWatch(50)
                    if (_advanceAfterAd.value) {
                        _advanceAfterAd.value = false
                        goToNextLevel()
                    }
                }
            }
        }
    }

    fun onCloseAdPlayer() {
        _showAdPlayer.value = false
        if (_advanceAfterAd.value) {
            _advanceAfterAd.value = false
            goToNextLevel()
        }
    }

    private fun removeWrongLetters() {
        val level = _activeLevel.value ?: return
        val currentTiles = _bankTiles.value.toMutableList()
        val answerLetters = level.answer.toList()

        // Find tiles that are not part of the answer and not already removed
        val wrongTileIndices = currentTiles.indices.filter { idx ->
            val tile = currentTiles[idx]
            !tile.isUsed && !tile.isRemovedByHint && !answerLetters.contains(tile.char)
        }.shuffled().take(3)

        wrongTileIndices.forEach { idx ->
            currentTiles[idx] = currentTiles[idx].copy(isRemovedByHint = true)
        }
        _bankTiles.value = currentTiles
    }

    fun onDoubleCoinsAd() {
        _showLevelComplete.value = false
        _advanceAfterAd.value = true
        _pendingAdPurpose.value = AdPurpose.EARN_COINS
        _showAdPlayer.value = true
    }

    fun goToNextLevel() {
        _showLevelComplete.value = false
        val currentLvl = _activeLevel.value ?: return
        val packLevels = QuizPackData.getLevelsForPack(currentLvl.packId)
        val nextLevelNumber = currentLvl.levelNumber + 1
        val nextLevel = packLevels.find { it.levelNumber == nextLevelNumber }

        if (nextLevel != null) {
            openLevel(nextLevel.id)
        } else {
            // Return to pack grid if last level of pack
            _currentLevelId.value = null
        }
    }

    fun closeLevelToGrid() {
        _showLevelComplete.value = false
        _currentLevelId.value = null
    }
}
