package com.example.data

object QuizPackData {

    private const val CDN_BASE_URL = "https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz"

    val allLevels: List<QuizLevel> = listOf(
        // FAMOUS BRANDS PACK (User sequence: amazon, apple, google, macdonald, nike, spotify, target, tesla, zoho)
        QuizLevel(
            id = "brands_1",
            packId = "brands",
            levelNumber = 1,
            answer = "AMAZON",
            hintSentence = "E-commerce giant with an orange smile arrow linking A to Z",
            triviaFact = "Originally started in Jeff Bezos's garage in 1994 as an online bookstore before expanding to 'the everything store'.",
            logoKey = "amazon",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/amazon.webp"
        ),
        QuizLevel(
            id = "brands_2",
            packId = "brands",
            levelNumber = 2,
            answer = "APPLE",
            hintSentence = "Bitten fruit tech titan from Cupertino creating iPhone and Mac",
            triviaFact = "Apple's first logo featured Sir Isaac Newton sitting under an apple tree before the rainbow bite was created.",
            logoKey = "apple",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/apple-logo.webp"
        ),
        QuizLevel(
            id = "brands_3",
            packId = "brands",
            levelNumber = 3,
            answer = "GOOGLE",
            hintSentence = "Search titan known for primary colors and a distinctive capital G",
            triviaFact = "Google's name originated from a misspelling of 'googol', which represents the number 1 followed by 100 zeros.",
            logoKey = "google",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/google.webp"
        ),
        QuizLevel(
            id = "brands_4",
            packId = "brands",
            levelNumber = 4,
            answer = "MCDONALDS",
            hintSentence = "Fast-food empire celebrated worldwide for its Golden Arches",
            triviaFact = "The Golden Arches were originally architectural features of early walk-up franchise stands in the 1950s.",
            logoKey = "mcdonalds",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/McDonald%27s-Logo.wine.webp"
        ),
        QuizLevel(
            id = "brands_5",
            packId = "brands",
            levelNumber = 5,
            answer = "NIKE",
            hintSentence = "Athletic giant famous for the iconic Swoosh and 'Just Do It'",
            triviaFact = "The famous Nike Swoosh was created in 1971 by graphic design student Carolyn Davidson for just $35.",
            logoKey = "nike",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/nike.webp"
        ),
        QuizLevel(
            id = "brands_6",
            packId = "brands",
            levelNumber = 6,
            answer = "SPOTIFY",
            hintSentence = "Audio streaming king with three curved acoustic wave lines",
            triviaFact = "Spotify was founded in Stockholm, Sweden in 2006 by Daniel Ek and Martin Lorentzon.",
            logoKey = "spotify",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/spotify.webp"
        ),
        QuizLevel(
            id = "brands_7",
            packId = "brands",
            levelNumber = 7,
            answer = "TARGET",
            hintSentence = "Retail giant with distinctive red and white concentric circles",
            triviaFact = "Target's classic bullseye logo has over 96% brand recognition across the United States.",
            logoKey = "target",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/Target_Bullseye-Logo_Red.webp"
        ),
        QuizLevel(
            id = "brands_8",
            packId = "brands",
            levelNumber = 8,
            answer = "TESLA",
            hintSentence = "Electric vehicle and clean energy pioneer with a stylized T",
            triviaFact = "Tesla's logo isn't just a 'T'—it represents a cross-section of an electric induction motor.",
            logoKey = "tesla",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/Tesla%2C_Inc.-Logomark-Black-Logo.wine.webp"
        ),
        QuizLevel(
            id = "brands_9",
            packId = "brands",
            levelNumber = 9,
            answer = "ZOHO",
            hintSentence = "Global cloud software suite provider with colorful toy building blocks",
            triviaFact = "Founded in 1996 as AdventNet, Zoho now runs entirely on private cloud infrastructure serving over 100 million users.",
            logoKey = "zoho",
            imageUrl = "$CDN_BASE_URL/Famous%20Brands/Zoho_Corporation-Logo.wine.webp"
        ),

        // ENTERTAINMENT PACK (User sequence: disney, netflix, twitch, warner-bros, youtube)
        QuizLevel(
            id = "entertainment_1",
            packId = "entertainment",
            levelNumber = 1,
            answer = "DISNEY",
            hintSentence = "The house of magic and fairy tales with an iconic castle spire",
            triviaFact = "Walt Disney holds the individual record for the most Academy Awards won in history, totaling 22 Oscars.",
            logoKey = "disney",
            imageUrl = "$CDN_BASE_URL/Entertainment/Disney%2B-Logo.wine.webp"
        ),
        QuizLevel(
            id = "entertainment_2",
            packId = "entertainment",
            levelNumber = 2,
            answer = "NETFLIX",
            hintSentence = "Streaming giant famous for its vivid red ribbon 'N' and chime",
            triviaFact = "Netflix began in 1997 shipping DVD rentals by mail before introducing online streaming in 2007.",
            logoKey = "netflix",
            imageUrl = "$CDN_BASE_URL/Entertainment/netflix.webp"
        ),
        QuizLevel(
            id = "entertainment_3",
            packId = "entertainment",
            levelNumber = 3,
            answer = "TWITCH",
            hintSentence = "World's leading interactive live streaming service for gaming & esports",
            triviaFact = "Twitch's chat mascot is named 'Glitch', represented by the retro robotic speech bubble with two square eyes.",
            logoKey = "twitch",
            imageUrl = "$CDN_BASE_URL/Entertainment/twitch.webp"
        ),
        QuizLevel(
            id = "entertainment_4",
            packId = "entertainment",
            levelNumber = 4,
            answer = "WARNERBROS",
            hintSentence = "Century-old movie studio with a famous blue and gold shield",
            triviaFact = "Warner Bros. produced 'The Jazz Singer' in 1927, the very first full-length talking motion picture.",
            logoKey = "warner",
            imageUrl = "$CDN_BASE_URL/Entertainment/warner-bros.webp"
        ),
        QuizLevel(
            id = "entertainment_5",
            packId = "entertainment",
            levelNumber = 5,
            answer = "YOUTUBE",
            hintSentence = "Video platform featuring a bright red rectangle and white play button",
            triviaFact = "The first video ever uploaded was 'Me at the zoo' by co-founder Jawed Karim in April 2005.",
            logoKey = "youtube",
            imageUrl = "$CDN_BASE_URL/Entertainment/youtube.webp"
        ),

        // GAMING & TECH PACK (1..10)
        QuizLevel(
            id = "gaming_1",
            packId = "gaming",
            levelNumber = 1,
            answer = "NINTENDO",
            hintSentence = "Kyoto gaming titan celebrated for Mario, Zelda, and Joy-Cons",
            triviaFact = "Nintendo was founded in 1889 by Fusajiro Yamauchi to produce traditional handmade Hanafuda playing cards.",
            logoKey = "nintendo"
        ),
        QuizLevel(
            id = "gaming_2",
            packId = "gaming",
            levelNumber = 2,
            answer = "PLAYSTATION",
            hintSentence = "Sony console brand with the colorful overlapping P and S logo",
            triviaFact = "Designer Manabu Sakamoto created the PS logo using four colors: yellow, green, red, and blue.",
            logoKey = "playstation"
        ),
        QuizLevel(
            id = "gaming_3",
            packId = "gaming",
            levelNumber = 3,
            answer = "XBOX",
            hintSentence = "Microsoft's gaming powerhouse with a glowing green sphere 'X'",
            triviaFact = "The original internal codename was 'DirectX Box' before Microsoft shortened it to Xbox.",
            logoKey = "xbox"
        ),
        QuizLevel(
            id = "gaming_4",
            packId = "gaming",
            levelNumber = 4,
            answer = "STEAM",
            hintSentence = "PC gaming platform with a mechanical locomotive steam piston",
            triviaFact = "Valve launched Steam in 2003 primarily as an automatic update client for Half-Life and Counter-Strike.",
            logoKey = "steam"
        ),
        QuizLevel(
            id = "gaming_5",
            packId = "gaming",
            levelNumber = 5,
            answer = "DISCORD",
            hintSentence = "Voice and community hub with game controller buddy Clyde",
            triviaFact = "Discord's playful controller mascot is officially named Clyde, who had a friendly makeover in 2021.",
            logoKey = "discord"
        ),
        // Ad Gated Levels (6..10)
        QuizLevel(
            id = "gaming_6",
            packId = "gaming",
            levelNumber = 6,
            answer = "ANDROID",
            hintSentence = "World's most popular mobile operating system with a green robot",
            triviaFact = "The beloved green robot was created by Irina Blok and internally nicknamed Bugdroid.",
            logoKey = "android"
        ),
        QuizLevel(
            id = "gaming_7",
            packId = "gaming",
            levelNumber = 7,
            answer = "ATARI",
            hintSentence = "Arcade pioneer with the iconic triple-prong 'Fuji' mountain symbol",
            triviaFact = "Atari created 'Pong' in 1972, which became the world's first commercially successful video game.",
            logoKey = "atari"
        ),
        QuizLevel(
            id = "gaming_8",
            packId = "gaming",
            levelNumber = 8,
            answer = "SEGA",
            hintSentence = "Arcade legend and blue blur creator with striped blue lettering",
            triviaFact = "The famous sung 'SE-GA!' audio chant at the start of Genesis games took up an eighth of the cartridge memory!",
            logoKey = "sega"
        ),
        QuizLevel(
            id = "gaming_9",
            packId = "gaming",
            levelNumber = 9,
            answer = "ROBLOX",
            hintSentence = "User-created 3D metaverse identified by a tilted square ring",
            triviaFact = "Roblox was first founded by David Baszucki and Erik Cassel under the prototype name GoBlocks.",
            logoKey = "roblox"
        ),
        QuizLevel(
            id = "gaming_10",
            packId = "gaming",
            levelNumber = 10,
            answer = "LINUX",
            hintSentence = "Open-source computing pioneer symbolized by Tux the penguin",
            triviaFact = "Creator Linus Torvalds decided on a penguin mascot after being nipped by a small fairy penguin in Australia.",
            logoKey = "linux"
        ),

        // SPORTS & AUTOS PACK (User sequence: adidas, audi, bmw, ferrari, mercedes, nba, olumpic, puma, redbull)
        QuizLevel(
            id = "sports_1",
            packId = "sports",
            levelNumber = 1,
            answer = "ADIDAS",
            hintSentence = "Sportswear titan recognized worldwide by the Three Stripes",
            triviaFact = "The three stripes were originally placed on running shoes in 1949 for lateral stability, not decoration.",
            logoKey = "adidas",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Adidas--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_2",
            packId = "sports",
            levelNumber = 2,
            answer = "AUDI",
            hintSentence = "German luxury auto marque with four interlocking silver rings",
            triviaFact = "The four rings represent the historic 1932 alliance of four independent automakers: Audi, DKW, Horch, and Wanderer.",
            logoKey = "audi",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Audi--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_3",
            packId = "sports",
            levelNumber = 3,
            answer = "BMW",
            hintSentence = "Bavarian automotive maker with blue and white quadrant roundel",
            triviaFact = "While often mistaken for an airplane propeller, the blue and white segments represent Bavaria's state flag.",
            logoKey = "bmw",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Bmw--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_4",
            packId = "sports",
            levelNumber = 4,
            answer = "FERRARI",
            hintSentence = "Italian supercar icon with a canary yellow shield and prancing stallion",
            triviaFact = "The prancing horse was gifted to Enzo Ferrari by Countess Paolina, whose son was an Italian aviation ace.",
            logoKey = "ferrari",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Ferrari--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_5",
            packId = "sports",
            levelNumber = 5,
            answer = "MERCEDES",
            hintSentence = "Pioneering luxury automaker with a gleaming three-pointed star",
            triviaFact = "The three-pointed star symbolizes founder Gottlieb Daimler's vision of motorized transport on land, water, and air.",
            logoKey = "mercedes",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Mercedes--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_6",
            packId = "sports",
            levelNumber = 6,
            answer = "NBA",
            hintSentence = "Premier basketball league with a dynamic player dribbling silhouette",
            triviaFact = "The silhouette on the red and blue badge was modeled directly after Lakers legend Jerry West.",
            logoKey = "nba",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Nba--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_7",
            packId = "sports",
            levelNumber = 7,
            answer = "OLYMPIC",
            hintSentence = "World athletic games uniting five continents with interlocking rings",
            triviaFact = "The five rings' colors plus the white background ensured every nation had at least one flag color represented.",
            logoKey = "olympic",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Olympic_rings_without_rims.svg.webp"
        ),
        QuizLevel(
            id = "sports_8",
            packId = "sports",
            levelNumber = 8,
            answer = "PUMA",
            hintSentence = "Speed and athletic brand with a leaping wild cat silhouette",
            triviaFact = "Puma was founded by Rudolf Dassler in 1948 across the river from his brother's company Adidas.",
            logoKey = "puma",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/Puma--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "sports_9",
            packId = "sports",
            levelNumber = 9,
            answer = "REDBULL",
            hintSentence = "Extreme sports & F1 champion with two charging red bulls and a golden sun",
            triviaFact = "Red Bull sponsors hundreds of extreme sports athletes and owns two Formula 1 racing teams.",
            logoKey = "redbull",
            imageUrl = "$CDN_BASE_URL/Sports%20%26%20Autos/redbull.webp"
        ),

        // FOOD & TREATS PACK (User sequence: burger king, dominos, kfc, pepsi, taco bell)
        QuizLevel(
            id = "food_1",
            packId = "food",
            levelNumber = 1,
            answer = "BURGERKING",
            hintSentence = "Home of the Whopper with two golden bun halves framing its name",
            triviaFact = "Burger King operates in Australia under the trademark name 'Hungry Jack's' due to a pre-existing trademark.",
            logoKey = "burgerking",
            imageUrl = "$CDN_BASE_URL/Food%20%26%20Treats/Burgerking--Streamline-Simple-Icons.webp"
        ),
        QuizLevel(
            id = "food_2",
            packId = "food",
            levelNumber = 2,
            answer = "DOMINOS",
            hintSentence = "Pizza delivery empire with a tilted red and blue domino tile",
            triviaFact = "The three dots on the domino represented the original three franchise stores opened in the 1960s.",
            logoKey = "dominos",
            imageUrl = "$CDN_BASE_URL/Food%20%26%20Treats/dominos.webp"
        ),
        QuizLevel(
            id = "food_3",
            packId = "food",
            levelNumber = 3,
            answer = "KFC",
            hintSentence = "Fried chicken legend founded in Corbin, Kentucky by the Colonel",
            triviaFact = "Colonel Harland Sanders was made an honorary Kentucky Colonel by the state governor in 1935.",
            logoKey = "kfc",
            imageUrl = "$CDN_BASE_URL/Food%20%26%20Treats/kfc.webp"
        ),
        QuizLevel(
            id = "food_4",
            packId = "food",
            levelNumber = 4,
            answer = "PEPSI",
            hintSentence = "Carbonated cola giant with a red, white and blue globe logo",
            triviaFact = "Originally called 'Brad's Drink' in 1893, Pepsi was invented by pharmacist Caleb Bradham in North Carolina.",
            logoKey = "pepsi",
            imageUrl = "$CDN_BASE_URL/Food%20%26%20Treats/pepsi_logo_icon_168910.webp"
        ),
        QuizLevel(
            id = "food_5",
            packId = "food",
            levelNumber = 5,
            answer = "TACOBELL",
            hintSentence = "Mexican-inspired fast food famous for a magenta bell and crunchy tacos",
            triviaFact = "Glen Bell opened the first Taco Bell in Downey, California in 1962, inspiring a nationwide Mexican craze.",
            logoKey = "tacobell",
            imageUrl = "$CDN_BASE_URL/Food%20%26%20Treats/taco%20bell.webp"
        ),

        // WORLD WONDERS PACK (1..10)
        QuizLevel(
            id = "world_1",
            packId = "world",
            levelNumber = 1,
            answer = "EIFFEL",
            hintSentence = "Iconic wrought-iron Parisian tower rising on the Champ de Mars",
            triviaFact = "Gustave Eiffel's tower was built for the 1889 World's Fair and expands up to 15 cm during hot summer days.",
            logoKey = "eiffel"
        ),
        QuizLevel(
            id = "world_2",
            packId = "world",
            levelNumber = 2,
            answer = "PYRAMIDS",
            hintSentence = "Monumental stone wonders rising over the golden desert of Giza",
            triviaFact = "The Great Pyramid of Giza is the oldest of the ancient Seven Wonders and the only one still largely intact.",
            logoKey = "pyramids"
        ),
        QuizLevel(
            id = "world_3",
            packId = "world",
            levelNumber = 3,
            answer = "LIBERTY",
            hintSentence = "Copper colossus holding a radiant torch in New York Harbor",
            triviaFact = "A gift from France in 1886, the statue's green patina formed naturally through oxidation of copper.",
            logoKey = "liberty"
        ),
        QuizLevel(
            id = "world_4",
            packId = "world",
            levelNumber = 4,
            answer = "COLOSSEUM",
            hintSentence = "Massive ancient amphitheater of arches in the heart of Rome",
            triviaFact = "Completed in 80 AD under Emperor Titus, the Colosseum could seat over 50,000 spectators for gladiatorial games.",
            logoKey = "colosseum"
        ),
        QuizLevel(
            id = "world_5",
            packId = "world",
            levelNumber = 5,
            answer = "TAJMAHAL",
            hintSentence = "White marble mausoleum with grand domes and minarets in Agra",
            triviaFact = "Emperor Shah Jahan commissioned the jewel-inlaid palace in 1631 in memory of his beloved wife Mumtaz Mahal.",
            logoKey = "tajmahal"
        ),
        // Ad Gated Levels (6..10)
        QuizLevel(
            id = "world_6",
            packId = "world",
            levelNumber = 6,
            answer = "BIGBEN",
            hintSentence = "Famed four-faced chiming clock tower in London beside Parliament",
            triviaFact = "'Big Ben' is officially the nickname for the massive 13.7-tonne Great Bell inside the Elizabeth Tower.",
            logoKey = "bigben"
        ),
        QuizLevel(
            id = "world_7",
            packId = "world",
            levelNumber = 7,
            answer = "FUJI",
            hintSentence = "Japan's sacred snow-capped volcanic peak rising gracefully",
            triviaFact = "Mount Fuji stands at 3,776 meters and is recognized worldwide as a sacred UNESCO cultural symbol of Japan.",
            logoKey = "fuji"
        ),
        QuizLevel(
            id = "world_8",
            packId = "world",
            levelNumber = 8,
            answer = "PISA",
            hintSentence = "Freestanding marble bell tower famously tilted in Tuscany, Italy",
            triviaFact = "Construction began in 1173; the tilt began early on due to soft clay soil on one side of the foundation.",
            logoKey = "pisa"
        ),
        QuizLevel(
            id = "world_9",
            packId = "world",
            levelNumber = 9,
            answer = "SPHINX",
            hintSentence = "Limestone statue of a lion with a pharaoh head guarding Giza",
            triviaFact = "Carved directly from bedrock, the Great Sphinx measures 73 meters from paw to tail and 20 meters high.",
            logoKey = "sphinx"
        ),
        QuizLevel(
            id = "world_10",
            packId = "world",
            levelNumber = 10,
            answer = "SYDNEY",
            hintSentence = "Multi-venue performing arts centre with soaring sail-shaped roofs",
            triviaFact = "Designed by Danish architect Jørn Utzon, the Opera House shells are covered in over one million ceramic tiles.",
            logoKey = "sydney"
        )
    )

    fun getLevelById(id: String): QuizLevel? = allLevels.find { it.id == id }

    fun getLevelsForPack(packId: String): List<QuizLevel> = allLevels.filter { it.packId == packId }

    /**
     * Determines whether a level is completed, unlocked, eligible for ad-unlock, or strictly locked.
     * Rule: Every level (whether free or watch-ad) can ONLY be unlocked if the previous level is completed.
     * Level 1 of any pack is always unlocked by default.
     */
    fun getLevelLockStatus(
        level: QuizLevel,
        allProgress: List<LevelProgressEntity>
    ): LevelLockStatus {
        val progress = allProgress.find { it.id == level.id }
        val isCompleted = progress?.isCompleted == true

        if (isCompleted) {
            return LevelLockStatus(
                isCompleted = true,
                isUnlocked = true,
                isAdGated = false,
                isStrictlyLocked = false,
                requiredPreviousLevel = null
            )
        }

        // Level 1 is always unlocked
        if (level.levelNumber == 1) {
            return LevelLockStatus(
                isCompleted = false,
                isUnlocked = true,
                isAdGated = false,
                isStrictlyLocked = false,
                requiredPreviousLevel = null
            )
        }

        // For level N > 1, level N - 1 in the same pack must be completed
        val packLevels = getLevelsForPack(level.packId)
        val prevLevel = packLevels.find { it.levelNumber == level.levelNumber - 1 }
        val prevProgress = prevLevel?.let { p -> allProgress.find { it.id == p.id } }
        val isPrevCompleted = prevProgress?.isCompleted == true

        if (!isPrevCompleted) {
            // Previous level has not been completed yet -> strictly locked
            return LevelLockStatus(
                isCompleted = false,
                isUnlocked = false,
                isAdGated = false,
                isStrictlyLocked = true,
                requiredPreviousLevel = prevLevel
            )
        }

        // Previous level IS completed:
        // Free levels (stages 1..5) are automatically unlocked once previous level is completed
        val isFreeLevel = level.levelNumber <= 5
        val hasWatchedAd = progress?.isUnlocked == true

        return if (isFreeLevel || hasWatchedAd) {
            LevelLockStatus(
                isCompleted = false,
                isUnlocked = true,
                isAdGated = false,
                isStrictlyLocked = false,
                requiredPreviousLevel = null
            )
        } else {
            // Ad-gated level (stages 6..10) whose previous level IS completed, ready for ad unlock
            LevelLockStatus(
                isCompleted = false,
                isUnlocked = false,
                isAdGated = true,
                isStrictlyLocked = false,
                requiredPreviousLevel = null
            )
        }
    }

    /**
     * Returns only the levels that should be shown to the user for a pack.
     * Rule:
     * - Level 1 is always shown.
     * - Level N (where N > 1) is ONLY shown when Level N-1 has been passed (completed).
     */
    fun getVisibleLevelsForPack(
        packId: String,
        allProgress: List<LevelProgressEntity>
    ): List<QuizLevel> {
        val packLevels = getLevelsForPack(packId).sortedBy { it.levelNumber }
        val visibleList = mutableListOf<QuizLevel>()

        for (level in packLevels) {
            if (level.levelNumber == 1) {
                visibleList.add(level)
            } else {
                val prevLevel = packLevels.find { it.levelNumber == level.levelNumber - 1 }
                val prevProgress = prevLevel?.let { p -> allProgress.find { it.id == p.id } }
                val isPrevCompleted = prevProgress?.isCompleted == true

                if (isPrevCompleted) {
                    visibleList.add(level)
                } else {
                    // Do not show this or any subsequent levels until previous level is passed
                    break
                }
            }
        }
        return visibleList
    }
}

data class LevelLockStatus(
    val isCompleted: Boolean,
    val isUnlocked: Boolean,
    val isAdGated: Boolean,
    val isStrictlyLocked: Boolean,
    val requiredPreviousLevel: QuizLevel?
)
