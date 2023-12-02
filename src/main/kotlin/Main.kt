import controllers.BossAPI
import models.Boss
import utils.ScannerInput
import kotlin.system.exitProcess

private val bossAPI = BossAPI()
private var defeatedNames = ArrayList<String>()

fun main(args: Array<String>) {
    defaultBosses()
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
            2  -> println("Currently not implemented")
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

fun exitApp(){
    exitProcess(0)
}