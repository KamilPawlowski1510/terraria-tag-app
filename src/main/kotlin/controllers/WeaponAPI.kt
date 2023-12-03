package controllers

import models.Weapon
import persistence.Serializer
import utils.Utilities

/**
 * Provides an API for managing weapon entities, including adding, deleting, searching, and handling storage operations.
 *
 * @property serializer The serializer used for reading and writing weapon data.
 */
class WeaponAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var weapons = ArrayList<Weapon>()
    private var queryWeapons = ArrayList<Weapon>()
    private var searchOptionAvailable = 2
    private var searchOptionDPS = 1

    /**
     * Adds a weapon to the collection.
     *
     * @param weapon The weapon to be added.
     * @return `true` if the weapon was added successfully, `false` otherwise.
     */
    fun add(weapon: Weapon): Boolean {
        return weapons.add(weapon)
    }

    /**
     * Validates whether the given index is within the bounds of the weapon list.
     *
     * @param index The index to be validated.
     * @return `true` if the index is valid, `false` otherwise.
     */
    private fun validateIndex(index: Int): Boolean {
        return Utilities.validateIndex(index, weapons)
    }

    /**
     * Deletes a weapon at the specified index.
     *
     * @param index The index of the weapon to be deleted.
     * @return The deleted weapon, or `null` if the index is invalid.
     */
    fun deleteWeapon(index: Int): Weapon? {
        return if (validateIndex(index)) {
            weapons.removeAt(index)
        } else {
            null
        }
    }

    /**
     * Retrieves the number of weapons.
     *
     * @return The total number of weapons.
     */
    fun numberOfWeapons(): Int = weapons.size

    /**
     * Retrieves the number of available weapons based on defeated boss names.
     *
     * @param defeatedNames List of defeated boss names.
     * @return The number of available weapons.
     */
    fun numberOfAvailableWeapons(defeatedNames: ArrayList<String>): Int = weapons.filter { it.checkRequirements(defeatedNames) }.size

    /**
     * Retrieves a weapon at the specified index.
     *
     * @param index The index of the weapon to retrieve.
     * @return The weapon at the specified index, or `null` if the index is invalid.
     */
    fun findWeapon(index: Int): Weapon? {
        return if (validateIndex(index)) {
            weapons[index]
        } else {
            null
        }
    }

    // Source for converting a list into an arraylist
    // https://discuss.kotlinlang.org/t/how-to-convert-list-to-arraylist/945
    /**
     * Filters weapons based on a specified tag.
     *
     * @param tag The tag to filter weapons by.
     */
    fun searchWithTag(tag: String) {
        queryWeapons = ArrayList(queryWeapons.filter { it.checkTag(tag) })
    }

    /**
     * Sets the list of query weapons to be equal to the list of all weapons.
     */
    private fun searchAllWeapons() {
        queryWeapons = weapons
    }

    /**
     * Sets the list of query weapons to include only available weapons based on defeated boss names.
     *
     * @param defeatedNames List of defeated boss names.
     */
    private fun searchAvailableWeapons(defeatedNames: ArrayList<String>) {
        queryWeapons = ArrayList(weapons.filter { it.checkRequirements(defeatedNames) })
    }

    /**
     * Sets the list of query weapons to include only unavailable weapons based on defeated boss names.
     *
     * @param defeatedNames List of defeated boss names.
     */
    private fun searchUnavailableWeapons(defeatedNames: ArrayList<String>) {
        queryWeapons = ArrayList(weapons.filter { !it.checkRequirements(defeatedNames) })
    }

    // Source for sortByDescending
    // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/sorted-by-descending.html
    /**
     * Sorts the list of query weapons by highest DPS in descending order.
     */
    private fun sortSearchByHighestDPS() {
        queryWeapons.sortByDescending { it.calculateDPS() }
    }

    /**
     * Sorts the list of query weapons by lowest DPS in ascending order.
     */
    private fun sortSearchByLowestDPS() {
        queryWeapons.sortBy { it.calculateDPS() }
    }

    /**
     * Searches for weapons based on specified criteria.
     *
     * @param defeatedNames List of defeated boss names.
     */
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

    /**
     * Retrieves the search results as a formatted string.
     *
     * @return A formatted string representing the search results.
     */
    fun searchResults(): String =
        if (weapons.isEmpty()) {
            "No weapons stored"
        } else if (queryWeapons.isEmpty()) {
            "No weapons match current settings"
        } else {
            queryWeapons.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value}" }
        }

    /**
     * Sets the search option for available weapons.
     *
     * @param option The search option for available weapons.
     */
    fun setSearchOptionAvailable(option: Int) {
        searchOptionAvailable = option
    }

    /**
     * Sets the search option for DPS.
     *
     * @param option The search option for DPS.
     */
    fun setSearchOptionDPS(option: Int) {
        searchOptionDPS = option
    }

    // Source for finding something with the highest value
    // https://www.baeldung.com/kotlin/max-value-in-array
    /**
     * Finds the name of the best available weapon based on DPS.
     *
     * @param defeatedNames List of defeated boss names.
     * @return The name of the best available weapon.
     */
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

    /**
     * Loads weapon data from storage.
     *
     * @throws Exception If an error occurs during the loading process.
     */
    @Throws(Exception::class)
    fun load() {
        weapons = serializer.read() as ArrayList<Weapon>
    }

    /**
     * Stores the current weapon data.
     *
     * @throws Exception If an error occurs during the storage process.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(weapons)
    }

    /**
     * Resets the collection of weapons, removing all entries.
     */
    fun reset() {
        weapons.clear()
    }
}
