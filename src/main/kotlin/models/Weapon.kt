package models

data class Weapon(var name: String, var damage: Int, var criticalChance: Int, var useTime: Int, var tags: ArrayList<String>, var requirements: ArrayList<String>) {

    fun calculateDPS(): Int{
        return (damage*(useTime/60))*(1+(criticalChance/100))
    }

    override fun toString(): String {
        return """$name (${calculateDPS()})
                  |Damage: €$damage, Use Time: $criticalChance%, Critical Strike Chance: $useTime%
                  |""".trimMargin()
    }

}