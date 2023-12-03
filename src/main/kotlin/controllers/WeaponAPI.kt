package controllers

import models.Weapon
import persistence.Serializer
import utils.Utilities

class WeaponAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var weapons = ArrayList<Weapon>()
    private var queryWeapons = ArrayList<Weapon>()
    private var searchOptionAvailable = 2
    private var searchOptionDPS = 1

    fun add(weapon: Weapon): Boolean {
        return weapons.add(weapon)
    }

    private fun validateIndex(index: Int): Boolean {
        return Utilities.validateIndex(index, weapons)
    }

    fun deleteWeapon(index: Int): Weapon? {
        return if (validateIndex(index)) {
            weapons.removeAt(index)
        } else {
            null
        }
    }

    fun numberOfWeapons(): Int = weapons.size

    fun numberOfAvailableWeapons(defeatedNames: ArrayList<String>): Int = weapons.filter { it.checkRequirements(defeatedNames) }.size

    fun findWeapon(index: Int): Weapon? {
        return if (validateIndex(index)) {
            weapons[index]
        } else {
            null
        }
    }

    // Source for converting a list into an arraylist
    // https://discuss.kotlinlang.org/t/how-to-convert-list-to-arraylist/945
    fun searchWithTag(tag: String) {
        queryWeapons = ArrayList(queryWeapons.filter { it.checkTag(tag) })
    }

    private fun searchAllWeapons() {
        queryWeapons = weapons
    }

    private fun searchAvailableWeapons(defeatedNames: ArrayList<String>) {
        queryWeapons = ArrayList(weapons.filter { it.checkRequirements(defeatedNames) })
    }

    private fun searchUnavailableWeapons(defeatedNames: ArrayList<String>) {
        queryWeapons = ArrayList(weapons.filter { !it.checkRequirements(defeatedNames) })
    }

    // Source for sortByDescending
    // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/sorted-by-descending.html
    private fun sortSearchByHighestDPS() {
        queryWeapons.sortByDescending { it.calculateDPS() }
    }

    private fun sortSearchByLowestDPS() {
        queryWeapons.sortBy { it.calculateDPS() }
    }

    fun search(defeatedNames: ArrayList<String>) {
        when (searchOptionAvailable) {
            1 -> searchAllWeapons()
            2 -> searchAvailableWeapons(defeatedNames)
            3 -> searchUnavailableWeapons(defeatedNames)
            else -> queryWeapons = weapons
        }

        when (searchOptionDPS) {
            1 -> sortSearchByHighestDPS()
            2 -> sortSearchByLowestDPS()
            else -> sortSearchByHighestDPS()
        }
    }

    fun searchResults(): String =
        if (weapons.isEmpty()) {
            "No weapons stored"
        } else if (queryWeapons.isEmpty()) {
            "No weapons match current settings"
        } else {
            queryWeapons.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value}" }
        }

    fun setSearchOptionAvailable(option: Int) {
        searchOptionAvailable = option
    }

    fun setSearchOptionDPS(option: Int) {
        searchOptionDPS = option
    }

    // Source for finding something with the highest value
    // https://www.baeldung.com/kotlin/max-value-in-array
    fun bestAvailableWeaponName(defeatedNames: ArrayList<String>): String {
        return if (weapons.isEmpty()) {
            "There are no weapons in the system"
        } else if (weapons.none { it.checkRequirements(defeatedNames) }) {
            "There are no available weapons"
        } else {
            val best = weapons.filter { it.checkRequirements(defeatedNames) }
                .maxWith(compareBy { it.calculateDPS() })
            best.name
        }
    }

    @Throws(Exception::class)
    fun load() {
        weapons = serializer.read() as ArrayList<Weapon>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(weapons)
    }

    fun reset() {
        weapons.clear()
    }
}
