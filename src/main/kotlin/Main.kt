import controllers.BossAPI
import controllers.WeaponAPI
import models.Boss
import models.Weapon
import persistence.XMLSerializer
import utils.ScannerInput
import java.io.File
import kotlin.system.exitProcess

/**
 * The Boss API instance for managing boss entities.
 */
private val bossAPI = BossAPI(XMLSerializer(File("bosses.xml")))

/**
 * List of defeated boss names.
 */
private var defeatedNames = ArrayList<String>()

/**
 * The Weapon API instance for managing weapon entities.
 */
private val weaponAPI = WeaponAPI(XMLSerializer(File("weapons.xml")))

/**
 * Main entry point of the program.
 *
 * @param args Command-line arguments.
 */
fun main(args: Array<String>) {
    checkBossesExist()
    checkWeaponsExist()
    runMenu()
}

/**
 * Displays the main menu and reads user input.
 *
 * @return The selected option from the main menu.
 */
fun mainMenu(): Int {
    println(
        """ 
         > ⠀         ⠀⢀⣴⣶⣤⣾⣿⣿⣦⣶⡄⠀⠀⠀
         > ⠀         ⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⠀⠀
         >          ⠀⠰⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠀
         >          ⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀
         >          ⠀⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀
         >          ⠀⠀⠀⠈⠛⠻⠟⠉⣭⡍⠀⠀⠀⠀⠀
         >          ⠀⠀⠀⣀⠀⠀⢰⣾⣿⡇⠀⠀⠀⠀⠀
         >          ⢀⣾⣿⣿⡇⠀⢸⣿⣿⡇⠀⠀⠀⠀⠀
         >          ⠈⠙⣿⡿⠻⣷⣼⣿⣿⡇⠀⣤⣤⣤⡀
         > ⠀⠀         ⠀⠀⠀⠀⢹⣿⣿⡇⣀⣿⣿⣿⣧
         > ⠀         ⠀⠀⠀⠀⠀⢸⣿⣿⣿⠟⠁⠹⠟⠁
         > ⠀         ⠀⠀⠀⠀⢀⣼⣿⣿⣇⠀⠀⠀⠀⠀
         > ⠀⠀⠀         ⢀⣤⣿⣿⣿⣿⣿⣷⣤⠀⠀⠀
         > -------------------------------------
         > |          TERRARIA TAG APP
         > -------------------------------------
         > |   1) Bosses (${bossAPI.numberOfDefeatedBosses()}/${bossAPI.numberOfBosses()} Defeated)
         > |      Next Boss: ${bossAPI.getNextBossName()}
         > -------------------------------------
         > |   2) Weapons (${weaponAPI.numberOfAvailableWeapons(defeatedNames)}/${weaponAPI.numberOfWeapons()} Available)
         > |      Best Weapon Available: 
         > |          ${weaponAPI.bestAvailableWeaponName(defeatedNames)}
         > -------------------------------------
         > |   3) Reset Data to Default
         > -------------------------------------
         > |   0) Exit                         
         > -------------------------------------
         > ==>> """.trimMargin(">")
    )
    return ScannerInput.readNextInt("Please select an option", 0, 3)
}

/**
 * Runs the main menu loop.
 */
fun runMenu() {
    bossAPI.generateNextBoss()
    do {
        val option = mainMenu()
        when (option) {
            1 -> defeatedNames = runBossMenu()
            2 -> runWeaponMenu()
            3 -> resetData()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

/**
 * Displays the boss menu and reads user input.
 *
 * @return The list of defeated boss names.
 */
fun bossMenu(): Int {
    bossAPI.generateNextBoss()
    println(bossAPI.listBosses())
    println(
        """ 
         > -------------------------------------
         > |          Boss Options             |
         > -------------------------------------
         > |   1) Change Boss                  |
         > -------------------------------------
         > |   0) Exit                         |
         > -------------------------------------
         > ==>> """.trimMargin(">")
    )
    return ScannerInput.readNextInt("Please select an option", 0, 1)
}

/**
 * Runs the boss menu loop.
 *
 * @return The list of defeated boss names.
 */
fun runBossMenu(): ArrayList<String> {
    do {
        val option = bossMenu()
        when (option) {
            1 -> changeBoss()
            0 -> return bossAPI.defeatedList()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

/**
 * Changes the defeated status of a boss.
 */
fun changeBoss() {
    if (bossAPI.numberOfBosses() == 0) {
        println("There are no bosses in the system to change")
    } else {
        val id = ScannerInput.readNextInt("Select the boss which you want to change or '0' to cancel", 0, bossAPI.numberOfBosses())
        if (id == 0) {
            return
        } else {
            bossAPI.toggleBoss(bossAPI.findBoss(id - 1)!!)
            saveBosses()
        }
    }
}

/**
 * Displays the weapon menu and reads user input.
 *
 * @return The selected option from the weapon menu.
 */
fun weaponMenu(): Int {
    println(weaponAPI.searchResults())
    println(
        """ 
         > -------------------------------------
         > |          Weapon Options           |
         > -------------------------------------
         > |   1) Change Search Options        |
         > |   2) Search by Tag                |
         > -------------------------------------
         > |   0) Exit                         |
         > -------------------------------------
         > ==>> """.trimMargin(">")
    )
    return ScannerInput.readNextInt("Please select an option", 0, 2)
}

/**
 * Runs the weapon menu loop.
 */
fun runWeaponMenu() {
    updateResults()
    do {
        val option = weaponMenu()
        when (option) {
            1 -> updateSearchOptions()
            2 -> searchByTag()
            0 -> return
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

/**
 * Updates the search options for weapons.
 */
fun updateSearchOptions() {
    println(
        """ 
         > Which weapons would you like to see?
         > 1: All Weapons
         > 2: Available Weapons
         > 3: Unavailable Weapons
         > ==>> """.trimMargin(">")
    )
    weaponAPI.setSearchOptionAvailable(ScannerInput.readNextInt("Please select an option", 1, 3))
    println(
        """ 
         > How should the weapons be ordered?
         > 1: By Highest DPS
         > 2: By Lowest DPS
         > ==>> """.trimMargin(">")
    )
    weaponAPI.setSearchOptionDPS(ScannerInput.readNextInt("Please select an option", 1, 2))
    updateResults()
}

/**
 * Searches for weapons with a specific tag and updates the search results.
 */
fun searchByTag() {
    updateResults()
    weaponAPI.searchWithTag(ScannerInput.readNextLine("Please enter a tag: "))
}

/**
 * Updates the weapon search results based on the current search criteria.
 */
fun updateResults() {
    weaponAPI.search(defeatedNames)
}

/**
 * Adds default bosses to the Boss API.
 */
fun addDefaultBosses() {
    bossAPI.add(Boss("King Slime", next = true))
    bossAPI.add(Boss("Eye of Cthulhu"))
    bossAPI.add(Boss("Eater of Worlds"))
    bossAPI.add(Boss("Brain of Cthulhu"))
    bossAPI.add(Boss("Queen Bee"))
    bossAPI.add(Boss("Skeletron"))
    bossAPI.add(Boss("Deerclops"))
    bossAPI.add(Boss("Wall of Flesh"))
    saveBosses()
}

/**
 * Adds default weapons to the Weapon API.
 */
fun addDefaultWeapons() {
    weaponAPI.add(Weapon("Night's Edge", 40, 4, 25, arrayListOf("melee", "broadsword"), arrayListOf("Eater of Worlds", "Skeletron")))
    weaponAPI.add(Weapon("Valor", 28, 4, 25, arrayListOf("melee", "yo-yo", "dungeon", "underground"), arrayListOf("Skeletron")))
    weaponAPI.add(Weapon("Storm Spear", 14, 4, 28, arrayListOf("melee", "spear", "desert", "underground"), ArrayList<String>()))
    weaponAPI.add(Weapon("Ice Boomerang", 21, 6, 20, arrayListOf("melee", "boomerang", "ice", "underground"), ArrayList<String>()))
    weaponAPI.add(Weapon("Terragrim", 17, 4, 25, arrayListOf("melee", "other", "forest", "surface"), ArrayList<String>()))
    weaponAPI.add(Weapon("Bee Keeper", 30, 4, 20, arrayListOf("melee", "broadsword", "jungle", "underground"), arrayListOf("Queen Bee")))
    weaponAPI.add(Weapon("Copper Shortsword", 5, 4, 13, arrayListOf("melee", "shortsword", "surface"), ArrayList<String>()))
    weaponAPI.add(Weapon("Bloody Machete", 20, 4, 15, arrayListOf("melee", "boomerang", "halloween"), ArrayList<String>()))
    weaponAPI.add(Weapon("The Meatball", 34, 4, 45, arrayListOf("melee", "flail", "crimson"), arrayListOf("Brain of Cthulhu")))
    weaponAPI.add(Weapon("Lucy the Axe", 27, 14, 15, arrayListOf("melee", "axe", "ice", "surface"), arrayListOf("Brain of Cthulhu")))
    weaponAPI.add(Weapon("Breaker Blade", 70, 4, 30, arrayListOf("melee", "broadsword", "underworld"), arrayListOf("Wall of Flesh")))
    weaponAPI.add(Weapon("Starfury", 25, 4, 20, arrayListOf("melee", "broadsword", "sky"), ArrayList<String>()))
    saveWeapons()
}

/**
 * Saves the current state of bosses to a file.
 */
fun saveBosses() {
    try {
        bossAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Loads the saved state of bosses from a file.
 */
fun loadBosses() {
    try {
        bossAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Saves the current state of weapons to a file.
 */
fun saveWeapons() {
    try {
        weaponAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Loads the saved state of weapons from a file.
 */
fun loadWeapons() {
    try {
        weaponAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

// Source for file.exits
// https://www.tutorialkart.com/kotlin/kotlin-check-if-file-exists/#gsc.tab=0
/**
 * Checks if the file for storing boss data exists, and loads or initializes data accordingly.
 */
fun checkBossesExist() {
    if (File("bosses.xml").exists()) {
        loadBosses()
    } else {
        addDefaultBosses()
    }
}

/**
 * Checks if the file for storing weapon data exists, and loads or initializes data accordingly.
 */
fun checkWeaponsExist() {
    if (File("weapons.xml").exists()) {
        loadWeapons()
    } else {
        addDefaultWeapons()
    }
}

/**
 * Resets the data by clearing both boss and weapon information and adding default values.
 */
fun resetData() {
    bossAPI.reset()
    weaponAPI.reset()
    addDefaultBosses()
    addDefaultWeapons()
}

/**
 * Exits the application.
 */
fun exitApp() {
    exitProcess(0)
}
