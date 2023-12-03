import controllers.BossAPI
import controllers.WeaponAPI
import models.Boss
import models.Weapon
import utils.ScannerInput
import kotlin.system.exitProcess

private val bossAPI = BossAPI()
private var defeatedNames = ArrayList<String>()
private val weaponAPI = WeaponAPI()

fun main(args: Array<String>) {
    defaultBosses()
    defaultWeapons()
    println((14.toDouble()*(28/60))*(1+(4/100)).toDouble())
    println(14*(28/60))
    println(28/60)
    runMenu()
}

fun mainMenu() : Int {
    println(""" 
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
         > |          TERRARIA TAG APP         |
         > -------------------------------------
         > |   1) Bosses                       |
         > |   2) Weapons                      |
         > -------------------------------------
         > |   0) Exit                         |
         > -------------------------------------
         > ==>> """.trimMargin(">"))
    return ScannerInput.readNextInt("Please select an option",0, 2)
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> defeatedNames = runBossMenu()
            2  -> runWeaponMenu()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun bossMenu() : Int {
    println(bossAPI.listBosses())
    println(""" 
         > -------------------------------------
         > |          Boss Options             |
         > -------------------------------------
         > |   1) Change Boss                  |
         > -------------------------------------
         > |   0) Exit                         |
         > -------------------------------------
         > ==>> """.trimMargin(">"))
    return ScannerInput.readNextInt("Please select an option", 0, 1)
}

fun runBossMenu(): ArrayList<String> {
    do {
        val option = bossMenu()
        when (option) {
            1  -> changeBoss()
            0  -> return bossAPI.defeatedList()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun changeBoss(){
    if(bossAPI.numberOfBosses() == 0){
        println("There are no bosses in the system to change")
    }
    else{
        val id = ScannerInput.readNextInt("Select the boss which you want to change or '0' to cancel", 0, bossAPI.numberOfBosses())
        if(id == 0){
            return
        }
        else{
            bossAPI.toggleBoss(bossAPI.findBoss(id - 1)!!)
        }
    }
}


fun weaponMenu() : Int {
    println(weaponAPI.searchResults())
    println(""" 
         > -------------------------------------
         > |          Weapon Options           |
         > -------------------------------------
         > |   1) Change Search Options        |
         > |   2) Search by Tag                |
         > -------------------------------------
         > |   0) Exit                         |
         > -------------------------------------
         > ==>> """.trimMargin(">"))
    return ScannerInput.readNextInt("Please select an option", 0, 2)
}

fun runWeaponMenu(){
    updateResults()
    do {
        val option = weaponMenu()
        when (option) {
            1  -> updateSearchOptions()
            2  -> searchByTag()
            0  -> return
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun updateSearchOptions(){
    println(""" 
         > Which weapons would you like to see?
         > 1: All Weapons
         > 2: Available Weapons
         > 3: Unavailable Weapons
         > ==>> """.trimMargin(">"))
    weaponAPI.setSearchOptionAvailable(ScannerInput.readNextInt("Please select an option", 1, 3))
    println(""" 
         > How should the weapons be ordered?
         > 1: By Highest DPS
         > 2: By Lowest DPS
         > ==>> """.trimMargin(">"))
    weaponAPI.setSearchOptionDPS(ScannerInput.readNextInt("Please select an option", 1, 2))
    updateResults()
}

fun searchByTag(){
    updateResults()
    weaponAPI.searchWithTag(ScannerInput.readNextLine("Please enter a tag: "))
}

fun updateResults(){
    weaponAPI.search(defeatedNames)
}

fun defaultBosses(){
    bossAPI.add(Boss("King Slime"))
    bossAPI.add(Boss("Eye of Cthulhu"))
    bossAPI.add(Boss("Eater of Worlds"))
    bossAPI.add(Boss("Brain of Cthulhu"))
    bossAPI.add(Boss("Queen Bee"))
    bossAPI.add(Boss("Skeletron"))
    bossAPI.add(Boss("Deerclops"))
    bossAPI.add(Boss("Wall of Flesh"))
}

fun defaultWeapons(){
    weaponAPI.add(Weapon("Night's Edge", 40, 4, 25, arrayListOf("melee", "broadsword"), arrayListOf("Eater of Worlds", "Skeletron")))
    weaponAPI.add(Weapon("Valor", 28, 4, 25, arrayListOf("melee", "yo-yo", "dungeon", "underground"), arrayListOf("Skeletron")))
    weaponAPI.add(Weapon("Storm Spear", 14, 4, 28, arrayListOf("melee", "spear", "desert", "underground"), ArrayList<String>()))
    weaponAPI.add(Weapon("Ice Boomerang", 21, 6, 20, arrayListOf("melee", "boomerang", "ice", "underground"), ArrayList<String>()))
    weaponAPI.add(Weapon("Terragrim", 17, 4, 25, arrayListOf("melee", "other", "forest", "surface"), ArrayList<String>()))
}

fun exitApp(){
    exitProcess(0)
}