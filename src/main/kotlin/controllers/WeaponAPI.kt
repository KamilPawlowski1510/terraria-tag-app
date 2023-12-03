package controllers

import models.Weapon
import utils.Utilities

class WeaponAPI(var defeatedNames: ArrayList<String>) {
    private var weapons = ArrayList<Weapon>()
    private var queryWeapons = ArrayList<Weapon>()
    private var searchOptionAvailable = 1
    private var searchOptionDPS = 1

    fun add(weapon: Weapon): Boolean {
        return weapons.add(weapon)
    }

    private fun validateIndex(index: Int): Boolean {
        return Utilities.validateIndex(index, weapons)
    }

    fun deleteWeapon(index: Int): Weapon?{
        return if(validateIndex(index)){
            weapons.removeAt(index)
        } else null
    }

    fun listWeapons(): String =
        if (weapons.isEmpty()) "No weapons stored"
        else weapons.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value}" }

    fun listWeaponsWithTag(tag: String): String{
        return if (weapons.isEmpty()) "No weapons stored"
        else if(weapons.none { it.checkTag(tag) }){
            "No weapons with the tag $tag"
        } else weapons.filter { it.checkTag(tag) }.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value}" }
    }

    fun numberOfWeapons(): Int = weapons.size

    fun findWeapon(index: Int): Weapon? {
        return if (validateIndex(index)) {
            weapons[index]
        } else null
    }

    fun searchWithTag(tag: String){
        queryWeapons = ArrayList(queryWeapons.filter { it.checkTag(tag) })
    }

    fun getAvailableWeapons(){
        queryWeapons = ArrayList(weapons.filter {  it.checkRequirements(defeatedNames)})
    }

    fun getUnavailableWeapons(){
        queryWeapons = ArrayList(weapons.filter {  !it.checkRequirements(defeatedNames)})
    }

    fun sortWeaponsByHighestDPS(){
        queryWeapons.sortBy { it.calculateDPS() }
    }

    fun sortWeaponsByLowestDPS(){
        queryWeapons.sortByDescending { it.calculateDPS() }
    }

    fun searchOptions(){

        when(searchOptionAvailable){
            1  -> queryWeapons = weapons
            2  -> getAvailableWeapons()
            3  -> getUnavailableWeapons()
            else  -> queryWeapons = weapons
        }

        when(searchOptionDPS){
            1  -> sortWeaponsByHighestDPS()
            2  -> sortWeaponsByLowestDPS()
            else  -> sortWeaponsByHighestDPS()
        }

    }

    fun searchResults(): String =
    if (weapons.isEmpty()) "No weapons stored"
    else if (queryWeapons.isEmpty()) "No weapons match current settings"
    else queryWeapons.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value.toString()}" }

    fun numberOfQuery(){
        queryWeapons.size
    }
}