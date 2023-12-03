package models

/**
 * Represents a weapon with various attributes.
 *
 * @property name The name of the weapon.
 * @property damage The damage dealt by the weapon.
 * @property criticalChance The critical strike chance of the weapon.
 * @property useTime The time it takes to use the weapon.
 * @property tags List of tags associated with the weapon.
 * @property requirements List of requirements for using the weapon.
 */
data class Weapon(var name: String, var damage: Int, var criticalChance: Int, var useTime: Int, var tags: ArrayList<String>, var requirements: ArrayList<String>) {

    /**
     * Calculates the damage per second (DPS) of the weapon.
     *
     * @return The calculated DPS of the weapon.
     */
    fun calculateDPS(): Int {
        return ((damage / (useTime.toDouble() / 60)) * (1 + (criticalChance.toDouble() / 100))).toInt()
    }

    /**
     * Converts the weapon object to a formatted string.
     *
     * @return A string representation of the weapon.
     */
    override fun toString(): String {
        return """$name, ${calculateDPS()} DPS
                  |Damage: $damage, Use Time: $useTime, Critical Strike Chance: $criticalChance%
                  |Tags: $tags
                  |
        """.trimMargin()
    }

    /**
     * Checks if the weapon has a specific tag.
     *
     * @param tag The tag to check.
     * @return `true` if the weapon has the specified tag, `false` otherwise.
     */
    fun checkTag(tag: String) = tags.contains(tag)

    /**
     * Checks if the weapon has its requirements met based on a list of bosses.
     *
     * @param bosses List of bosses to check against the weapon's requirements.
     * @return `true` if the weapon meets all requirements, `false` otherwise.
     */
    fun checkRequirements(bosses: ArrayList<String>): Boolean = requirements.count { bosses.contains(it) } == requirements.size
}
