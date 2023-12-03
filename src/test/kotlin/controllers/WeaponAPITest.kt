package controllers

import models.Weapon
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class WeaponAPITest {

    private var nightsEdge: Weapon? = null
    private var valor: Weapon? = null
    private var stormSpear: Weapon? = null
    private var iceBoomerang: Weapon? = null
    private var terragrim: Weapon? = null
    private var defeatedNames: ArrayList<String>? = ArrayList()
    private var populatedWeapons: WeaponAPI? = WeaponAPI(XMLSerializer(File("weapons1.xml")))
    private var emptyWeapons: WeaponAPI? = WeaponAPI(XMLSerializer(File("weapons2.xml")))

// "Eye of Cthulhu", "Huh"
    @BeforeEach
    fun setup() {
        nightsEdge = Weapon("Night's Edge", 40, 4, 25, arrayListOf("melee", "broadsword"), arrayListOf("Eater of Worlds", "Skeletron"))
        valor = Weapon("Valor", 28, 4, 25, arrayListOf("melee", "yo-yo", "dungeon", "underground"), arrayListOf("Skeletron"))
        stormSpear = Weapon("Storm Spear", 14, 4, 28, arrayListOf("melee", "spear", "desert", "underground"), ArrayList<String>())
        iceBoomerang = Weapon("Ice Boomerang", 21, 6, 20, arrayListOf("melee", "boomerang", "ice", "underground"), ArrayList<String>())
        terragrim = Weapon("Terragrim", 17, 4, 25, arrayListOf("melee", "other", "forest", "surface"), ArrayList<String>())

        defeatedNames = arrayListOf("Skeletron")

        // adding 5 Weapons to the weapons API
        populatedWeapons!!.add(nightsEdge!!)
        populatedWeapons!!.add(valor!!)
        populatedWeapons!!.add(stormSpear!!)
        populatedWeapons!!.add(iceBoomerang!!)
        populatedWeapons!!.add(terragrim!!)
    }

    @AfterEach
    fun tearDown() {
        nightsEdge = null
        valor = null
        stormSpear = null
        iceBoomerang = null
        terragrim = null
        defeatedNames = null
        populatedWeapons = null
        emptyWeapons = null
    }

    @Nested
    inner class AddWeapons {
        @Test
        fun `adding a Weapon to a populated list adds to ArrayList`() {
            val newWeapon = Weapon("Bee Keeper", 30, 4, 20, arrayListOf("melee", "broadsword", "jungle", "underground"), arrayListOf("Queen Bee"))
            assertEquals(5, populatedWeapons!!.numberOfWeapons())
            assertTrue(populatedWeapons!!.add(newWeapon))
            assertEquals(6, populatedWeapons!!.numberOfWeapons())
            assertEquals(newWeapon, populatedWeapons!!.findWeapon(populatedWeapons!!.numberOfWeapons() - 1))
        }

        @Test
        fun `adding a Weapon to an empty list adds to ArrayList`() {
            val newWeapon = Weapon("Bee Keeper", 30, 4, 20, arrayListOf("melee", "broadsword", "jungle", "underground"), arrayListOf("Queen Bee"))
            assertEquals(0, emptyWeapons!!.numberOfWeapons())
            assertTrue(emptyWeapons!!.add(newWeapon))
            assertEquals(1, emptyWeapons!!.numberOfWeapons())
            assertEquals(newWeapon, emptyWeapons!!.findWeapon(emptyWeapons!!.numberOfWeapons() - 1))
        }
    }

    @Nested
    inner class DeleteWeapons {

        @Test
        fun `deleting a Weapon that does not exist, returns null`() {
            assertNull(emptyWeapons!!.deleteWeapon(0))
            assertNull(populatedWeapons!!.deleteWeapon(-1))
            assertNull(populatedWeapons!!.deleteWeapon(5))
        }

        @Test
        fun `deleting a weapon that exists delete and returns deleted object`() {
            assertEquals(5, populatedWeapons!!.numberOfWeapons())
            assertEquals(terragrim, populatedWeapons!!.deleteWeapon(4))
            assertEquals(4, populatedWeapons!!.numberOfWeapons())
            assertEquals(nightsEdge, populatedWeapons!!.deleteWeapon(0))
            assertEquals(3, populatedWeapons!!.numberOfWeapons())
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfWeaponsCalculatedCorrectly() {
            assertEquals(5, populatedWeapons!!.numberOfWeapons())
            assertEquals(0, emptyWeapons!!.numberOfWeapons())
        }
    }

    @Nested
    inner class FindWeapon {
        @Test
        fun `a list with no weapons will return null regardless of index`() {
            assertNull(emptyWeapons!!.findWeapon(-5))
            assertNull(emptyWeapons!!.findWeapon(0))
            assertNull(emptyWeapons!!.findWeapon(20))
        }

        @Test
        fun `a list with weapons will only return a weapon with a valid index`() {
            assertEquals(5, populatedWeapons!!.numberOfWeapons())
            assertNull(populatedWeapons!!.findWeapon(-1))
            assertNotNull(populatedWeapons!!.findWeapon(0))
            assertNotNull(populatedWeapons!!.findWeapon(1))
            assertNotNull(populatedWeapons!!.findWeapon(2))
            assertNotNull(populatedWeapons!!.findWeapon(3))
            assertNotNull(populatedWeapons!!.findWeapon(4))
            assertNull(populatedWeapons!!.findWeapon(5))
        }
    }
}
