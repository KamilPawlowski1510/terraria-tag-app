package controllers

import models.Boss
import kotlin.collections.ArrayList


class BossAPI {

    private var bosses = ArrayList<Boss>()

    fun add(boss: Boss): Boolean {
        return bosses.add(boss)
    }

    private fun validateIndex(index: Int) = index >= 0 && index < bosses.size

    fun deleteBoss(index: Int): Boss?{
        return if(validateIndex(index)){
            bosses.removeAt(index)
        } else null
    }
//Source: https://stackoverflow.com/questions/224311/cleanest-way-to-toggle-a-boolean-variable-in-java
    fun toggleBoss(boss: Boss) {
        boss.defeated = !boss.defeated
    }

    fun defeatedList(): ArrayList<String>{
        val defeatedNames = ArrayList<String>()
        bosses.filter { it.defeated }.forEach { defeatedNames.add(it.name) }
        return defeatedNames
    }

    fun listBosses(): String =
        if (bosses.isEmpty()) "No bosses stored"
        else bosses.joinToString(separator = "\n") { "$it" }

    fun numberOfBosses(): Int = bosses.size

    fun findBoss(index: Int): Boss? {
        return if (validateIndex(index)) {
            bosses[index]
        } else null
    }

}