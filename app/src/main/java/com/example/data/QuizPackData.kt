package com.example.data

import java.net.URLEncoder

object QuizPackData {

    const val CDN_BASE_URL = "https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz"

    fun buildCdnUrl(folderName: String, fileName: String): String {
        val encodedFolder = try {
            URLEncoder.encode(folderName, "UTF-8").replace("+", "%20")
        } catch (_: Exception) {
            folderName
        }
        val encodedFile = try {
            URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
        } catch (_: Exception) {
            fileName
        }
        return "$CDN_BASE_URL/$encodedFolder/$encodedFile"
    }

    val bundledLevels: List<QuizLevel> = listOf(
        // ==================== 1. AUTOMOTIVE ====================
        QuizLevel(
            id = "automotive_1",
            packId = "automotive",
            levelNumber = 1,
            answer = "TOYOTA",
            hintSentence = "Japanese automotive giant with three overlapping ellipses forming a T",
            triviaFact = "The three ovals represent the heart of the customer, the heart of the product, and endless technological progress.",
            logoKey = "toyota",
            imageUrl = buildCdnUrl("Automotive", "toyota.webp")
        ),
        QuizLevel(
            id = "automotive_2",
            packId = "automotive",
            levelNumber = 2,
            answer = "BMW",
            hintSentence = "Bavarian motor company known for the blue and white circular roundel",
            triviaFact = "The blue and white quarters represent the state colors of Bavaria, Germany, where BMW was founded.",
            logoKey = "bmw",
            imageUrl = buildCdnUrl("Automotive", "bmw.webp")
        ),
        QuizLevel(
            id = "automotive_3",
            packId = "automotive",
            levelNumber = 3,
            answer = "FERRARI",
            hintSentence = "Italian luxury supercar maker with a prancing stallion on a yellow shield",
            triviaFact = "The prancing horse was originally painted on the fuselage of Francesco Baracca's fighter plane during WWI.",
            logoKey = "ferrari",
            imageUrl = buildCdnUrl("Automotive", "ferrari.webp")
        ),
        QuizLevel(
            id = "automotive_4",
            packId = "automotive",
            levelNumber = 4,
            answer = "HONDA",
            hintSentence = "Major car and motorcycle manufacturer symbolized by a bold silver H badge",
            triviaFact = "Honda has been the world's largest motorcycle manufacturer since 1959, producing over 400 million units.",
            logoKey = "honda",
            imageUrl = buildCdnUrl("Automotive", "honda.webp")
        ),
        QuizLevel(
            id = "automotive_5",
            packId = "automotive",
            levelNumber = 5,
            answer = "DUCATI",
            hintSentence = "Italian performance motorcycle pioneer famous for desmodromic valve V-twins",
            triviaFact = "Ducati was founded in 1926 in Bologna, Italy, initially manufacturing radio components before making motorcycles.",
            logoKey = "ducati",
            imageUrl = buildCdnUrl("Automotive", "ducati.webp")
        ),
        QuizLevel(
            id = "automotive_6",
            packId = "automotive",
            levelNumber = 6,
            answer = "TESLA",
            hintSentence = "Electric vehicle and clean energy leader represented by a stylized curved T",
            triviaFact = "Tesla's logo isn't just a letter 'T'; it depicts a cross-section of an electric induction motor rotor.",
            logoKey = "tesla",
            imageUrl = buildCdnUrl("Automotive", "tesla.webp")
        ),
        QuizLevel(
            id = "automotive_7",
            packId = "automotive",
            levelNumber = 7,
            answer = "PORSCHE",
            hintSentence = "German sports car marque with Stuttgart's coat of arms and prancing horse",
            triviaFact = "Ferdinand Porsche designed the iconic Volkswagen Beetle before founding his eponymous sports car company in 1948.",
            logoKey = "porsche",
            imageUrl = buildCdnUrl("Automotive", "porsche.webp")
        ),
        QuizLevel(
            id = "automotive_8",
            packId = "automotive",
            levelNumber = 8,
            answer = "YAMAHA",
            hintSentence = "Motorcycle and motor company emblem featuring three crossed tuning forks",
            triviaFact = "The three interlocking tuning forks honor Yamaha's origins in musical instruments and acoustics.",
            logoKey = "yamaha",
            imageUrl = buildCdnUrl("Automotive", "yamaha.webp")
        ),
        QuizLevel(
            id = "automotive_9",
            packId = "automotive",
            levelNumber = 9,
            answer = "LAMBORGHINI",
            hintSentence = "Italian exotic supercar builder bearing a charging golden bull emblem",
            triviaFact = "Founder Ferruccio Lamborghini chose a charging bull because his zodiac astrological sign was Taurus.",
            logoKey = "lamborghini",
            imageUrl = buildCdnUrl("Automotive", "lamborghini.webp")
        ),
        QuizLevel(
            id = "automotive_10",
            packId = "automotive",
            levelNumber = 10,
            answer = "HARLEY",
            hintSentence = "Legendary American motorcycle manufacturer with an iconic Bar and Shield",
            triviaFact = "Harley-Davidson was founded in Milwaukee, Wisconsin in 1903 in a humble 10x15 foot wooden shed.",
            logoKey = "harley",
            imageUrl = buildCdnUrl("Automotive", "harley.webp")
        ),

        // ==================== 2. FOOD & BEVERAGE ====================
        QuizLevel(
            id = "food_beverage_1",
            packId = "food_beverage",
            levelNumber = 1,
            answer = "MCDONALDS",
            hintSentence = "Fast-food burger empire celebrated worldwide for its iconic Golden Arches",
            triviaFact = "The Golden Arches were originally structural features of early walk-up franchise restaurants in the 1950s.",
            logoKey = "mcdonalds",
            imageUrl = buildCdnUrl("Food & Beverage", "mcdonalds.webp")
        ),
        QuizLevel(
            id = "food_beverage_2",
            packId = "food_beverage",
            levelNumber = 2,
            answer = "KFC",
            hintSentence = "Fried chicken empire famous for 11 secret herbs and spices and Colonel Sanders",
            triviaFact = "Colonel Harland Sanders was commissioned as an honorary Kentucky Colonel by Governor Ruby Laffoon in 1935.",
            logoKey = "kfc",
            imageUrl = buildCdnUrl("Food & Beverage", "kfc.webp")
        ),
        QuizLevel(
            id = "food_beverage_3",
            packId = "food_beverage",
            levelNumber = 3,
            answer = "COCACOLA",
            hintSentence = "World-famous cola beverage with iconic flowing Spencerian script",
            triviaFact = "Invented by pharmacist John Stith Pemberton in Atlanta in 1886; the Spencerian script was designed by his bookkeeper Frank Robinson.",
            logoKey = "cocacola",
            imageUrl = buildCdnUrl("Food & Beverage", "cocacola.webp")
        ),
        QuizLevel(
            id = "food_beverage_4",
            packId = "food_beverage",
            levelNumber = 4,
            answer = "PEPSI",
            hintSentence = "Global carbonated cola featuring a red, white, and blue spherical globe",
            triviaFact = "Created in 1893 by pharmacist Caleb Bradham under the initial name 'Brad's Drink' in New Bern, North Carolina.",
            logoKey = "pepsi",
            imageUrl = buildCdnUrl("Food & Beverage", "pepsi.webp")
        ),
        QuizLevel(
            id = "food_beverage_5",
            packId = "food_beverage",
            levelNumber = 5,
            answer = "KITKAT",
            hintSentence = "Chocolate wafer fingers famous for the slogan 'Have a break, have a KitKat'",
            triviaFact = "Introduced in 1935 in York, England by Rowntree's as 'Rowntree's Chocolate Crisp' before becoming KitKat.",
            logoKey = "kitkat",
            imageUrl = buildCdnUrl("Food & Beverage", "kitkat.webp")
        ),
        QuizLevel(
            id = "food_beverage_6",
            packId = "food_beverage",
            levelNumber = 6,
            answer = "LAYS",
            hintSentence = "Popular potato chips brand with a glowing yellow sun over a red banner",
            triviaFact = "Founded in Nashville, Tennessee in 1932 by Herman Lay, who initially sold potato chips from the trunk of his car.",
            logoKey = "lays",
            imageUrl = buildCdnUrl("Food & Beverage", "lays.webp")
        ),
        QuizLevel(
            id = "food_beverage_7",
            packId = "food_beverage",
            levelNumber = 7,
            answer = "STARBUCKS",
            hintSentence = "Global coffeehouse chain recognized by a circular twin-tailed siren emblem",
            triviaFact = "The Starbucks siren is drawn from a 16th-century Norse woodcut depicting a mythological two-tailed sea creature.",
            logoKey = "starbucks",
            imageUrl = buildCdnUrl("Food & Beverage", "starbucks.webp")
        ),
        QuizLevel(
            id = "food_beverage_8",
            packId = "food_beverage",
            levelNumber = 8,
            answer = "SUBWAY",
            hintSentence = "Fresh submarine sandwich chain with green and yellow directional arrows",
            triviaFact = "Subway operates over 37,000 restaurants globally and serves more than 5,300 sandwiches every minute.",
            logoKey = "subway",
            imageUrl = buildCdnUrl("Food & Beverage", "subway.webp")
        ),
        QuizLevel(
            id = "food_beverage_9",
            packId = "food_beverage",
            levelNumber = 9,
            answer = "PRINGLES",
            hintSentence = "Stackable potato crisps can featuring a mustachioed mascot named Julius",
            triviaFact = "The hyperbolic paraboloid geometry of a Pringles chip was designed by supercomputer modeling to prevent breaking.",
            logoKey = "pringles",
            imageUrl = buildCdnUrl("Food & Beverage", "pringles.webp")
        ),
        QuizLevel(
            id = "food_beverage_10",
            packId = "food_beverage",
            levelNumber = 10,
            answer = "OREO",
            hintSentence = "World's bestselling sandwich cookie featuring chocolate wafers with creme",
            triviaFact = "Over 500 billion Oreo cookies have been sold since their introduction in 1912 by Nabisco in New York City.",
            logoKey = "oreo",
            imageUrl = buildCdnUrl("Food & Beverage", "oreo.webp")
        ),

        // ==================== 3. TECHNOLOGY ====================
        QuizLevel(
            id = "technology_1",
            packId = "technology",
            levelNumber = 1,
            answer = "GOOGLE",
            hintSentence = "Search and cloud titan known for primary colors and a distinctive capital G",
            triviaFact = "Google's name originated from a misspelling of 'googol', which represents the number 1 followed by 100 zeros.",
            logoKey = "google",
            imageUrl = buildCdnUrl("Technology", "google.webp")
        ),
        QuizLevel(
            id = "technology_2",
            packId = "technology",
            levelNumber = 2,
            answer = "SPOTIFY",
            hintSentence = "Audio streaming leader with three curved acoustic wave lines in a green circle",
            triviaFact = "Spotify was launched in Stockholm, Sweden in 2006 by Daniel Ek and Martin Lorentzon to curb online music piracy.",
            logoKey = "spotify",
            imageUrl = buildCdnUrl("Technology", "spotify.webp")
        ),
        QuizLevel(
            id = "technology_3",
            packId = "technology",
            levelNumber = 3,
            answer = "APPLE",
            hintSentence = "Bitten fruit consumer electronics giant behind iPhone, iPad, and Mac",
            triviaFact = "Apple's first logo featured Sir Isaac Newton sitting beneath an apple tree before Rob Janoff drew the bitten apple.",
            logoKey = "apple",
            imageUrl = buildCdnUrl("Technology", "apple.webp")
        ),
        QuizLevel(
            id = "technology_4",
            packId = "technology",
            levelNumber = 4,
            answer = "SAMSUNG",
            hintSentence = "South Korean tech titan famous for Galaxy phones, memory chips, and smart TVs",
            triviaFact = "In Korean, 'Samsung' means 'three stars', chosen by founder Lee Byung-chull to represent eternal greatness.",
            logoKey = "samsung",
            imageUrl = buildCdnUrl("Technology", "samsung.webp")
        ),
        QuizLevel(
            id = "technology_5",
            packId = "technology",
            levelNumber = 5,
            answer = "MICROSOFT",
            hintSentence = "Computing giant famous for Windows, Office, and Azure, with four colored squares",
            triviaFact = "Founded in 1975 by Bill Gates and Paul Allen, Microsoft is a portmanteau of 'microcomputer' and 'software'.",
            logoKey = "microsoft",
            imageUrl = buildCdnUrl("Technology", "microsoft.webp")
        ),
        QuizLevel(
            id = "technology_6",
            packId = "technology",
            levelNumber = 6,
            answer = "SONY",
            hintSentence = "Japanese electronics and entertainment powerhouse behind PlayStation and Alpha",
            triviaFact = "The name Sony comes from the Latin word 'sonus' (sound) and the slang term 'sonny boy' used in 1950s Japan.",
            logoKey = "sony",
            imageUrl = buildCdnUrl("Technology", "sony.webp")
        ),
        QuizLevel(
            id = "technology_7",
            packId = "technology",
            levelNumber = 7,
            answer = "INTEL",
            hintSentence = "Semiconductor pioneer whose microprocessors power computers across the globe",
            triviaFact = "Intel is a portmanteau of 'Integrated Electronics', co-founded in 1968 by Robert Noyce and Gordon Moore.",
            logoKey = "intel",
            imageUrl = buildCdnUrl("Technology", "intel.webp")
        ),
        QuizLevel(
            id = "technology_8",
            packId = "technology",
            levelNumber = 8,
            answer = "ADOBE",
            hintSentence = "Creative software pioneer behind Photoshop, Acrobat, Illustrator, and Premiere",
            triviaFact = "The company was named after Adobe Creek in Los Altos, California, which ran behind co-founder John Warnock's home.",
            logoKey = "adobe",
            imageUrl = buildCdnUrl("Technology", "adobe.webp")
        ),
        QuizLevel(
            id = "technology_9",
            packId = "technology",
            levelNumber = 9,
            answer = "ZOHO",
            hintSentence = "Cloud productivity software suite represented by colorful interlocking toy blocks",
            triviaFact = "Founded in 1996 as AdventNet by Sridhar Vembu, Zoho operates private cloud data centers serving 100M+ users.",
            logoKey = "zoho",
            imageUrl = buildCdnUrl("Technology", "zoho.webp")
        ),
        QuizLevel(
            id = "technology_10",
            packId = "technology",
            levelNumber = 10,
            answer = "DELL",
            hintSentence = "Computer technology corporation with a distinctive slanted E in its logo",
            triviaFact = "Michael Dell started the company in 1984 from his dorm room at the University of Texas with just $1,000.",
            logoKey = "dell",
            imageUrl = buildCdnUrl("Technology", "dell.webp")
        ),

        // ==================== 4. FASHION & CLOTHING ====================
        QuizLevel(
            id = "fashion_clothing_1",
            packId = "fashion_clothing",
            levelNumber = 1,
            answer = "NIKE",
            hintSentence = "Athletic apparel and footwear titan recognized worldwide by the Swoosh",
            triviaFact = "The famous Swoosh was designed in 1971 by graphic design student Carolyn Davidson for just $35.",
            logoKey = "nike",
            imageUrl = buildCdnUrl("Fashion & Clothing", "nike.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_2",
            packId = "fashion_clothing",
            levelNumber = 2,
            answer = "ADIDAS",
            hintSentence = "German sports brand famous for the iconic 3-Stripes and Trefoil emblems",
            triviaFact = "The company name comes from founder Adolf ('Adi') Dassler, who crafted track shoes in Bavaria in 1949.",
            logoKey = "adidas",
            imageUrl = buildCdnUrl("Fashion & Clothing", "adidas.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_3",
            packId = "fashion_clothing",
            levelNumber = 3,
            answer = "GUCCI",
            hintSentence = "Florentine luxury fashion house known for interlocking double-G monogram",
            triviaFact = "Guccio Gucci founded the house in Florence in 1921 after working as a bellboy at the Savoy Hotel in London.",
            logoKey = "gucci",
            imageUrl = buildCdnUrl("Fashion & Clothing", "gucci.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_4",
            packId = "fashion_clothing",
            levelNumber = 4,
            answer = "PRADA",
            hintSentence = "Milanese high-fashion luxury label represented by a triangular metal plaque",
            triviaFact = "Prada was founded in Milan in 1913 by Mario Prada and became the official supplier to the Italian Royal Household.",
            logoKey = "prada",
            imageUrl = buildCdnUrl("Fashion & Clothing", "prada.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_5",
            packId = "fashion_clothing",
            levelNumber = 5,
            answer = "PUMA",
            hintSentence = "Athletic footwear and apparel brand with a leaping feline silhouette",
            triviaFact = "Founded in 1948 by Rudolf Dassler, brother of Adidas founder Adi Dassler, following a family split.",
            logoKey = "puma",
            imageUrl = buildCdnUrl("Fashion & Clothing", "puma.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_6",
            packId = "fashion_clothing",
            levelNumber = 6,
            answer = "CHANEL",
            hintSentence = "French luxury fashion house with interlocking mirrored C letter monogram",
            triviaFact = "Founded in Paris in 1910 by Gabrielle 'Coco' Chanel, pioneering the iconic little black dress and No. 5 perfume.",
            logoKey = "chanel",
            imageUrl = buildCdnUrl("Fashion & Clothing", "chanel.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_7",
            packId = "fashion_clothing",
            levelNumber = 7,
            answer = "ZARA",
            hintSentence = "Spanish fast-fashion powerhouse with overlapping serif serif typography",
            triviaFact = "Founded in Galicia, Spain in 1975 by Amancio Ortega, Zara can design and place a new garment in stores in just 15 days.",
            logoKey = "zara",
            imageUrl = buildCdnUrl("Fashion & Clothing", "zara.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_8",
            packId = "fashion_clothing",
            levelNumber = 8,
            answer = "UNDERARMOUR",
            hintSentence = "Performance athletic brand featuring an interlocking U and A emblem",
            triviaFact = "Founded in 1996 by Kevin Plank, a former University of Maryland football player, in his grandmother's basement.",
            logoKey = "underarmour",
            imageUrl = buildCdnUrl("Fashion & Clothing", "underarmour.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_9",
            packId = "fashion_clothing",
            levelNumber = 9,
            answer = "LEVIS",
            hintSentence = "American denim pioneer famous for the red tab and 501 copper-riveted jeans",
            triviaFact = "Levi Strauss and Jacob Davis patented copper rivets on work trousers in 1873, inventing modern blue jeans.",
            logoKey = "levis",
            imageUrl = buildCdnUrl("Fashion & Clothing", "levis.webp")
        ),
        QuizLevel(
            id = "fashion_clothing_10",
            packId = "fashion_clothing",
            levelNumber = 10,
            answer = "DIOR",
            hintSentence = "French haute couture house celebrated worldwide for elegant luxury collections",
            triviaFact = "Christian Dior revolutionized women's fashion in 1947 with his groundbreaking 'New Look' silhouette.",
            logoKey = "dior",
            imageUrl = buildCdnUrl("Fashion & Clothing", "dior.webp")
        ),

        // ==================== 5. ENTERTAINMENT ====================
        QuizLevel(
            id = "entertainment_1",
            packId = "entertainment",
            levelNumber = 1,
            answer = "NETFLIX",
            hintSentence = "Pioneering video streaming titan with a bold red ribbon letter N",
            triviaFact = "Netflix started in 1997 as a DVD-by-mail rental business before launching its revolutionary streaming platform in 2007.",
            logoKey = "netflix",
            imageUrl = buildCdnUrl("Entertainment", "netflix.webp")
        ),
        QuizLevel(
            id = "entertainment_2",
            packId = "entertainment",
            levelNumber = 2,
            answer = "DISNEY",
            hintSentence = "Magic entertainment kingdom with an iconic fairytale castle and cursive script",
            triviaFact = "Walt Disney created Mickey Mouse in 1928 after losing the rights to his first animated character, Oswald the Lucky Rabbit.",
            logoKey = "disney",
            imageUrl = buildCdnUrl("Entertainment", "disney.webp")
        ),
        QuizLevel(
            id = "entertainment_3",
            packId = "entertainment",
            levelNumber = 3,
            answer = "PLAYSTATION",
            hintSentence = "Sony's gaming console giant with a standing P and casting S shadow",
            triviaFact = "The PlayStation logo was designed by Manabu Sakamoto, symbolizing the console's breakthrough 3D polygon graphics.",
            logoKey = "playstation",
            imageUrl = buildCdnUrl("Entertainment", "playstation.webp")
        ),
        QuizLevel(
            id = "entertainment_4",
            packId = "entertainment",
            levelNumber = 4,
            answer = "XBOX",
            hintSentence = "Microsoft's gaming console brand marked by an embossed sphere with a green X",
            triviaFact = "The original Xbox was developed by four engineers and originally called the DirectX Box.",
            logoKey = "xbox",
            imageUrl = buildCdnUrl("Entertainment", "xbox.webp")
        ),
        QuizLevel(
            id = "entertainment_5",
            packId = "entertainment",
            levelNumber = 5,
            answer = "HBO",
            hintSentence = "Prestige television and streaming network with a bullseye dot inside its O",
            triviaFact = "Home Box Office launched in November 1972, transmitting an NHL hockey game to 365 cable subscribers in Wilkes-Barre, PA.",
            logoKey = "hbo",
            imageUrl = buildCdnUrl("Entertainment", "hbo.webp")
        ),
        QuizLevel(
            id = "entertainment_6",
            packId = "entertainment",
            levelNumber = 6,
            answer = "MARVEL",
            hintSentence = "Superhero comic and cinematic universe with bold white letters on red",
            triviaFact = "Started in 1939 as Timely Comics, Marvel launched Spider-Man, the Avengers, and X-Men under Stan Lee and Jack Kirby.",
            logoKey = "marvel",
            imageUrl = buildCdnUrl("Entertainment", "marvel.webp")
        ),
        QuizLevel(
            id = "entertainment_7",
            packId = "entertainment",
            levelNumber = 7,
            answer = "NINTENDO",
            hintSentence = "Legendary gaming corporation in a racetrack capsule, creators of Mario and Zelda",
            triviaFact = "Founded in Kyoto, Japan in 1889, Nintendo originally manufactured handmade hanafuda playing cards.",
            logoKey = "nintendo",
            imageUrl = buildCdnUrl("Entertainment", "nintendo.webp")
        ),
        QuizLevel(
            id = "entertainment_8",
            packId = "entertainment",
            levelNumber = 8,
            answer = "PARAMOUNT",
            hintSentence = "Historic film studio symbolized by a snow-capped mountain encircled by stars",
            triviaFact = "Founded in 1912 by Adolph Zukor, Paramount is the second oldest surviving film studio in the United States.",
            logoKey = "paramount",
            imageUrl = buildCdnUrl("Entertainment", "paramount.webp")
        ),
        QuizLevel(
            id = "entertainment_9",
            packId = "entertainment",
            levelNumber = 9,
            answer = "PIXAR",
            hintSentence = "Computer animation studio famous for Toy Story and a bouncing desk lamp",
            triviaFact = "The desk lamp is named Luxo Jr., starring in Pixar's groundbreaking 1986 CGI short film directed by John Lasseter.",
            logoKey = "pixar",
            imageUrl = buildCdnUrl("Entertainment", "pixar.webp")
        ),
        QuizLevel(
            id = "entertainment_10",
            packId = "entertainment",
            levelNumber = 10,
            answer = "CRUNCHYROLL",
            hintSentence = "Leading global anime streaming service with a vibrant orange eye emblem",
            triviaFact = "Crunchyroll streams anime in over 200 countries and territories with a library exceeding 40,000 episodes.",
            logoKey = "crunchyroll",
            imageUrl = buildCdnUrl("Entertainment", "crunchyroll.webp")
        ),

        // ==================== 6. FINANCE & BANKING ====================
        QuizLevel(
            id = "finance_banking_1",
            packId = "finance_banking",
            levelNumber = 1,
            answer = "VISA",
            hintSentence = "Global payments technology network connecting billions of cards and merchants",
            triviaFact = "The name VISA was selected in 1976 because it was easily pronounceable and recognized across virtually all languages.",
            logoKey = "visa",
            imageUrl = buildCdnUrl("Finance & Banking", "visa.webp")
        ),
        QuizLevel(
            id = "finance_banking_2",
            packId = "finance_banking",
            levelNumber = 2,
            answer = "PAYPAL",
            hintSentence = "Digital payments and wallet platform with double interlocking blue Ps",
            triviaFact = "PayPal was established in 1998 as Confinity by Max Levchin, Peter Thiel, and Luke Nosek before merging with Elon Musk's X.com.",
            logoKey = "paypal",
            imageUrl = buildCdnUrl("Finance & Banking", "paypal.webp")
        ),
        QuizLevel(
            id = "finance_banking_3",
            packId = "finance_banking",
            levelNumber = 3,
            answer = "MASTERCARD",
            hintSentence = "Payment network recognized everywhere by overlapping red and yellow circles",
            triviaFact = "The interlocking red and yellow circles were designed in 1968, representing financial connection and shared value.",
            logoKey = "mastercard",
            imageUrl = buildCdnUrl("Finance & Banking", "mastercard.webp")
        ),
        QuizLevel(
            id = "finance_banking_4",
            packId = "finance_banking",
            levelNumber = 4,
            answer = "CHASE",
            hintSentence = "Leading American consumer bank recognized by an octagonal blue symbol",
            triviaFact = "The distinctive octagonal logo was designed by Chermayeff & Geismar in 1960 and remains an icon of banking design.",
            logoKey = "chase",
            imageUrl = buildCdnUrl("Finance & Banking", "chase.webp")
        ),
        QuizLevel(
            id = "finance_banking_5",
            packId = "finance_banking",
            levelNumber = 5,
            answer = "AMEX",
            hintSentence = "Prestigious credit card and traveler services company in a blue box",
            triviaFact = "Founded in 1850 in Buffalo, NY as an express freight mail company before pioneering traveler's cheques in 1891.",
            logoKey = "amex",
            imageUrl = buildCdnUrl("Finance & Banking", "amex.webp")
        ),
        QuizLevel(
            id = "finance_banking_6",
            packId = "finance_banking",
            levelNumber = 6,
            answer = "STRIPE",
            hintSentence = "Financial infrastructure and online payment processing software for internet business",
            triviaFact = "Founded in 2010 by Irish brothers Patrick and John Collison, Stripe now powers transactions for millions of online enterprises.",
            logoKey = "stripe",
            imageUrl = buildCdnUrl("Finance & Banking", "stripe.webp")
        ),
        QuizLevel(
            id = "finance_banking_7",
            packId = "finance_banking",
            levelNumber = 7,
            answer = "CITIBANK",
            hintSentence = "Global consumer banking corporation featuring a red arch linking two 'i's",
            triviaFact = "The Citibank umbrella arch logo was sketched by Paula Scher on the back of a napkin in under five minutes.",
            logoKey = "citibank",
            imageUrl = buildCdnUrl("Finance & Banking", "citibank.webp")
        ),
        QuizLevel(
            id = "finance_banking_8",
            packId = "finance_banking",
            levelNumber = 8,
            answer = "REVOLUT",
            hintSentence = "Fintech super-app offering borderless currency exchange and debit accounts",
            triviaFact = "Founded in London in 2015 by Nikolay Storonsky and Vlad Yatsenko to eliminate high foreign transaction fees.",
            logoKey = "revolut",
            imageUrl = buildCdnUrl("Finance & Banking", "revolut.webp")
        ),
        QuizLevel(
            id = "finance_banking_9",
            packId = "finance_banking",
            levelNumber = 9,
            answer = "SQUARE",
            hintSentence = "Mobile point-of-sale and financial services company with a concentric square",
            triviaFact = "Founded in 2009 by Jack Dorsey and Jim McKelvey, enabling anyone with a smartphone to accept credit card payments.",
            logoKey = "square",
            imageUrl = buildCdnUrl("Finance & Banking", "square.webp")
        ),
        QuizLevel(
            id = "finance_banking_10",
            packId = "finance_banking",
            levelNumber = 10,
            answer = "BARCLAYS",
            hintSentence = "British multinational universal bank featuring an iconic spread eagle badge",
            triviaFact = "Barclays traces its roots back to 1690 in London and installed the world's very first cash automated teller machine (ATM) in 1967.",
            logoKey = "barclays",
            imageUrl = buildCdnUrl("Finance & Banking", "barclays.webp")
        ),

        // ==================== 7. TRAVEL & AIRLINES ====================
        QuizLevel(
            id = "travel_airlines_1",
            packId = "travel_airlines",
            levelNumber = 1,
            answer = "EMIRATES",
            hintSentence = "Dubai-based luxury international airline with flowing Arabic calligraphy",
            triviaFact = "Emirates operated its inaugural flight from Dubai to Karachi in 1985 with aircraft leased from Pakistan International Airlines.",
            logoKey = "emirates",
            imageUrl = buildCdnUrl("Travel & Airlines", "emirates.webp")
        ),
        QuizLevel(
            id = "travel_airlines_2",
            packId = "travel_airlines",
            levelNumber = 2,
            answer = "AIRBNB",
            hintSentence = "Vacation rental and lodging marketplace with the 'Bélo' looping symbol",
            triviaFact = "The 'Bélo' symbol represents people, places, love, and Airbnb combined into one continuous fluid loop.",
            logoKey = "airbnb",
            imageUrl = buildCdnUrl("Travel & Airlines", "airbnb.webp")
        ),
        QuizLevel(
            id = "travel_airlines_3",
            packId = "travel_airlines",
            levelNumber = 3,
            answer = "BOEING",
            hintSentence = "Aerospace giant and jetliner manufacturer with a globe and supersonic ring",
            triviaFact = "Boeing's historic 747 'Jumbo Jet' made commercial transatlantic flight accessible to millions after its 1969 debut.",
            logoKey = "boeing",
            imageUrl = buildCdnUrl("Travel & Airlines", "boeing.webp")
        ),
        QuizLevel(
            id = "travel_airlines_4",
            packId = "travel_airlines",
            levelNumber = 4,
            answer = "DELTA",
            hintSentence = "Major US airline with a red and blue triangular 'Widget' logo",
            triviaFact = "Delta began in 1925 as Huff Daland Dusters in Macon, Georgia, operating the world's first commercial crop-dusting service.",
            logoKey = "delta",
            imageUrl = buildCdnUrl("Travel & Airlines", "delta.webp")
        ),
        QuizLevel(
            id = "travel_airlines_5",
            packId = "travel_airlines",
            levelNumber = 5,
            answer = "BOOKING",
            hintSentence = "Global online travel agency and hotel reservation website with a blue B",
            triviaFact = "Started in Amsterdam in 1996 as Bookings.nl by Geert-Jan Bruinsma and now lists over 28 million accommodation options.",
            logoKey = "booking",
            imageUrl = buildCdnUrl("Travel & Airlines", "booking.webp")
        ),
        QuizLevel(
            id = "travel_airlines_6",
            packId = "travel_airlines",
            levelNumber = 6,
            answer = "HILTON",
            hintSentence = "Global hospitality chain operating luxury hotels, resorts, and suites",
            triviaFact = "Hilton was the first hotel chain to install televisions in guest rooms across the country in 1947.",
            logoKey = "hilton",
            imageUrl = buildCdnUrl("Travel & Airlines", "hilton.webp")
        ),
        QuizLevel(
            id = "travel_airlines_7",
            packId = "travel_airlines",
            levelNumber = 7,
            answer = "LUFTHANSA",
            hintSentence = "German flag carrier airline recognized by an encircled flying crane",
            triviaFact = "The iconic flying crane emblem was designed in 1918 by architect and graphic designer Otto Firle.",
            logoKey = "lufthansa",
            imageUrl = buildCdnUrl("Travel & Airlines", "lufthansa.webp")
        ),
        QuizLevel(
            id = "travel_airlines_8",
            packId = "travel_airlines",
            levelNumber = 8,
            answer = "MARRIOTT",
            hintSentence = "World's largest hotel company recognized by a bold crimson letter M",
            triviaFact = "J. Willard Marriott founded the company in 1927 as a small nine-stool root beer stand in Washington, D.C.",
            logoKey = "marriott",
            imageUrl = buildCdnUrl("Travel & Airlines", "marriott.webp")
        ),
        QuizLevel(
            id = "travel_airlines_9",
            packId = "travel_airlines",
            levelNumber = 9,
            answer = "EXPEDIA",
            hintSentence = "Online travel booking platform featuring an airplane soaring through a circle",
            triviaFact = "Expedia was originally created in 1996 inside Microsoft as an internal online travel planning division.",
            logoKey = "expedia",
            imageUrl = buildCdnUrl("Travel & Airlines", "expedia.webp")
        ),
        QuizLevel(
            id = "travel_airlines_10",
            packId = "travel_airlines",
            levelNumber = 10,
            answer = "UBER",
            hintSentence = "Ridesharing, mobility, and travel app with bold monochrome typography",
            triviaFact = "Founded in 2009 by Travis Kalanick and Garrett Camp after they couldn't find a taxi in Paris on a snowy evening.",
            logoKey = "uber",
            imageUrl = buildCdnUrl("Travel & Airlines", "uber.webp")
        ),

        // ==================== 8. SPORTS ====================
        QuizLevel(
            id = "sports_1",
            packId = "sports",
            levelNumber = 1,
            answer = "NBA",
            hintSentence = "Premier professional basketball league with a white silhouette dribbling on red and blue",
            triviaFact = "The famous NBA silhouette is widely recognized as Hall of Fame Lakers legend Jerry West.",
            logoKey = "nba",
            imageUrl = buildCdnUrl("Sports", "nba.webp")
        ),
        QuizLevel(
            id = "sports_2",
            packId = "sports",
            levelNumber = 2,
            answer = "FIFA",
            hintSentence = "International governing body of association football and organizer of the World Cup",
            triviaFact = "Founded in Paris in 1904, FIFA organizes the Men's and Women's World Cups, watched by over 5 billion spectators.",
            logoKey = "fifa",
            imageUrl = buildCdnUrl("Sports", "fifa.webp")
        ),
        QuizLevel(
            id = "sports_3",
            packId = "sports",
            levelNumber = 3,
            answer = "NFL",
            hintSentence = "America's premier football league represented by a blue and red shield with eight stars",
            triviaFact = "The eight stars in the NFL crest represent the league's eight divisions (AFC/NFC North, South, East, West).",
            logoKey = "nfl",
            imageUrl = buildCdnUrl("Sports", "nfl.webp")
        ),
        QuizLevel(
            id = "sports_4",
            packId = "sports",
            levelNumber = 4,
            answer = "REDBULL",
            hintSentence = "Energy drink and extreme sports giant with two charging red bulls before a golden sun",
            triviaFact = "Red Bull owns top Formula 1 racing teams, football clubs, and world-record extreme sports events.",
            logoKey = "redbull",
            imageUrl = buildCdnUrl("Sports", "redbull.webp")
        ),
        QuizLevel(
            id = "sports_5",
            packId = "sports",
            levelNumber = 5,
            answer = "SPALDING",
            hintSentence = "Sporting goods manufacturer famous for iconic basketballs and baseball equipment",
            triviaFact = "Founded in 1876 by Boston Red Stockings pitcher A.G. Spalding, creating the first official American League baseball.",
            logoKey = "spalding",
            imageUrl = buildCdnUrl("Sports", "spalding.webp")
        ),
        QuizLevel(
            id = "sports_6",
            packId = "sports",
            levelNumber = 6,
            answer = "WILSON",
            hintSentence = "Historic sports brand with a red script W, official ball of the NFL and NBA",
            triviaFact = "Wilson has handcrafted the official NFL football (nicknamed 'The Duke') in Ada, Ohio continuously since 1955.",
            logoKey = "wilson",
            imageUrl = buildCdnUrl("Sports", "wilson.webp")
        ),
        QuizLevel(
            id = "sports_7",
            packId = "sports",
            levelNumber = 7,
            answer = "MLB",
            hintSentence = "Major League Baseball emblem featuring a batter silhouette on blue and red",
            triviaFact = "Designed in 1968 by Jerry Dior to commemorate professional baseball's 100th anniversary.",
            logoKey = "mlb",
            imageUrl = buildCdnUrl("Sports", "mlb.webp")
        ),
        QuizLevel(
            id = "sports_8",
            packId = "sports",
            levelNumber = 8,
            answer = "REEBOK",
            hintSentence = "Fitness and sports gear brand with the iconic Vector chevron mark",
            triviaFact = "The name Reebok comes from the Afrikaans word 'rhebok', an agile, swift South African antelope.",
            logoKey = "reebok",
            imageUrl = buildCdnUrl("Sports", "reebok.webp")
        ),
        QuizLevel(
            id = "sports_9",
            packId = "sports",
            levelNumber = 9,
            answer = "F1",
            hintSentence = "Pinnacle of motorsport featuring a sleek italic letter F and speed trail number 1",
            triviaFact = "Formula 1 began its official World Championship in 1950 at Silverstone Circuit in the United Kingdom.",
            logoKey = "f1",
            imageUrl = buildCdnUrl("Sports", "f1.webp")
        ),
        QuizLevel(
            id = "sports_10",
            packId = "sports",
            levelNumber = 10,
            answer = "OLYMPICS",
            hintSentence = "World's greatest multi-sport event symbolized by five interlocking colored rings",
            triviaFact = "Designed by Pierre de Coubertin in 1913, the five rings represent the inhabited continents united by Olympism.",
            logoKey = "olympics",
            imageUrl = buildCdnUrl("Sports", "olympics.webp")
        ),

        // ==================== 9. BEAUTY & PERSONAL CARE ====================
        QuizLevel(
            id = "beauty_personal_care_1",
            packId = "beauty_personal_care",
            levelNumber = 1,
            answer = "LOREAL",
            hintSentence = "French cosmetics and beauty leader with the slogan 'Because You're Worth It'",
            triviaFact = "Founded in 1909 by chemist Eugène Schueller, who formulated one of the world's first safe synthetic hair dyes.",
            logoKey = "loreal",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "loreal.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_2",
            packId = "beauty_personal_care",
            levelNumber = 2,
            answer = "NIVEA",
            hintSentence = "Skincare brand celebrated worldwide for its iconic blue tin creme",
            triviaFact = "The name Nivea is derived from the Latin word 'niveus', meaning 'snow-white', reflecting its pure white creme.",
            logoKey = "nivea",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "nivea.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_3",
            packId = "beauty_personal_care",
            levelNumber = 3,
            answer = "SEPHORA",
            hintSentence = "French multinational prestige beauty retailer with black and white striped branding",
            triviaFact = "The name Sephora is a combination of the Greek word 'sephos' (beauty) and the biblical name Zipporah.",
            logoKey = "sephora",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "sephora.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_4",
            packId = "beauty_personal_care",
            levelNumber = 4,
            answer = "DOVE",
            hintSentence = "Personal care brand known for beauty bars and a golden bird silhouette",
            triviaFact = "Dove launched in the US in 1957 with a patented cleansing bar containing one-quarter moisturizing cream.",
            logoKey = "dove",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "dove.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_5",
            packId = "beauty_personal_care",
            levelNumber = 5,
            answer = "GILLETTE",
            hintSentence = "Shaving razor pioneer: 'The Best a Man Can Get', with clean angled typography",
            triviaFact = "King C. Gillette invented the world's first disposable safety razor blade in 1901.",
            logoKey = "gillette",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "gillette.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_6",
            packId = "beauty_personal_care",
            levelNumber = 6,
            answer = "COLGATE",
            hintSentence = "Oral hygiene and toothpaste leader featuring a bright red and white banner with a smile",
            triviaFact = "William Colgate founded the company in NYC in 1806; Colgate introduced toothpaste in collapsible tubes in 1896.",
            logoKey = "colgate",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "colgate.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_7",
            packId = "beauty_personal_care",
            levelNumber = 7,
            answer = "MAC",
            hintSentence = "Professional makeup artistry cosmetics brand celebrated for bold lipsticks",
            triviaFact = "Founded in Toronto in 1984 by Frank Toskan and Frank Angelo to provide durable makeup for professional studio lighting.",
            logoKey = "mac",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "mac.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_8",
            packId = "beauty_personal_care",
            levelNumber = 8,
            answer = "OLAY",
            hintSentence = "Global skincare and anti-aging face creams brand with a refined cameo silhouette",
            triviaFact = "Formulated in 1952 by South African chemist Graham Wulff under the original name 'Oil of Olay'.",
            logoKey = "olay",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "olay.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_9",
            packId = "beauty_personal_care",
            levelNumber = 9,
            answer = "CLINIQUE",
            hintSentence = "Dermatologist-developed prestige skincare line with a minimalist silver letter C",
            triviaFact = "Founded in 1968 by Evelyn Lauder as the world's first 100% allergy-tested, fragrance-free cosmetics brand.",
            logoKey = "clinique",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "clinique.webp")
        ),
        QuizLevel(
            id = "beauty_personal_care_10",
            packId = "beauty_personal_care",
            levelNumber = 10,
            answer = "PANTENE",
            hintSentence = "Hair care and shampoo brand named for its panthenol vitamin formula",
            triviaFact = "Created in Switzerland in 1945, inspired by panthenol discoveries during WWII medical research on skin therapies.",
            logoKey = "pantene",
            imageUrl = buildCdnUrl("Beauty & Personal Care", "pantene.webp")
        ),

        // ==================== 10. RETAIL & SUPERMARKETS ====================
        QuizLevel(
            id = "retail_supermarkets_1",
            packId = "retail_supermarkets",
            levelNumber = 1,
            answer = "AMAZON",
            hintSentence = "E-commerce giant with an orange smile arrow linking A to Z",
            triviaFact = "Started in Jeff Bezos's garage in 1994 as an online bookstore before expanding to 'the everything store'.",
            logoKey = "amazon",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "amazon.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_2",
            packId = "retail_supermarkets",
            levelNumber = 2,
            answer = "WALMART",
            hintSentence = "Superstore retail giant featuring a bright yellow six-petal spark symbol",
            triviaFact = "Sam Walton opened the first Walmart discount city store in Rogers, Arkansas in 1962.",
            logoKey = "walmart",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "walmart.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_3",
            packId = "retail_supermarkets",
            levelNumber = 3,
            answer = "TARGET",
            hintSentence = "Retail chain famous for its red and white concentric bullseye rings",
            triviaFact = "Target's bullseye logo has over 96% recognition across the US and was designed in 1962.",
            logoKey = "target",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "target.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_4",
            packId = "retail_supermarkets",
            levelNumber = 4,
            answer = "COSTCO",
            hintSentence = "Membership warehouse club famous for bulk shopping and wholesale savings",
            triviaFact = "Costco opened its first warehouse in Seattle in 1983; its $1.50 hot dog and soda combo price has stayed unchanged since 1985.",
            logoKey = "costco",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "costco.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_5",
            packId = "retail_supermarkets",
            levelNumber = 5,
            answer = "IKEA",
            hintSentence = "Swedish flat-pack furniture empire with bold blue letters in a yellow ellipse",
            triviaFact = "IKEA is an acronym for Ingvar Kamprad (founder), Elmtaryd (family farm), and Agunnaryd (nearby parish).",
            logoKey = "ikea",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "ikea.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_6",
            packId = "retail_supermarkets",
            levelNumber = 6,
            answer = "EBAY",
            hintSentence = "Pioneering online auction and consumer shopping marketplace",
            triviaFact = "The first item ever sold on eBay in 1995 was a broken laser pointer purchased for $14.83 by a collector.",
            logoKey = "ebay",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "ebay.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_7",
            packId = "retail_supermarkets",
            levelNumber = 7,
            answer = "ALDI",
            hintSentence = "German discount supermarket chain with clean striped blue letter A badge",
            triviaFact = "Founded in Essen in 1946 by brothers Karl and Theo Albrecht; ALDI stands for 'Albrecht Diskont'.",
            logoKey = "aldi",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "aldi.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_8",
            packId = "retail_supermarkets",
            levelNumber = 8,
            answer = "BESTBUY",
            hintSentence = "Consumer electronics retail chain with a large bright yellow price tag logo",
            triviaFact = "Founded in Minnesota in 1966 as 'Sound of Music' before rebranding to Best Buy in 1983.",
            logoKey = "bestbuy",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "bestbuy.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_9",
            packId = "retail_supermarkets",
            levelNumber = 9,
            answer = "SEVENELEVEN",
            hintSentence = "Convenience store chain famous for Slurpees and a lowercase n in its logo",
            triviaFact = "Named 7-Eleven in 1946 to advertise its then-unprecedented operating hours: 7 a.m. to 11 p.m., 7 days a week.",
            logoKey = "seveneleven",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "seveneleven.webp")
        ),
        QuizLevel(
            id = "retail_supermarkets_10",
            packId = "retail_supermarkets",
            levelNumber = 10,
            answer = "HOMEDEPOT",
            hintSentence = "Home improvement retailer recognized by an angled orange square stencil",
            triviaFact = "Founded in Atlanta in 1978 by Bernie Marcus and Arthur Blank to create massive superstores for DIY homeowners.",
            logoKey = "homedepot",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "homedepot.webp")
        ),

        // ==================== 11. SOCIAL MEDIA ====================
        QuizLevel(
            id = "social_media_1",
            packId = "social_media",
            levelNumber = 1,
            answer = "FACEBOOK",
            hintSentence = "World's largest social network recognized by a white lowercase f on blue",
            triviaFact = "Launched in 2004 by Mark Zuckerberg in his Harvard dorm room, initially restricted to university students.",
            logoKey = "facebook",
            imageUrl = buildCdnUrl("Social Media", "facebook.webp")
        ),
        QuizLevel(
            id = "social_media_2",
            packId = "social_media",
            levelNumber = 2,
            answer = "INSTAGRAM",
            hintSentence = "Photo and video sharing app with a sunset gradient camera glyph",
            triviaFact = "Launched in October 2010 by Kevin Systrom and Mike Krieger, reaching 25,000 users on its very first day.",
            logoKey = "instagram",
            imageUrl = buildCdnUrl("Social Media", "instagram.webp")
        ),
        QuizLevel(
            id = "social_media_3",
            packId = "social_media",
            levelNumber = 3,
            answer = "TIKTOK",
            hintSentence = "Short-form mobile video platform with a cyan and red chromatic aberration musical note",
            triviaFact = "Launched internationally in 2017 by ByteDance, becoming one of the fastest apps to surpass 1 billion active users.",
            logoKey = "tiktok",
            imageUrl = buildCdnUrl("Social Media", "tiktok.webp")
        ),
        QuizLevel(
            id = "social_media_4",
            packId = "social_media",
            levelNumber = 4,
            answer = "TWITTER",
            hintSentence = "Microblogging and news platform famous for the soaring blue silhouette bird",
            triviaFact = "Twitter's original blue bird logo was named 'Larry' in honor of Boston Celtics legend Larry Bird.",
            logoKey = "twitter",
            imageUrl = buildCdnUrl("Social Media", "twitter.webp")
        ),
        QuizLevel(
            id = "social_media_5",
            packId = "social_media",
            levelNumber = 5,
            answer = "SNAPCHAT",
            hintSentence = "Ephemeral messaging app featuring a playful white ghost on a bright yellow field",
            triviaFact = "The friendly ghost mascot is named 'Ghostface Chillah', inspired by Wu-Tang Clan member Ghostface Killah.",
            logoKey = "snapchat",
            imageUrl = buildCdnUrl("Social Media", "snapchat.webp")
        ),
        QuizLevel(
            id = "social_media_6",
            packId = "social_media",
            levelNumber = 6,
            answer = "YOUTUBE",
            hintSentence = "World's largest video sharing platform featuring a white play button on a red badge",
            triviaFact = "The first video ever uploaded was 'Me at the zoo', posted by co-founder Jawed Karim on April 23, 2005.",
            logoKey = "youtube",
            imageUrl = buildCdnUrl("Social Media", "youtube.webp")
        ),
        QuizLevel(
            id = "social_media_7",
            packId = "social_media",
            levelNumber = 7,
            answer = "WHATSAPP",
            hintSentence = "Global instant messaging platform with a telephone receiver inside a speech bubble",
            triviaFact = "Founded in 2009 by former Yahoo employees Jan Koum and Brian Acton to replace expensive international SMS.",
            logoKey = "whatsapp",
            imageUrl = buildCdnUrl("Social Media", "whatsapp.webp")
        ),
        QuizLevel(
            id = "social_media_8",
            packId = "social_media",
            levelNumber = 8,
            answer = "LINKEDIN",
            hintSentence = "Professional networking platform with a clean blue square letter 'in'",
            triviaFact = "Founded in Reid Hoffman's living room in 2002, LinkedIn connects over 1 billion professionals globally.",
            logoKey = "linkedin",
            imageUrl = buildCdnUrl("Social Media", "linkedin.webp")
        ),
        QuizLevel(
            id = "social_media_9",
            packId = "social_media",
            levelNumber = 9,
            answer = "REDDIT",
            hintSentence = "Community discussion forums: 'the front page of the internet', with alien Snoo",
            triviaFact = "The orange-eyed alien mascot is named Snoo, a shorthand play on 'What's new?'.",
            logoKey = "reddit",
            imageUrl = buildCdnUrl("Social Media", "reddit.webp")
        ),
        QuizLevel(
            id = "social_media_10",
            packId = "social_media",
            levelNumber = 10,
            answer = "PINTEREST",
            hintSentence = "Visual discovery engine and digital moodboard platform with a script red P pin",
            triviaFact = "Founded in 2010 by Ben Silbermann, Evan Sharp, and Paul Sciarra, designed as a digital pinboard for personal collections.",
            logoKey = "pinterest",
            imageUrl = buildCdnUrl("Social Media", "pinterest.webp")
        ),

        // ==================== 12. HEALTH & PHARMA ====================
        QuizLevel(
            id = "health_pharma_1",
            packId = "health_pharma",
            levelNumber = 1,
            answer = "PFIZER",
            hintSentence = "Global biopharmaceutical titan with a blue double helix ribbon emblem",
            triviaFact = "Founded in Brooklyn in 1849 by cousins Charles Pfizer and Charles Erhart as a fine-chemicals manufacturer.",
            logoKey = "pfizer",
            imageUrl = buildCdnUrl("Health & Pharma", "pfizer.webp")
        ),
        QuizLevel(
            id = "health_pharma_2",
            packId = "health_pharma",
            levelNumber = 2,
            answer = "JOHNSON",
            hintSentence = "Healthcare and consumer health pioneer famous for baby care and red cursive signature",
            triviaFact = "Founded in New Brunswick, NJ in 1886; the red cursive logo is based directly on co-founder James Wood Johnson's signature.",
            logoKey = "johnson",
            imageUrl = buildCdnUrl("Health & Pharma", "johnson.webp")
        ),
        QuizLevel(
            id = "health_pharma_3",
            packId = "health_pharma",
            levelNumber = 3,
            answer = "MODERNA",
            hintSentence = "Biotechnology company pioneering transformative mRNA therapeutic vaccines",
            triviaFact = "The company name Moderna is a portmanteau of 'Modified' and 'RNA', founded in Cambridge, Massachusetts in 2010.",
            logoKey = "moderna",
            imageUrl = buildCdnUrl("Health & Pharma", "moderna.webp")
        ),
        QuizLevel(
            id = "health_pharma_4",
            packId = "health_pharma",
            levelNumber = 4,
            answer = "BAYER",
            hintSentence = "Pharmaceutical and life sciences company famous for the interlocking Bayer Cross",
            triviaFact = "The famous Bayer Cross was registered in 1904, appearing stamped on Aspirin tablets for over a century.",
            logoKey = "bayer",
            imageUrl = buildCdnUrl("Health & Pharma", "bayer.webp")
        ),
        QuizLevel(
            id = "health_pharma_5",
            packId = "health_pharma",
            levelNumber = 5,
            answer = "ROCHE",
            hintSentence = "Swiss healthcare and diagnostics leader with a blue hexagonal logo",
            triviaFact = "Founded in Basel, Switzerland in 1896 by Fritz Hoffmann-La Roche, pioneering standardized industrial medicine.",
            logoKey = "roche",
            imageUrl = buildCdnUrl("Health & Pharma", "roche.webp")
        ),
        QuizLevel(
            id = "health_pharma_6",
            packId = "health_pharma",
            levelNumber = 6,
            answer = "NOVARTIS",
            hintSentence = "Swiss pharmaceutical corporation symbolized by an orange and blue mortar flame",
            triviaFact = "Novartis was formed in 1996 through the historic merger of Swiss chemical-pharma pioneers Ciba-Geigy and Sandoz.",
            logoKey = "novartis",
            imageUrl = buildCdnUrl("Health & Pharma", "novartis.webp")
        ),
        QuizLevel(
            id = "health_pharma_7",
            packId = "health_pharma",
            levelNumber = 7,
            answer = "ASTRAZENECA",
            hintSentence = "British-Swedish pharmaceutical leader in oncology, cardiovascular, and respiratory medicines",
            triviaFact = "Formed in 1999 through the union of Sweden's Astra AB and the UK's Zeneca Group.",
            logoKey = "astrazeneca",
            imageUrl = buildCdnUrl("Health & Pharma", "astrazeneca.webp")
        ),
        QuizLevel(
            id = "health_pharma_8",
            packId = "health_pharma",
            levelNumber = 8,
            answer = "CVS",
            hintSentence = "Retail pharmacy and healthcare chain with an iconic red geometric heart",
            triviaFact = "CVS initially stood for 'Consumer Value Stores', opening its first location in Lowell, Massachusetts in 1963.",
            logoKey = "cvs",
            imageUrl = buildCdnUrl("Health & Pharma", "cvs.webp")
        ),
        QuizLevel(
            id = "health_pharma_9",
            packId = "health_pharma",
            levelNumber = 9,
            answer = "WALGREENS",
            hintSentence = "American neighborhood pharmacy chain known for the flowing red cursive script W",
            triviaFact = "Founded in Chicago in 1901 by Charles R. Walgreen, popularizing the malted milkshake at its lunch counters in 1922.",
            logoKey = "walgreens",
            imageUrl = buildCdnUrl("Health & Pharma", "walgreens.webp")
        ),
        QuizLevel(
            id = "health_pharma_10",
            packId = "health_pharma",
            levelNumber = 10,
            answer = "SANOFI",
            hintSentence = "French healthcare and pharmaceutical company with a stylized dove and water droplet",
            triviaFact = "Headquartered in Paris, Sanofi is one of the world's leading providers of pediatric vaccines and diabetes treatments.",
            logoKey = "sanofi",
            imageUrl = buildCdnUrl("Health & Pharma", "sanofi.webp")
        ),

        // ==================== 13. ENERGY & TELECOM ====================
        QuizLevel(
            id = "energy_telecom_1",
            packId = "energy_telecom",
            levelNumber = 1,
            answer = "SHELL",
            hintSentence = "Global energy and oil multinational symbolized by a red and yellow scallop seashell",
            triviaFact = "The company began in 1833 in London selling decorative sea shells imported from the Far East before moving into oil.",
            logoKey = "shell",
            imageUrl = buildCdnUrl("Energy & Telecom", "shell.webp")
        ),
        QuizLevel(
            id = "energy_telecom_2",
            packId = "energy_telecom",
            levelNumber = 2,
            answer = "VODAFONE",
            hintSentence = "British multinational telecom giant recognized by a bold red speechmark inside a circle",
            triviaFact = "The name Vodafone is a portmanteau of 'Voice', 'Data', and 'Phone', established in Newbury, UK in 1982.",
            logoKey = "vodafone",
            imageUrl = buildCdnUrl("Energy & Telecom", "vodafone.webp")
        ),
        QuizLevel(
            id = "energy_telecom_3",
            packId = "energy_telecom",
            levelNumber = 3,
            answer = "VERIZON",
            hintSentence = "Major American telecommunications provider with a distinctive red checkmark",
            triviaFact = "The name Verizon blends the Latin word 'veritas' (truth) with the word 'horizon', created after the Bell Atlantic-GTE merger.",
            logoKey = "verizon",
            imageUrl = buildCdnUrl("Energy & Telecom", "verizon.webp")
        ),
        QuizLevel(
            id = "energy_telecom_4",
            packId = "energy_telecom",
            levelNumber = 4,
            answer = "BP",
            hintSentence = "Global energy supermajor represented by the green and yellow Helios sunburst",
            triviaFact = "The Helios logo was named after the ancient Greek sun god, symbolizing energy in all its varied forms.",
            logoKey = "bp",
            imageUrl = buildCdnUrl("Energy & Telecom", "bp.webp")
        ),
        QuizLevel(
            id = "energy_telecom_5",
            packId = "energy_telecom",
            levelNumber = 5,
            answer = "CHEVRON",
            hintSentence = "Multinational energy corporation with blue and red parallel chevron stripes",
            triviaFact = "Traces its origin to the Pacific Coast Oil Company founded in California in 1879, later becoming Standard Oil of California.",
            logoKey = "chevron",
            imageUrl = buildCdnUrl("Energy & Telecom", "chevron.webp")
        ),
        QuizLevel(
            id = "energy_telecom_6",
            packId = "energy_telecom",
            levelNumber = 6,
            answer = "ATT",
            hintSentence = "American telecommunications conglomerate recognized by a glowing blue striped globe",
            triviaFact = "Traces its lineage to Alexander Graham Bell, who invented the telephone and founded the Bell Telephone Company in 1877.",
            logoKey = "att",
            imageUrl = buildCdnUrl("Energy & Telecom", "att.webp")
        ),
        QuizLevel(
            id = "energy_telecom_7",
            packId = "energy_telecom",
            levelNumber = 7,
            answer = "EXXON",
            hintSentence = "Energy titan formed from Standard Oil, famous for interlocking double red Xs",
            triviaFact = "The interlocking double Xs were designed by Raymond Loewy in 1966 to create a distinctive, unforgettable trademark.",
            logoKey = "exxon",
            imageUrl = buildCdnUrl("Energy & Telecom", "exxon.webp")
        ),
        QuizLevel(
            id = "energy_telecom_8",
            packId = "energy_telecom",
            levelNumber = 8,
            answer = "TMOBILE",
            hintSentence = "Wireless carrier leader celebrated worldwide for its vibrant magenta brand color",
            triviaFact = "T-Mobile's iconic magenta color is a federally registered trademark protected in the telecommunications sector.",
            logoKey = "tmobile",
            imageUrl = buildCdnUrl("Energy & Telecom", "tmobile.webp")
        ),
        QuizLevel(
            id = "energy_telecom_9",
            packId = "energy_telecom",
            levelNumber = 9,
            answer = "TOTAL",
            hintSentence = "French integrated multi-energy supermajor with a multi-colored circular wind ribbon",
            triviaFact = "Founded in 1924 as the Compagnie française des pétroles after WWI, recently rebranding to TotalEnergies.",
            logoKey = "total",
            imageUrl = buildCdnUrl("Energy & Telecom", "total.webp")
        ),
        QuizLevel(
            id = "energy_telecom_10",
            packId = "energy_telecom",
            levelNumber = 10,
            answer = "ORANGE",
            hintSentence = "Global telecommunications and digital services network with a square orange box",
            triviaFact = "Originally launched in the UK in 1994 with the historic tagline: 'The future's bright, the future's Orange.'",
            logoKey = "orange",
            imageUrl = buildCdnUrl("Energy & Telecom", "orange.webp")
        )
    )

    private val dynamicLevelsList = java.util.concurrent.CopyOnWriteArrayList<QuizLevel>(bundledLevels)

    val allLevels: List<QuizLevel>
        get() = dynamicLevelsList.toList()

    fun updateLevels(newLevels: List<QuizLevel>) {
        dynamicLevelsList.clear()
        dynamicLevelsList.addAll(newLevels)
    }

    fun addLevels(newLevels: List<QuizLevel>) {
        val currentIds = dynamicLevelsList.map { it.id }.toSet()
        val toAdd = newLevels.filter { it.id !in currentIds }
        if (toAdd.isNotEmpty()) {
            dynamicLevelsList.addAll(toAdd)
        }
    }

    fun getLevelById(id: String): QuizLevel? = dynamicLevelsList.find { it.id == id }

    fun getLevelsForPack(packId: String): List<QuizLevel> =
        dynamicLevelsList.filter { it.packId == packId }.sortedBy { it.levelNumber }

    /**
     * Determines whether a level is completed, unlocked, eligible for ad-unlock, or strictly locked.
     * Rule: Every level (whether free or watch-ad) can ONLY be unlocked if the previous level is completed.
     * Level 1 of any pack is always unlocked by default.
     */
    fun getLevelLockStatus(
        level: QuizLevel,
        allProgress: List<LevelProgressEntity>,
        isAdminMode: Boolean = false
    ): LevelLockStatus {
        val progress = allProgress.find { it.id == level.id }
        val isCompleted = progress?.isCompleted == true

        if (isAdminMode) {
            return LevelLockStatus(
                isCompleted = isCompleted,
                isUnlocked = true,
                isAdGated = false,
                isStrictlyLocked = false,
                requiredPreviousLevel = null
            )
        }

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
     * - In Admin mode: All levels are visible and accessible.
     * - In User mode: Level 1 is always shown. Level N (where N > 1) is ONLY shown when Level N-1 has been passed (completed).
     */
    fun getVisibleLevelsForPack(
        packId: String,
        allProgress: List<LevelProgressEntity>,
        isAdminMode: Boolean = false
    ): List<QuizLevel> {
        val packLevels = getLevelsForPack(packId).sortedBy { it.levelNumber }
        if (isAdminMode) {
            return packLevels
        }
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
