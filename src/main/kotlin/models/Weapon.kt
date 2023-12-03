package models

data class Weapon(var name: String, var damage: Int, var criticalChance: Int, var useTime: Int, var tags: ArrayList<String>, var requirements: ArrayList<String>) {

    fun calculateDPS(): Int{
        return ((damage/(useTime.toDouble()/60))*(1+(criticalChance.toDouble()/100))).toInt()
    }

    override fun toString(): String {
        return """$name (${calculateDPS()})
                  |Damage: $damage, Use Time: $useTime, Critical Strike Chance: $criticalChance%
                  |""".trimMargin()
    }

    fun checkTag(tag :String) = tags.contains(tag)

    fun checkRequirements(bosses: ArrayList<String>): Boolean = requirements.count { bosses.contains(it) } == requirements.size
}