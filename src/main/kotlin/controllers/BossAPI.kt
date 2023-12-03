package controllers

import models.Boss
import persistence.Serializer
import utils.Utilities
import kotlin.collections.ArrayList


class BossAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var bosses = ArrayList<Boss>()

    fun add(boss: Boss): Boolean {
        return bosses.add(boss)
    }

    private fun validateIndex(index: Int): Boolean {
        return Utilities.validateIndex(index, bosses)
    }

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
        else bosses.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value.toString()}" }

    fun numberOfBosses(): Int = bosses.size

    fun numberOfDefeatedBosses(): Int = bosses.count { it.defeated }

    fun findBoss(index: Int): Boss? {
        return if (validateIndex(index)) {
            bosses[index]
        } else null
    }

    fun generateNextBoss(){
        bosses.forEach { it.next = false }
        val next = bosses.find{!it.defeated}
        if (next is Boss){
            next.next = true
        }
    }

    fun getNextBossName(): String{
        val next = bosses.find{it.next}
        return if (next is Boss){
            next.name
        } else "You did it!"
    }

    @Throws(Exception::class)
    fun load() {
        bosses = serializer.read() as ArrayList<Boss>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(bosses)
    }

    fun reset(){
        bosses.clear()
    }

}