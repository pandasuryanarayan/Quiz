package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardSecondary
import com.example.ui.theme.ArcadeFlame
import com.example.ui.theme.ArcadeNeonCyan
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadePurple
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextMuted

/**
 * Renders quiz logos directly from the jsDelivr CDN endpoint in the Dark Arcade showcase box.
 */
@Composable
fun LogoCard(
    logoKey: String,
    imageUrl: String? = null,
    clueText: String? = null,
    categoryName: String? = null,
    modifier: Modifier = Modifier,
    cardSize: Dp = 190.dp
) {
    var retryTrigger by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer dark arcade container with ambient border
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("quiz_logo_card"),
            shape = RoundedCornerShape(26.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                // Inner canvas container
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardSize),
                    shape = RoundedCornerShape(18.dp),
                    color = ArcadeCardSecondary
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF202036), Color(0xFF151522))
                                )
                            )
                            .padding(12.dp)
                    ) {
                        // Top badges row: "● LIVE" badge + Category pill
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ArcadeNeonGreen,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .clip(CircleShape)
                                            .background(Color.Black)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "LIVE",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp,
                                        color = Color.Black
                                    )
                                }
                            }

                            categoryName?.let { cat ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0x33FFFFFF),
                                    border = BorderStroke(0.5.dp, ArcadeBorder)
                                ) {
                                    Text(
                                        text = cat.uppercase(),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.5.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 0.8.sp,
                                        color = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        // Authentic Logo image centered
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp, vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!imageUrl.isNullOrBlank()) {
                                val context = LocalContext.current
                                val imageRequest = remember(imageUrl, retryTrigger) {
                                    ImageRequest.Builder(context)
                                        .data(imageUrl)
                                        .crossfade(true)
                                        .build()
                                }

                                SubcomposeAsyncImage(
                                    model = imageRequest,
                                    contentDescription = "Authentic Brand Logo",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize(),
                                    loading = {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(32.dp),
                                                strokeWidth = 2.5.dp,
                                                color = ArcadeNeonCyan
                                            )
                                        }
                                    },
                                    error = {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clickable { retryTrigger++ }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.BrokenImage,
                                                contentDescription = "Retry image",
                                                tint = ArcadeTextMuted,
                                                modifier = Modifier.size(36.dp)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "Tap to retry",
                                                fontSize = 11.sp,
                                                color = ArcadeTextDim,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                )
                            } else {
                                Text(
                                    text = logoKey.uppercase(),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }

                        // Bottom Clue / Hint Subtitle
                        clueText?.let { clue ->
                            Text(
                                text = clue.uppercase(),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = Color.White.copy(alpha = 0.35f),
                                maxLines = 1,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}
