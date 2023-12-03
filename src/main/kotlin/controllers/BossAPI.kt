package controllers

import models.Boss
import persistence.Serializer
import utils.Utilities
import kotlin.collections.ArrayList

/**
 * Provides an API for managing boss entities, including adding, deleting, toggling defeat status,
 * retrieving lists and information about bosses, and handling storage operations.
 *
 * @property serializer The serializer used for reading and writing boss data.
 */
class BossAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var bosses = ArrayList<Boss>()

    /**
     * Adds a boss to the collection.
     *
     * @param boss The boss to be added.
     * @return `true` if the boss was added successfully, `false` otherwise.
     */
    fun add(boss: Boss): Boolean {
        return bosses.add(boss)
    }

    /**
     * Validates whether the given index is within the bounds of the boss list.
     *
     * @param index The index to be validated.
     * @return `true` if the index is valid, `false` otherwise.
     */
    private fun validateIndex(index: Int): Boolean {
        return Utilities.validateIndex(index, bosses)
    }

    /**
     * Deletes a boss at the specified index.
     *
     * @param index The index of the boss to be deleted.
     * @return The deleted boss, or `null` if the index is invalid.
     */
    fun deleteBoss(index: Int): Boss? {
        return if (validateIndex(index)) {
            bosses.removeAt(index)
        } else {
            null
        }
    }

    /**
     * Toggles the defeated status of a given boss.
     *
     * @param boss The boss whose defeated status will be toggled.
     */
// Source: https://stackoverflow.com/questions/224311/cleanest-way-to-toggle-a-boolean-variable-in-java
    fun toggleBoss(boss: Boss) {
        boss.defeated = !boss.defeated
    }

    /**
     * Retrieves a list of names of defeated bosses.
     *
     * @return List of defeated boss names.
     */
    fun defeatedList(): ArrayList<String> {
        val defeatedNames = ArrayList<String>()
        bosses.filter { it.defeated }.forEach { defeatedNames.add(it.name) }
        return defeatedNames
    }

    /**
     * Retrieves a formatted string representing the list of all bosses.
     *
     * @return A formatted string listing all bosses.
     */
    fun listBosses(): String =
        if (bosses.isEmpty()) {
            "No bosses stored"
        } else {
            bosses.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value}" }
        }

    /**
     * Retrieves the number of bosses.
     *
     * @return The total number of bosses.
     */
    fun numberOfBosses(): Int = bosses.size

    /**
     * Retrieves the number of defeated bosses.
     *
     * @return The number of defeated bosses.
     */
    fun numberOfDefeatedBosses(): Int = bosses.count { it.defeated }

    /**
     * Retrieves a boss at the specified index.
     *
     * @param index The index of the boss to retrieve.
     * @return The boss at the specified index, or `null` if the index is invalid.
     */
    fun findBoss(index: Int): Boss? {
        return if (validateIndex(index)) {
            bosses[index]
        } else {
            null
        }
    }

    /**
     * Sets the next boss to be encountered.
     */
    fun generateNextBoss() {
        bosses.forEach { it.next = false }
        val next = bosses.find { !it.defeated }
        if (next is Boss) {
            next.next = true
        }
    }

    /**
     * Retrieves the name of the next boss to be encountered.
     *
     * @return The name of the next boss, or "You did it!" if there are no remaining undefeated bosses.
     */
    fun getNextBossName(): String {
        val next = bosses.find { it.next }
        return if (next is Boss) {
            next.name
        } else {
            "You did it!"
        }
    }

    /**
     * Loads boss data from storage.
     *
     * @throws Exception If an error occurs during the loading process.
     */
    @Throws(Exception::class)
    fun load() {
        bosses = serializer.read() as ArrayList<Boss>
    }

    /**
     * Stores the current boss data.
     *
     * @throws Exception If an error occurs during the storage process.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(bosses)
    }

    /**
     * Resets the collection of bosses, removing all entries.
     */
    fun reset() {
        bosses.clear()
    }
}
