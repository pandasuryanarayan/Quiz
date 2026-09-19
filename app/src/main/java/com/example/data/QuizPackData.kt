package com.example.data

import java.net.URLEncoder
import java.util.concurrent.CopyOnWriteArrayList

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
        // ==================== AUTOMOTIVE (69 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "automotive_1",
            packId = "automotive",
            levelNumber = 1,
            answer = "TOYOTA",
            hintSentence = "World's top auto seller with three overlapping ellipses forming a T",
            triviaFact = "The three ovals symbolize the heart of the customer, the heart of the car, and boundless innovation.",
            logoKey = "toyota",
            imageUrl = buildCdnUrl("Automotive", "Toyota.webp"),
            originalName = "Toyota",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_2",
            packId = "automotive",
            levelNumber = 2,
            answer = "BMW",
            hintSentence = "Bavarian automotive icon with the blue and white checkered roundel",
            triviaFact = "The blue and white quarters represent the state colors of Bavaria, Germany.",
            logoKey = "bmw",
            imageUrl = buildCdnUrl("Automotive", "Bmw.webp"),
            originalName = "BMW",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_3",
            packId = "automotive",
            levelNumber = 3,
            answer = "FERRARI",
            hintSentence = "Italian supercar legend with the prancing horse on a Modena yellow shield",
            triviaFact = "Enzo Ferrari was given the prancing horse emblem by the mother of Italian WWI fighter ace Francesco Baracca.",
            logoKey = "ferrari",
            imageUrl = buildCdnUrl("Automotive", "Ferrari.webp"),
            originalName = "Ferrari",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_4",
            packId = "automotive",
            levelNumber = 4,
            answer = "HONDA",
            hintSentence = "Japanese automotive leader recognized by a polished silver H badge",
            triviaFact = "Honda has been the world's largest motorcycle manufacturer since 1959.",
            logoKey = "honda",
            imageUrl = buildCdnUrl("Automotive", "Honda.webp"),
            originalName = "Honda",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_5",
            packId = "automotive",
            levelNumber = 5,
            answer = "TESLA",
            hintSentence = "World electric vehicle leader with a stylized T cross-section of an electric motor",
            triviaFact = "The T logo represents a cross-section of an electric induction motor rotor and stator.",
            logoKey = "tesla",
            imageUrl = buildCdnUrl("Automotive", "Tesla.webp"),
            originalName = "Tesla",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_6",
            packId = "automotive",
            levelNumber = 6,
            answer = "PORSCHE",
            hintSentence = "German sports car maker featuring Stuttgart's crest and prancing horse",
            triviaFact = "The Porsche crest is based on the coat of arms of the Free People's State of Württemberg.",
            logoKey = "porsche",
            imageUrl = buildCdnUrl("Automotive", "Porsche.webp"),
            originalName = "Porsche",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_7",
            packId = "automotive",
            levelNumber = 7,
            answer = "AUDI",
            hintSentence = "German luxury automaker recognized worldwide by four interlocking rings",
            triviaFact = "The four rings represent the merger of four independent car manufacturers forming Auto Union in 1932.",
            logoKey = "audi",
            imageUrl = buildCdnUrl("Automotive", "Audi.webp"),
            originalName = "Audi",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_8",
            packId = "automotive",
            levelNumber = 8,
            answer = "LAMBORGHINI",
            hintSentence = "Italian supercar icon symbolized by a raging golden bull on black shield",
            triviaFact = "Ferruccio Lamborghini chose the bull because Taurus was his zodiac sign.",
            logoKey = "lamborghini",
            imageUrl = buildCdnUrl("Automotive", "Lamborghini.webp"),
            originalName = "Lamborghini",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_9",
            packId = "automotive",
            levelNumber = 9,
            answer = "MERCEDES",
            hintSentence = "Pinnacle of German luxury with the three-pointed star in a circle",
            triviaFact = "The three points of the star represent Gottlieb Daimler's ambition for motorization on land, water, and air.",
            logoKey = "mercedes",
            imageUrl = buildCdnUrl("Automotive", "Mercedes.webp"),
            originalName = "Mercedes-Benz",
            alternateAnswers = listOf("MERCEDESBENZ")
        ),
        QuizLevel(
            id = "automotive_10",
            packId = "automotive",
            levelNumber = 10,
            answer = "FORD",
            hintSentence = "American automotive pioneer that revolutionized mass production with the blue oval",
            triviaFact = "Henry Ford introduced the moving assembly line in 1913, reducing Model T build time to 93 minutes.",
            logoKey = "ford",
            imageUrl = buildCdnUrl("Automotive", "Ford.webp"),
            originalName = "Ford",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_11",
            packId = "automotive",
            levelNumber = 11,
            answer = "HYUNDAI",
            hintSentence = "South Korean automotive titan with a slanted H representing a handshake",
            triviaFact = "The slanted H logo represents two people shaking hands: the company and the customer.",
            logoKey = "hyundai",
            imageUrl = buildCdnUrl("Automotive", "Hyundai.webp"),
            originalName = "Hyundai",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_12",
            packId = "automotive",
            levelNumber = 12,
            answer = "VOLKSWAGEN",
            hintSentence = "German automotive titan with stacked V and W encased in a circle",
            triviaFact = "Volkswagen literally translates to 'people's car' in German.",
            logoKey = "volkswagen",
            imageUrl = buildCdnUrl("Automotive", "Volkswagen.webp"),
            originalName = "Volkswagen",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_13",
            packId = "automotive",
            levelNumber = 13,
            answer = "NISSAN",
            hintSentence = "Major Japanese carmaker with a horizontal brand bar across a circle",
            triviaFact = "Nissan originated in 1911 as Kaishinsha Motor Car Works, producing the DAT car.",
            logoKey = "nissan",
            imageUrl = buildCdnUrl("Automotive", "Nissan.webp"),
            originalName = "Nissan",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_14",
            packId = "automotive",
            levelNumber = 14,
            answer = "JEEP",
            hintSentence = "Iconic off-road SUV brand famous for seven-slot vertical grille",
            triviaFact = "Originated as the Willys MB reconnaissance vehicle for the US military during World War II.",
            logoKey = "jeep",
            imageUrl = buildCdnUrl("Automotive", "Jeep.webp"),
            originalName = "Jeep",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_15",
            packId = "automotive",
            levelNumber = 15,
            answer = "MAZDA",
            hintSentence = "Japanese car manufacturer with winged M stylized inside an oval",
            triviaFact = "The name Mazda comes from Ahura Mazda, the ancient Persian god of wisdom and light.",
            logoKey = "mazda",
            imageUrl = buildCdnUrl("Automotive", "Mazda.webp"),
            originalName = "Mazda",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_16",
            packId = "automotive",
            levelNumber = 16,
            answer = "VOLVO",
            hintSentence = "Swedish safety innovator recognized by the ancient iron symbol and diagonal grille sash",
            triviaFact = "Volvo means 'I roll' in Latin, originally registered as a trademark for ball bearings.",
            logoKey = "volvo",
            imageUrl = buildCdnUrl("Automotive", "Volvo.webp"),
            originalName = "Volvo",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_17",
            packId = "automotive",
            levelNumber = 17,
            answer = "ASTONMARTIN",
            hintSentence = "British ultra-luxury sports car brand celebrated as James Bond's signature ride",
            triviaFact = "Aston Martin was founded in 1913 by Lionel Martin and Robert Bamford in London.",
            logoKey = "astonmartin",
            imageUrl = buildCdnUrl("Automotive", "Astonmartin.webp"),
            originalName = "Aston Martin",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_18",
            packId = "automotive",
            levelNumber = 18,
            answer = "BENTLEY",
            hintSentence = "British luxury grand tourer maker with a winged silver B emblem",
            triviaFact = "Founded in 1919 by W. O. Bentley in Cricklewood, North London.",
            logoKey = "bentley",
            imageUrl = buildCdnUrl("Automotive", "Bentley.webp"),
            originalName = "Bentley",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_19",
            packId = "automotive",
            levelNumber = 19,
            answer = "BUGATTI",
            hintSentence = "Legendary French hypercar manufacturer behind the Veyron and Chiron",
            triviaFact = "Founded in 1909 in Molsheim, Alsace by Italian-born industrial designer Ettore Bugatti.",
            logoKey = "bugatti",
            imageUrl = buildCdnUrl("Automotive", "Bugatti.webp"),
            originalName = "Bugatti",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_20",
            packId = "automotive",
            levelNumber = 20,
            answer = "ROLLSROYCE",
            hintSentence = "Quintessential British luxury crowned by the Spirit of Ecstasy mascot",
            triviaFact = "Founded in 1904 by Charles Rolls and Henry Royce after building the 10 hp car.",
            logoKey = "rollsroyce",
            imageUrl = buildCdnUrl("Automotive", "RollsRoyce.webp"),
            originalName = "Rolls-Royce",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_21",
            packId = "automotive",
            levelNumber = 21,
            answer = "MCLAREN",
            hintSentence = "British supercar builder born from Bruce McLaren's Formula 1 racing team",
            triviaFact = "McLaren built the legendary F1 in 1992, which remained the world's fastest naturally aspirated car.",
            logoKey = "mclaren",
            imageUrl = buildCdnUrl("Automotive", "McLaren.webp"),
            originalName = "McLaren",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_22",
            packId = "automotive",
            levelNumber = 22,
            answer = "MASERATI",
            hintSentence = "Italian luxury sports vehicle maker adorned with Neptune's trident",
            triviaFact = "The trident emblem was inspired by the Fountain of Neptune in Bologna's Piazza Maggiore.",
            logoKey = "maserati",
            imageUrl = buildCdnUrl("Automotive", "Maserati.webp"),
            originalName = "Maserati",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_23",
            packId = "automotive",
            levelNumber = 23,
            answer = "ALFAROMEO",
            hintSentence = "Italian luxury car maker known for the red cross and Biscione snake badge",
            triviaFact = "Founded in Milan in 1910, its emblem combines the cross of Milan and the serpent of the Visconti family.",
            logoKey = "alfaromeo",
            imageUrl = buildCdnUrl("Automotive", "Alfa Romeo.webp"),
            originalName = "Alfa Romeo",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_24",
            packId = "automotive",
            levelNumber = 24,
            answer = "BHARATBENZ",
            hintSentence = "Daimler truck and bus brand designed specifically for the Indian market",
            triviaFact = "Launched in 2012 by Daimler India Commercial Vehicles with headquarters in Oragadam, Chennai.",
            logoKey = "bharatbenz",
            imageUrl = buildCdnUrl("Automotive", "Bharatbenz.webp"),
            originalName = "BharatBenz",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_25",
            packId = "automotive",
            levelNumber = 25,
            answer = "BUICK",
            hintSentence = "Historic American premium brand featuring the tri-shield emblem",
            triviaFact = "Buick was the company that established General Motors in 1908.",
            logoKey = "buick",
            imageUrl = buildCdnUrl("Automotive", "Buick.webp"),
            originalName = "Buick",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_26",
            packId = "automotive",
            levelNumber = 26,
            answer = "BYD",
            hintSentence = "Global electric vehicle giant whose name stands for 'Build Your Dreams'",
            triviaFact = "Founded in Shenzhen in 1995 as a rechargeable battery factory before becoming an EV leader.",
            logoKey = "byd",
            imageUrl = buildCdnUrl("Automotive", "Byd.webp"),
            originalName = "BYD",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_27",
            packId = "automotive",
            levelNumber = 27,
            answer = "CADILLAC",
            hintSentence = "American luxury automotive pioneer symbolized by the crest of Antoine de la Mothe",
            triviaFact = "Cadillac was named after the French explorer who founded Detroit in 1701.",
            logoKey = "cadillac",
            imageUrl = buildCdnUrl("Automotive", "Cadillac.webp"),
            originalName = "Cadillac",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_28",
            packId = "automotive",
            levelNumber = 28,
            answer = "CHRYSLER",
            hintSentence = "Classic American automaker famous for the 300 sedan and winged crest",
            triviaFact = "Founded by Walter Chrysler in 1925 from the remains of the Maxwell Motor Company.",
            logoKey = "chrysler",
            imageUrl = buildCdnUrl("Automotive", "Chrysler.webp"),
            originalName = "Chrysler",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_29",
            packId = "automotive",
            levelNumber = 29,
            answer = "CUPRA",
            hintSentence = "Spanish high-performance electrified brand with a tribal bronze badge",
            triviaFact = "Originally SEAT's motorsport division, Cupra was launched as a standalone brand in 2018.",
            logoKey = "cupra",
            imageUrl = buildCdnUrl("Automotive", "Cupra.webp"),
            originalName = "Cupra",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_30",
            packId = "automotive",
            levelNumber = 30,
            answer = "DODGE",
            hintSentence = "American muscle car maker famous for the Charger, Challenger, and dual racing stripes",
            triviaFact = "Founded as the Dodge Brothers Company machine shop by Horace and John Dodge in 1900.",
            logoKey = "dodge",
            imageUrl = buildCdnUrl("Automotive", "Dodge.webp"),
            originalName = "Dodge",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_31",
            packId = "automotive",
            levelNumber = 31,
            answer = "FIAT",
            hintSentence = "Italy's largest automobile manufacturer famous for the retro 500 city car",
            triviaFact = "FIAT stands for Fabbrica Italiana Automobili Torino, established in 1899.",
            logoKey = "fiat",
            imageUrl = buildCdnUrl("Automotive", "Fiat.webp"),
            originalName = "Fiat",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_32",
            packId = "automotive",
            levelNumber = 32,
            answer = "GEELY",
            hintSentence = "Major multinational automotive group that owns Volvo Cars and Lotus",
            triviaFact = "Founded in 1986 by Li Shufu as a refrigerator parts maker before building cars in 1997.",
            logoKey = "geely",
            imageUrl = buildCdnUrl("Automotive", "Geely.webp"),
            originalName = "Geely",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_33",
            packId = "automotive",
            levelNumber = 33,
            answer = "GENESIS",
            hintSentence = "South Korean luxury vehicle brand with winged badge and crest grille",
            triviaFact = "Spun off from Hyundai Motor Group in 2015 into an independent global luxury marque.",
            logoKey = "genesis",
            imageUrl = buildCdnUrl("Automotive", "Genesis.webp"),
            originalName = "Genesis",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_34",
            packId = "automotive",
            levelNumber = 34,
            answer = "GMC",
            hintSentence = "American truck and utility brand with three bold red block letters",
            triviaFact = "GMC traces its roots to 1902 when Max Grabowsky founded the Rapid Motor Vehicle Company.",
            logoKey = "gmc",
            imageUrl = buildCdnUrl("Automotive", "Gmc.webp"),
            originalName = "GMC",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_35",
            packId = "automotive",
            levelNumber = 35,
            answer = "IVECO",
            hintSentence = "European commercial vehicle and truck manufacturer headquartered in Turin",
            triviaFact = "The name IVECO is an acronym for Industrial Vehicles Corporation, established in 1975.",
            logoKey = "iveco",
            imageUrl = buildCdnUrl("Automotive", "Iveco.webp"),
            originalName = "Iveco",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_36",
            packId = "automotive",
            levelNumber = 36,
            answer = "KIA",
            hintSentence = "South Korean automaker with a modern connected angular typography logo",
            triviaFact = "Founded in 1944 as a maker of steel tubing and bicycle parts.",
            logoKey = "kia",
            imageUrl = buildCdnUrl("Automotive", "Kia.webp"),
            originalName = "Kia",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_37",
            packId = "automotive",
            levelNumber = 37,
            answer = "KOENIGSEGG",
            hintSentence = "Swedish extreme hypercar maker with a gold and blue ghost shield emblem",
            triviaFact = "Founded in 1994 by Christian von Koenigsegg to create the world-class supercar.",
            logoKey = "koenigsegg",
            imageUrl = buildCdnUrl("Automotive", "Koenigsegg.webp"),
            originalName = "Koenigsegg",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_38",
            packId = "automotive",
            levelNumber = 38,
            answer = "LI",
            hintSentence = "Leading Chinese electric vehicle manufacturer specializing in range-extended SUVs",
            triviaFact = "Founded by Li Xiang in 2015, Li Auto is a pioneer of range-extended electric vehicles (EREVs).",
            logoKey = "li",
            imageUrl = buildCdnUrl("Automotive", "Li.webp"),
            originalName = "Li Auto",
            alternateAnswers = listOf("LIAUTO")
        ),
        QuizLevel(
            id = "automotive_39",
            packId = "automotive",
            levelNumber = 39,
            answer = "LINCOLN",
            hintSentence = "Luxury vehicle division of Ford characterized by a four-point star emblem",
            triviaFact = "Founded in 1917 by Henry Leland and named after Abraham Lincoln.",
            logoKey = "lincoln",
            imageUrl = buildCdnUrl("Automotive", "Lincoln.webp"),
            originalName = "Lincoln",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_40",
            packId = "automotive",
            levelNumber = 40,
            answer = "LOTUS",
            hintSentence = "British sports and racing car pioneer with yellow and green circular badge",
            triviaFact = "Founded in 1952 by Colin Chapman, famous for the philosophy 'Simplify, then add lightness'.",
            logoKey = "lotus",
            imageUrl = buildCdnUrl("Automotive", "Lotus.webp"),
            originalName = "Lotus",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_41",
            packId = "automotive",
            levelNumber = 41,
            answer = "LUCID",
            hintSentence = "American luxury electric car maker known for the Air luxury sedan",
            triviaFact = "Originally founded in 2007 as Atieva, focusing on EV batteries before building the Lucid Air.",
            logoKey = "lucid",
            imageUrl = buildCdnUrl("Automotive", "Lucid.webp"),
            originalName = "Lucid",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_42",
            packId = "automotive",
            levelNumber = 42,
            answer = "MAHINDRA",
            hintSentence = "Indian automotive giant celebrated for rugged SUVs like Thar and Scorpio",
            triviaFact = "Founded in Ludhiana in 1945 as Muhammad & Mahindra before becoming Mahindra & Mahindra.",
            logoKey = "mahindra",
            imageUrl = buildCdnUrl("Automotive", "Mahindra.webp"),
            originalName = "Mahindra",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_43",
            packId = "automotive",
            levelNumber = 43,
            answer = "MAN",
            hintSentence = "German commercial vehicle and diesel engine giant with a prowling lion crest",
            triviaFact = "MAN traces its heritage back to 1758, making it one of Europe's oldest engineering companies.",
            logoKey = "man",
            imageUrl = buildCdnUrl("Automotive", "Man.webp"),
            originalName = "MAN",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_44",
            packId = "automotive",
            levelNumber = 44,
            answer = "MG",
            hintSentence = "Historic British marque recognized by its octagonal badge, now global EV maker",
            triviaFact = "MG stands for Morris Garages, founded by Cecil Kimber in Oxford in the 1920s.",
            logoKey = "mg",
            imageUrl = buildCdnUrl("Automotive", "Mg.webp"),
            originalName = "MG",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_45",
            packId = "automotive",
            levelNumber = 45,
            answer = "MINI",
            hintSentence = "British icon of compact automotive design with a winged circular badge",
            triviaFact = "Designed by Sir Alec Issigonis in 1959 to beat a fuel crisis with front-wheel drive packaging.",
            logoKey = "mini",
            imageUrl = buildCdnUrl("Automotive", "Mini.webp"),
            originalName = "Mini",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_46",
            packId = "automotive",
            levelNumber = 46,
            answer = "MITSUBISHI",
            hintSentence = "Japanese conglomerate recognized by three red diamonds radiating from center",
            triviaFact = "Mitsubishi means 'three water chestnuts' or diamonds in Japanese.",
            logoKey = "mitsubishi",
            imageUrl = buildCdnUrl("Automotive", "Mitsubishi.webp"),
            originalName = "Mitsubishi",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_47",
            packId = "automotive",
            levelNumber = 47,
            answer = "NIO",
            hintSentence = "Chinese premium electric car company pioneer of battery swapping stations",
            triviaFact = "Nio's Chinese name 'Weilai' means 'Blue Sky Coming', symbolized in its split logo.",
            logoKey = "nio",
            imageUrl = buildCdnUrl("Automotive", "Nio.webp"),
            originalName = "Nio",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_48",
            packId = "automotive",
            levelNumber = 48,
            answer = "PAGANI",
            hintSentence = "Italian boutique hypercar atelier that created the Zonda and Huayra",
            triviaFact = "Founded in 1992 by Horacio Pagani, who previously managed Lamborghini's composites department.",
            logoKey = "pagani",
            imageUrl = buildCdnUrl("Automotive", "Pagani.webp"),
            originalName = "Pagani",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_49",
            packId = "automotive",
            levelNumber = 49,
            answer = "PEUGEOT",
            hintSentence = "French automaker symbolized since 1858 by a heraldic roaring lion",
            triviaFact = "Peugeot began in 1810 as a family-run grain mill that later made coffee grinders and bicycles.",
            logoKey = "peugeot",
            imageUrl = buildCdnUrl("Automotive", "Peugeot.webp"),
            originalName = "Peugeot",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_50",
            packId = "automotive",
            levelNumber = 50,
            answer = "POLESTAR",
            hintSentence = "Swedish performance electric brand featuring two offset chevron arrows",
            triviaFact = "Originally a Swedish touring car racing team before Volvo and Geely acquired it.",
            logoKey = "polestar",
            imageUrl = buildCdnUrl("Automotive", "Polestar.webp"),
            originalName = "Polestar",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_51",
            packId = "automotive",
            levelNumber = 51,
            answer = "RAM",
            hintSentence = "American heavy-duty pickup truck brand with a bighorn ram's head emblem",
            triviaFact = "Spun off from Dodge into an independent truck brand in 2009 by Chrysler Group.",
            logoKey = "ram",
            imageUrl = buildCdnUrl("Automotive", "Ram.webp"),
            originalName = "Ram",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_52",
            packId = "automotive",
            levelNumber = 52,
            answer = "RENAULT",
            hintSentence = "French automotive giant symbolized by a geometric silver diamond emblem",
            triviaFact = "The famous diamond logo first appeared on Renault car grilles in 1925.",
            logoKey = "renault",
            imageUrl = buildCdnUrl("Automotive", "Renault.webp"),
            originalName = "Renault",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_53",
            packId = "automotive",
            levelNumber = 53,
            answer = "RIMAC",
            hintSentence = "Croatian electric hypercar company that built the record-shattering Nevera",
            triviaFact = "Founded by Mate Rimac in 2009 in his garage after converting an old BMW E30 to electric.",
            logoKey = "rimac",
            imageUrl = buildCdnUrl("Automotive", "Rimac.webp"),
            originalName = "Rimac",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_54",
            packId = "automotive",
            levelNumber = 54,
            answer = "RIVIAN",
            hintSentence = "American adventure electric vehicle pioneer behind the R1T truck and R1S SUV",
            triviaFact = "Rivian's compass emblem represents adventures guided by four distinct directions.",
            logoKey = "rivian",
            imageUrl = buildCdnUrl("Automotive", "Rivian.webp"),
            originalName = "Rivian",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_55",
            packId = "automotive",
            levelNumber = 55,
            answer = "SAIC",
            hintSentence = "China's largest state-owned automotive manufacturing conglomerate",
            triviaFact = "SAIC Motor (Shanghai Automotive Industry Corp) dates back to the 1940s.",
            logoKey = "saic",
            imageUrl = buildCdnUrl("Automotive", "Saic.webp"),
            originalName = "SAIC",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_56",
            packId = "automotive",
            levelNumber = 56,
            answer = "SCANIA",
            hintSentence = "Swedish heavy truck and bus manufacturer featuring a red crowned griffin",
            triviaFact = "The crowned griffin is from the coat of arms of the southern Swedish province of Scania.",
            logoKey = "scania",
            imageUrl = buildCdnUrl("Automotive", "Scania.webp"),
            originalName = "Scania",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_57",
            packId = "automotive",
            levelNumber = 57,
            answer = "SEAT",
            hintSentence = "Spanish automobile manufacturer based in Martorell with a segmented chrome S",
            triviaFact = "Founded in 1950 by the Spanish state-owned industrial holding institute INI.",
            logoKey = "seat",
            imageUrl = buildCdnUrl("Automotive", "Seat.webp"),
            originalName = "SEAT",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_58",
            packId = "automotive",
            levelNumber = 58,
            answer = "SKODA",
            hintSentence = "Czech car manufacturer with the famous winged arrow in an emerald circle",
            triviaFact = "Founded in 1895 as Laurin & Klement, making Skoda one of the world's oldest automakers.",
            logoKey = "skoda",
            imageUrl = buildCdnUrl("Automotive", "Skoda.webp"),
            originalName = "Skoda",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_59",
            packId = "automotive",
            levelNumber = 59,
            answer = "SUBARU",
            hintSentence = "Japanese AWD automaker named after the Pleiades star cluster",
            triviaFact = "Subaru is the Japanese name for the Pleiades star cluster, six stars of which are visible to the naked eye.",
            logoKey = "subaru",
            imageUrl = buildCdnUrl("Automotive", "Subaru.webp"),
            originalName = "Subaru",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_60",
            packId = "automotive",
            levelNumber = 60,
            answer = "SUZUKI",
            hintSentence = "Japanese compact car and motorcycle leader with a sharp stylized S",
            triviaFact = "Michio Suzuki founded the company in 1909 as a manufacturer of silk weaving looms.",
            logoKey = "suzuki",
            imageUrl = buildCdnUrl("Automotive", "Suzuki.webp"),
            originalName = "Suzuki",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_61",
            packId = "automotive",
            levelNumber = 61,
            answer = "TATA",
            hintSentence = "Indian automotive giant behind the Harrier, Safari, and owner of JLR",
            triviaFact = "Tata Motors entered passenger cars with the fully indigenous Tata Indica in 1998.",
            logoKey = "tata",
            imageUrl = buildCdnUrl("Automotive", "Tata.webp"),
            originalName = "Tata",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_62",
            packId = "automotive",
            levelNumber = 62,
            answer = "VAUXHALL",
            hintSentence = "Historic British automobile manufacturer featuring a griffin holding a V banner",
            triviaFact = "The griffin emblem is derived from the coat of arms of Sir Falkes de Breauté, a 13th-century knight.",
            logoKey = "vauxhall",
            imageUrl = buildCdnUrl("Automotive", "Vauxhall.webp"),
            originalName = "Vauxhall",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_63",
            packId = "automotive",
            levelNumber = 63,
            answer = "VINFAST",
            hintSentence = "Vietnamese electric car maker featuring a prominent chrome V emblem",
            triviaFact = "Founded in 2017 by Vingroup, VinFast is Vietnam's first domestic automaker.",
            logoKey = "vinfast",
            imageUrl = buildCdnUrl("Automotive", "Vinfast.webp"),
            originalName = "VinFast",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_64",
            packId = "automotive",
            levelNumber = 64,
            answer = "XPENG",
            hintSentence = "Chinese smart electric vehicle maker with an X-shaped quad-blade emblem",
            triviaFact = "Founded in 2014 by He Xiaopeng, focusing on autonomous driving software and smart EVs.",
            logoKey = "xpeng",
            imageUrl = buildCdnUrl("Automotive", "Xpeng.webp"),
            originalName = "XPeng",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_65",
            packId = "automotive",
            levelNumber = 65,
            answer = "ZEEKR",
            hintSentence = "Premium electric mobility marque under Geely with a frameless dual-door badge",
            triviaFact = "Zeekr was founded in 2021 as Geely's high-end pure electric vehicle brand.",
            logoKey = "zeekr",
            imageUrl = buildCdnUrl("Automotive", "Zeekr.webp"),
            originalName = "Zeekr",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_66",
            packId = "automotive",
            levelNumber = 66,
            answer = "ABARTH",
            hintSentence = "Italian performance tuning marque recognized worldwide for the fiery scorpion crest",
            triviaFact = "Founded by Carlo Abarth in 1949 in Turin, renowned for turning compact chassis into rally winners.",
            logoKey = "abarth",
            imageUrl = buildCdnUrl("Automotive", "Abarth.webp"),
            originalName = "Abarth",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_67",
            packId = "automotive",
            levelNumber = 67,
            answer = "ALPINE",
            hintSentence = "French sports and racing car marque celebrated for the iconic rear-engine A110",
            triviaFact = "Founded in 1955 by Jean Rédélé, today Alpine powers France's Formula 1 racing operations.",
            logoKey = "alpine",
            imageUrl = buildCdnUrl("Automotive", "Alpine.webp"),
            originalName = "Alpine",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_68",
            packId = "automotive",
            levelNumber = 68,
            answer = "CATERHAM",
            hintSentence = "British specialist lightweight sports car maker famed for open-wheel Lotus Seven racers",
            triviaFact = "Produces minimalist track-focused cars honoring Colin Chapman's philosophy of adding lightness.",
            logoKey = "caterham",
            imageUrl = buildCdnUrl("Automotive", "Caterham.webp"),
            originalName = "Caterham",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "automotive_69",
            packId = "automotive",
            levelNumber = 69,
            answer = "CORVETTE",
            hintSentence = "America's sports car legend celebrated by the crossed racing and fleur-de-lis flags",
            triviaFact = "Produced across eight storied generations since 1953, evolving into a mid-engine supercar.",
            logoKey = "corvette",
            imageUrl = buildCdnUrl("Automotive", "Corvette.webp"),
            originalName = "Corvette",
            alternateAnswers = emptyList()
        ),
        // ==================== FOOD & BEVERAGES (13 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "food_beverage_1",
            packId = "food_beverage",
            levelNumber = 1,
            answer = "BURGERKING",
            hintSentence = "Fast food giant famous for flame-grilled Whoppers between red burger buns",
            triviaFact = "Founded in Jacksonville, Florida in 1953 as Insta-Burger King before adopting Burger King.",
            logoKey = "burgerking",
            imageUrl = buildCdnUrl("Food & Beverages", "Burgerking.webp"),
            originalName = "Burger King",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_2",
            packId = "food_beverage",
            levelNumber = 2,
            answer = "DOMINOS",
            hintSentence = "World's largest pizza delivery company with a red and blue domino tile",
            triviaFact = "The three dots on the domino represented the company's first three stores in Michigan.",
            logoKey = "dominos",
            imageUrl = buildCdnUrl("Food & Beverages", "dominos.webp"),
            originalName = "Domino's",
            alternateAnswers = listOf("DOMINO")
        ),
        QuizLevel(
            id = "food_beverage_3",
            packId = "food_beverage",
            levelNumber = 3,
            answer = "HALDIRAM",
            hintSentence = "Renowned Indian sweets, namkeen, and savory snacks manufacturer",
            triviaFact = "Started in 1937 as a tiny namkeen shop in Bikaner, Rajasthan by Ganga Bishan Agarwal.",
            logoKey = "haldiram",
            imageUrl = buildCdnUrl("Food & Beverages", "Haldiram.webp"),
            originalName = "Haldiram's",
            alternateAnswers = listOf("HALDIRAMS")
        ),
        QuizLevel(
            id = "food_beverage_4",
            packId = "food_beverage",
            levelNumber = 4,
            answer = "ITC",
            hintSentence = "Diversified Indian conglomerate behind Aashirvaad, Sunfeast, and Bingo!",
            triviaFact = "Founded in 1910 in Kolkata, today ITC is one of India's foremost private sector companies.",
            logoKey = "itc",
            imageUrl = buildCdnUrl("Food & Beverages", "Itc.webp"),
            originalName = "ITC",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_5",
            packId = "food_beverage",
            levelNumber = 5,
            answer = "KFC",
            hintSentence = "World-famous fried chicken chain featuring the portrait of Colonel Sanders",
            triviaFact = "Colonel Harland Sanders perfected his secret recipe of 11 herbs and spices in 1940.",
            logoKey = "kfc",
            imageUrl = buildCdnUrl("Food & Beverages", "kfc.webp"),
            originalName = "KFC",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_6",
            packId = "food_beverage",
            levelNumber = 6,
            answer = "MCDONALDS",
            hintSentence = "World's biggest fast-food burger franchise famous for the Golden Arches",
            triviaFact = "The Golden Arches were originally architectural elements of the very first franchised restaurant in 1953.",
            logoKey = "mcdonalds",
            imageUrl = buildCdnUrl("Food & Beverages", "McDonald's.webp"),
            originalName = "McDonald's",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_7",
            packId = "food_beverage",
            levelNumber = 7,
            answer = "MOTHERDAIRY",
            hintSentence = "Beloved Indian dairy brand known for milk, ice creams, and curd under Operation Flood",
            triviaFact = "Commissioned in 1974 as a wholly owned subsidiary of the National Dairy Development Board (NDDB).",
            logoKey = "motherdairy",
            imageUrl = buildCdnUrl("Food & Beverages", "MotherDairy.webp"),
            originalName = "Mother Dairy",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_8",
            packId = "food_beverage",
            levelNumber = 8,
            answer = "NESTLE",
            hintSentence = "Global food and beverage powerhouse symbolized by a bird in an oak nest",
            triviaFact = "Henri Nestlé created the famous bird's nest trademark in 1868 based on his family coat of arms.",
            logoKey = "nestle",
            imageUrl = buildCdnUrl("Food & Beverages", "Nestle.webp"),
            originalName = "Nestle",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_9",
            packId = "food_beverage",
            levelNumber = 9,
            answer = "PARLE",
            hintSentence = "Maker of the iconic Parle-G, the world's largest-selling biscuit brand",
            triviaFact = "Founded in 1929 in Vile Parle, Mumbai, producing biscuits that fueled freedom fighters during India's struggle.",
            logoKey = "parle",
            imageUrl = buildCdnUrl("Food & Beverages", "Parle.webp"),
            originalName = "Parle",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_10",
            packId = "food_beverage",
            levelNumber = 10,
            answer = "PEPSI",
            hintSentence = "Famous cola brand recognized by the red, white, and blue circular globe",
            triviaFact = "Created in 1893 by pharmacist Caleb Bradham in New Bern, North Carolina as 'Brad's Drink'.",
            logoKey = "pepsi",
            imageUrl = buildCdnUrl("Food & Beverages", "pepsi.webp"),
            originalName = "Pepsi",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_11",
            packId = "food_beverage",
            levelNumber = 11,
            answer = "PEPSICO",
            hintSentence = "Global food and beverage colossus behind Pepsi, Lay's, Quaker, and Gatorade",
            triviaFact = "Formed in 1965 through the merger of the Pepsi-Cola Company and Frito-Lay.",
            logoKey = "pepsico",
            imageUrl = buildCdnUrl("Food & Beverages", "Pepsico.webp"),
            originalName = "PepsiCo",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_12",
            packId = "food_beverage",
            levelNumber = 12,
            answer = "REDBULL",
            hintSentence = "Energy drink titan with two charging red bulls before a golden sun: 'Gives You Wings'",
            triviaFact = "Inspired by Krating Daeng, an energy drink from Thailand created by Chaleo Yoovidhya.",
            logoKey = "redbull",
            imageUrl = buildCdnUrl("Food & Beverages", "redbull.webp"),
            originalName = "Red Bull",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "food_beverage_13",
            packId = "food_beverage",
            levelNumber = 13,
            answer = "TACOBELL",
            hintSentence = "Mexican-inspired fast-food favorite with a purple ringing bell emblem: 'Live Más'",
            triviaFact = "Founded by Glen Bell in Downey, California in 1962, pioneering fast-food crunchy tacos.",
            logoKey = "tacobell",
            imageUrl = buildCdnUrl("Food & Beverages", "taco bell.webp"),
            originalName = "Taco Bell",
            alternateAnswers = emptyList()
        ),
        // ==================== TECHNOLOGY (9 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "technology_1",
            packId = "technology",
            levelNumber = 1,
            answer = "ADOBE",
            hintSentence = "Creative software powerhouse behind Photoshop, Premiere, and Illustrator with a red A",
            triviaFact = "Named after Adobe Creek in Los Altos, California, which ran behind co-founder John Warnock's house.",
            logoKey = "adobe",
            imageUrl = buildCdnUrl("Technology", "adobe.webp"),
            originalName = "Adobe",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_2",
            packId = "technology",
            levelNumber = 2,
            answer = "AMAZON",
            hintSentence = "E-commerce and cloud computing giant with an orange arrow smiling from A to Z",
            triviaFact = "The smiling arrow points from A to Z, showing that Amazon sells everything from A to Z.",
            logoKey = "amazon",
            imageUrl = buildCdnUrl("Technology", "amazon.webp"),
            originalName = "Amazon",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_3",
            packId = "technology",
            levelNumber = 3,
            answer = "APPLE",
            hintSentence = "Consumer electronics titan symbolized by a minimalist apple with a clean bite mark",
            triviaFact = "Rob Janoff designed the apple with a bite so people wouldn't confuse it with a cherry.",
            logoKey = "apple",
            imageUrl = buildCdnUrl("Technology", "Apple.webp"),
            originalName = "Apple",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_4",
            packId = "technology",
            levelNumber = 4,
            answer = "GOOGLE",
            hintSentence = "Global search and Android pioneer with a four-color G emblem",
            triviaFact = "The name Google originated from a misspelling of 'googol', the number 1 followed by 100 zeros.",
            logoKey = "google",
            imageUrl = buildCdnUrl("Technology", "google.webp"),
            originalName = "Google",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_5",
            packId = "technology",
            levelNumber = 5,
            answer = "MICROSOFT",
            hintSentence = "Global software giant recognized by a four-color window pane square",
            triviaFact = "Founded in 1975 by Bill Gates and Paul Allen, Microsoft is a portmanteau of microcomputer and software.",
            logoKey = "microsoft",
            imageUrl = buildCdnUrl("Technology", "Microsoft.webp"),
            originalName = "Microsoft",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_6",
            packId = "technology",
            levelNumber = 6,
            answer = "NVIDIA",
            hintSentence = "Semiconductor and AI computing leader recognized by a green all-seeing eye logo",
            triviaFact = "Founded in 1993 by Jensen Huang, Chris Malachowsky, and Curtis Priem in a Denny's restaurant.",
            logoKey = "nvidia",
            imageUrl = buildCdnUrl("Technology", "nvidia.webp"),
            originalName = "Nvidia",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_7",
            packId = "technology",
            levelNumber = 7,
            answer = "SAMSUNG",
            hintSentence = "South Korean technology powerhouse behind Galaxy smartphones and displays",
            triviaFact = "In Korean, the word 'Samsung' means 'three stars', representing greatness and eternity.",
            logoKey = "samsung",
            imageUrl = buildCdnUrl("Technology", "Samsung.webp"),
            originalName = "Samsung",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_8",
            packId = "technology",
            levelNumber = 8,
            answer = "SPOTIFY",
            hintSentence = "Swedish audio streaming service symbolized by three black sound waves on green",
            triviaFact = "Launched in Stockholm in 2008 by Daniel Ek and Martin Lorentzon.",
            logoKey = "spotify",
            imageUrl = buildCdnUrl("Technology", "spotify.webp"),
            originalName = "Spotify",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "technology_9",
            packId = "technology",
            levelNumber = 9,
            answer = "ZOHO",
            hintSentence = "Indian cloud software and SaaS titan with colorful building blocks",
            triviaFact = "Founded in 1996 as AdventNet by Sridhar Vembu and Tony Thomas before rebranding to Zoho.",
            logoKey = "zoho",
            imageUrl = buildCdnUrl("Technology", "Zoho.webp"),
            originalName = "Zoho",
            alternateAnswers = emptyList()
        ),
        // ==================== ENTERTAINMENT (7 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "entertainment_1",
            packId = "entertainment",
            levelNumber = 1,
            answer = "APPLEMUSIC",
            hintSentence = "Global music streaming platform with a red-pink musical note emblem",
            triviaFact = "Launched in 2015 alongside the Beats 1 radio station, now broadcasting in over 160 countries.",
            logoKey = "applemusic",
            imageUrl = buildCdnUrl("Entertainment", "apple-music.webp"),
            originalName = "Apple Music",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "entertainment_2",
            packId = "entertainment",
            levelNumber = 2,
            answer = "JIOHOTSTAR",
            hintSentence = "India's premier streaming service bringing together JioCinema and Disney+ Hotstar",
            triviaFact = "Hotstar became famous globally when it set the world record for concurrent live streaming viewers during IPL.",
            logoKey = "jiohotstar",
            imageUrl = buildCdnUrl("Entertainment", "Jiohotstar.webp"),
            originalName = "JioHotstar",
            alternateAnswers = listOf("HOTSTAR")
        ),
        QuizLevel(
            id = "entertainment_3",
            packId = "entertainment",
            levelNumber = 3,
            answer = "NETFLIX",
            hintSentence = "Pioneering streaming platform famous for the red ribbon N and 'ta-dum' chime",
            triviaFact = "Started in 1997 as a DVD-by-mail service before launching streaming in 2007.",
            logoKey = "netflix",
            imageUrl = buildCdnUrl("Entertainment", "netflix.webp"),
            originalName = "Netflix",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "entertainment_4",
            packId = "entertainment",
            levelNumber = 4,
            answer = "SHAZAM",
            hintSentence = "Music recognition app with a blue S sound-wave emblem that identifies songs in seconds",
            triviaFact = "Founded in 1999 as an SMS service where users dialed 2580 and held their phone to music.",
            logoKey = "shazam",
            imageUrl = buildCdnUrl("Entertainment", "Shazam.webp"),
            originalName = "Shazam",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "entertainment_5",
            packId = "entertainment",
            levelNumber = 5,
            answer = "TWITCH",
            hintSentence = "World's leading live-streaming platform for gamers with a purple speech bubble",
            triviaFact = "Spun off from Justin.tv in 2011 and acquired by Amazon in 2014 for $970 million.",
            logoKey = "twitch",
            imageUrl = buildCdnUrl("Entertainment", "twitch.webp"),
            originalName = "Twitch",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "entertainment_6",
            packId = "entertainment",
            levelNumber = 6,
            answer = "WARNERBROS",
            hintSentence = "Legendary Hollywood film studio recognized by the golden WB shield",
            triviaFact = "Founded in 1923 by four brothers: Harry, Albert, Sam, and Jack Warner.",
            logoKey = "warnesbros",
            imageUrl = buildCdnUrl("Entertainment", "WarnesBros.webp"),
            originalName = "Warner Bros.",
            alternateAnswers = listOf("WARNESBROS")
        ),
        QuizLevel(
            id = "entertainment_7",
            packId = "entertainment",
            levelNumber = 7,
            answer = "YOUTUBE",
            hintSentence = "World's largest video platform recognized by a red rounded rectangle play button",
            triviaFact = "The very first video, 'Me at the zoo', was uploaded by co-founder Jawed Karim on April 23, 2005.",
            logoKey = "youtube",
            imageUrl = buildCdnUrl("Entertainment", "youtube.webp"),
            originalName = "YouTube",
            alternateAnswers = emptyList()
        ),
        // ==================== FASHION & CLOTHING (3 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "fashion_clothing_1",
            packId = "fashion_clothing",
            levelNumber = 1,
            answer = "ADIDAS",
            hintSentence = "German sports giant celebrated worldwide for its iconic three stripes",
            triviaFact = "Founded in 1949 by Adolf 'Adi' Dassler in Herzogenaurach, Germany.",
            logoKey = "adidas",
            imageUrl = buildCdnUrl("Fashion & Clothing", "Adidas.png"),
            originalName = "Adidas",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "fashion_clothing_2",
            packId = "fashion_clothing",
            levelNumber = 2,
            answer = "NIKE",
            hintSentence = "World's leading athletic brand recognized by the iconic curved Swoosh",
            triviaFact = "The Nike Swoosh was designed in 1971 by graphic design student Carolyn Davidson for just $35.",
            logoKey = "nike",
            imageUrl = buildCdnUrl("Fashion & Clothing", "Nike.webp"),
            originalName = "Nike",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "fashion_clothing_3",
            packId = "fashion_clothing",
            levelNumber = 3,
            answer = "PUMA",
            hintSentence = "Athletic footwear and apparel icon with a jumping wild cat silhouette",
            triviaFact = "Founded by Rudolf Dassler in 1948 following a feud with his brother Adi Dassler (founder of Adidas).",
            logoKey = "puma",
            imageUrl = buildCdnUrl("Fashion & Clothing", "puma.webp"),
            originalName = "Puma",
            alternateAnswers = emptyList()
        ),
        // ==================== FINANCE & BANKING (1 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "finance_banking_1",
            packId = "finance_banking",
            levelNumber = 1,
            answer = "MASTERCARD",
            hintSentence = "Global payment network symbolized by interlocking red and yellow circles",
            triviaFact = "Originally formed as Master Charge: The Interbank Card in 1966 before becoming Mastercard in 1979.",
            logoKey = "mastercard",
            imageUrl = buildCdnUrl("Finance & Banking", "Mastercard.webp"),
            originalName = "Mastercard",
            alternateAnswers = emptyList()
        ),
        // ==================== SPORTS (2 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "sports_1",
            packId = "sports",
            levelNumber = 1,
            answer = "NBA",
            hintSentence = "Premier North American men's basketball league with Jerry West's silhouette in red and blue",
            triviaFact = "Alan Siegel designed the iconic silhouette logo in 1969 based on a photo of Lakers star Jerry West.",
            logoKey = "nba",
            imageUrl = buildCdnUrl("Sports", "Nba.webp"),
            originalName = "NBA",
            alternateAnswers = emptyList()
        ),
        QuizLevel(
            id = "sports_2",
            packId = "sports",
            levelNumber = 2,
            answer = "OLYMPIC",
            hintSentence = "World's preeminent international sporting tournament symbolized by five interlocking rings",
            triviaFact = "Pierre de Coubertin designed the five rings in 1913, representing the five inhabited continents.",
            logoKey = "olympic",
            imageUrl = buildCdnUrl("Sports", "Olympic.webp"),
            originalName = "Olympic",
            alternateAnswers = listOf("OLYMPICS")
        ),
        // ==================== RETAIL & SUPERMARKETS (1 REAL LOGOS FROM GITHUB) ====================
        QuizLevel(
            id = "retail_supermarkets_1",
            packId = "retail_supermarkets",
            levelNumber = 1,
            answer = "TARGET",
            hintSentence = "Major American department store retailer with an unmistakable red bullseye",
            triviaFact = "The iconic bullseye logo was created in 1962 when Target opened its first store in Roseville, Minnesota.",
            logoKey = "target",
            imageUrl = buildCdnUrl("Retail & Supermarkets", "Target.webp"),
            originalName = "Target",
            alternateAnswers = emptyList()
        ),
    )

    private val dynamicLevelsList = CopyOnWriteArrayList<QuizLevel>(bundledLevels)

    val allLevels: List<QuizLevel>
        get() = dynamicLevelsList.toList()

    fun updateLevels(newLevels: List<QuizLevel>) {
        val seenIds = mutableSetOf<String>()
        val seenAnswers = mutableSetOf<String>()
        val seenUrls = mutableSetOf<String>()
        val deduplicated = mutableListOf<QuizLevel>()

        for (lvl in newLevels) {
            val ansKey = "${lvl.packId}:${lvl.answer.uppercase()}"
            val urlKey = lvl.imageUrl?.lowercase()
            val urlAlreadySeen = urlKey != null && urlKey in seenUrls
            if (lvl.id !in seenIds && ansKey !in seenAnswers && !urlAlreadySeen) {
                seenIds.add(lvl.id)
                seenAnswers.add(ansKey)
                if (urlKey != null) seenUrls.add(urlKey)
                deduplicated.add(lvl)
            }
        }
        dynamicLevelsList.clear()
        dynamicLevelsList.addAll(deduplicated)
    }

    fun addLevels(newLevels: List<QuizLevel>) {
        val existingIds = dynamicLevelsList.map { it.id }.toMutableSet()
        val existingAnswers = dynamicLevelsList.map { "${it.packId}:${it.answer.uppercase()}" }.toMutableSet()
        val existingUrls = dynamicLevelsList.mapNotNull { it.imageUrl?.lowercase() }.toMutableSet()

        val toAdd = mutableListOf<QuizLevel>()
        for (lvl in newLevels) {
            val ansKey = "${lvl.packId}:${lvl.answer.uppercase()}"
            val urlKey = lvl.imageUrl?.lowercase()
            val urlAlreadySeen = urlKey != null && urlKey in existingUrls
            if (lvl.id !in existingIds && ansKey !in existingAnswers && !urlAlreadySeen) {
                existingIds.add(lvl.id)
                existingAnswers.add(ansKey)
                if (urlKey != null) existingUrls.add(urlKey)
                toAdd.add(lvl)
            }
        }
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
