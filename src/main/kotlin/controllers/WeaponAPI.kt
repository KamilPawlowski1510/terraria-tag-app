package controllers

import models.Weapon
import utils.Utilities

class WeaponAPI() {
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

    fun deleteWeapon(index: Int): Weapon?{
        return if(validateIndex(index)){
            weapons.removeAt(index)
        } else null
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

    fun searchAllWeapons(){
        queryWeapons = weapons
    }

    fun searchAvailableWeapons(defeatedNames: ArrayList<String>){
        queryWeapons = ArrayList(weapons.filter {  it.checkRequirements(defeatedNames)})
    }

    fun searchUnavailableWeapons(defeatedNames: ArrayList<String>){
        queryWeapons = ArrayList(weapons.filter {  !it.checkRequirements(defeatedNames)})
    }

    fun sortSearchByHighestDPS(){
        queryWeapons.sortBy { it.calculateDPS() }
    }

    fun sortSearchByLowestDPS(){
        queryWeapons.sortByDescending { it.calculateDPS() }
    }

    fun search(defeatedNames: ArrayList<String>){

        when(searchOptionAvailable){
            1  -> searchAllWeapons()
            2  -> searchAvailableWeapons(defeatedNames)
            3  -> searchUnavailableWeapons(defeatedNames)
            else  -> queryWeapons = weapons
        }

        when(searchOptionDPS){
            1  -> sortSearchByHighestDPS()
            2  -> sortSearchByLowestDPS()
            else  -> sortSearchByHighestDPS()
        }

    }

    fun searchResults(): String =
    if (weapons.isEmpty()) "No weapons stored"
    else if (queryWeapons.isEmpty()) "No weapons match current settings"
    else queryWeapons.withIndex().joinToString(separator = "\n") { "${it.index + 1}: ${it.value.toString()}" }

    fun numberOfQuery(){
        queryWeapons.size
    }

    fun setSearchOptionAvailable(option: Int){
        searchOptionAvailable = option
    }

    fun setSearchOptionDPS(option: Int){
        searchOptionDPS = option
    }

}