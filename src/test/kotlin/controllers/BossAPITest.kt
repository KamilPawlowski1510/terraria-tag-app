package controllers

import models.Boss
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class BossAPITest {

    private var kingSlime: Boss? = null
    private var eyeOfCthulhu: Boss? = null
    private var eaterOfWorlds: Boss? = null
    private var brainOfCthulhu: Boss? = null
    private var queenBee: Boss? = null
    private var populatedBosses: BossAPI? = BossAPI()
    private var emptyBosses: BossAPI? = BossAPI()

    @BeforeEach
    fun setup(){
        kingSlime = Boss("King Slime", true)
        eyeOfCthulhu = Boss("Eye of Cthulhu", true)
        eaterOfWorlds = Boss("Eater of Worlds", false)
        brainOfCthulhu = Boss("Brain of Cthulhu", false)
        queenBee = Boss("Queen Bee", false)

        //adding 5 Bosses to the bosses API
        populatedBosses!!.add(kingSlime!!)
        populatedBosses!!.add(eyeOfCthulhu!!)
        populatedBosses!!.add(eaterOfWorlds!!)
        populatedBosses!!.add(brainOfCthulhu!!)
        populatedBosses!!.add(queenBee!!)
    }

    @AfterEach
    fun tearDown(){
        kingSlime = null
        eyeOfCthulhu = null
        eaterOfWorlds = null
        brainOfCthulhu = null
        queenBee = null
        populatedBosses = null
        emptyBosses = null
    }

    @Nested
    inner class AddBosses {
        @Test
        fun `adding a Boss to a populated list adds to ArrayList`() {
            val newBoss = Boss("Skeletron", false)
            assertEquals(5, populatedBosses!!.numberOfBosses())
            assertTrue(populatedBosses!!.add(newBoss))
            assertEquals(6, populatedBosses!!.numberOfBosses())
            assertEquals(newBoss, populatedBosses!!.findBoss(populatedBosses!!.numberOfBosses() - 1))
        }

        @Test
        fun `adding a Boss to an empty list adds to ArrayList`() {
            val newBoss = Boss("Skeletron", false)
            assertEquals(0, emptyBosses!!.numberOfBosses())
            assertTrue(emptyBosses!!.add(newBoss))
            assertEquals(1, emptyBosses!!.numberOfBosses())
            assertEquals(newBoss, emptyBosses!!.findBoss(emptyBosses!!.numberOfBosses() - 1))
        }
    }

    @Nested
    inner class ListBosses {

        @Test
        fun `listBosses returns No Bosses Stored message when ArrayList is empty`() {
            assertEquals(0, emptyBosses!!.numberOfBosses())
            assertTrue(emptyBosses!!.listBosses().lowercase().contains("no bosses"))
        }

        @Test
        fun `listBosses returns Bosses when ArrayList has bosses stored`() {
            assertEquals(5, populatedBosses!!.numberOfBosses())
            val bossesString = populatedBosses!!.listBosses().lowercase()
            assertTrue(bossesString.contains("king"))
            assertTrue(bossesString.contains("eye"))
            assertTrue(bossesString.contains("eater"))
            assertTrue(bossesString.contains("brain"))
            assertTrue(bossesString.contains("queen"))
        }
    }

    @Nested
    inner class DeleteBosses {

        @Test
        fun `deleting a Boss that does not exist, returns null`() {
            assertNull(emptyBosses!!.deleteBoss(0))
            assertNull(populatedBosses!!.deleteBoss(-1))
            assertNull(populatedBosses!!.deleteBoss(5))
        }

        @Test
        fun `deleting a boss that exists delete and returns deleted object`() {
            assertEquals(5, populatedBosses!!.numberOfBosses())
            assertEquals(queenBee, populatedBosses!!.deleteBoss(4))
            assertEquals(4, populatedBosses!!.numberOfBosses())
            assertEquals(kingSlime, populatedBosses!!.deleteBoss(0))
            assertEquals(3, populatedBosses!!.numberOfBosses())
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfBossesCalculatedCorrectly() {
            assertEquals(5, populatedBosses!!.numberOfBosses())
            assertEquals(0, emptyBosses!!.numberOfBosses())
        }
    }

    @Nested
    inner class Toggle {
        @Test
        fun `toggling a defeated boss makes them undefeated`() {
            val slime = populatedBosses!!.findBoss(0)
            assertTrue(slime!!.defeated)
            populatedBosses!!.toggleBoss(slime)
            assertFalse(slime.defeated)
        }

        @Test
        fun `toggling an undefeated boss makes them defeated`() {
            val brain = populatedBosses!!.findBoss(3)
            assertFalse(brain!!.defeated)
            populatedBosses!!.toggleBoss(brain)
            assertTrue(brain.defeated)
        }
    }

    @Nested
    inner class DefeatList {
        @Test
        fun `a list with no defeated bosses will yield an empty defeated list`() {
            assertEquals(0, emptyBosses!!.numberOfBosses())
            assertEquals(0, emptyBosses!!.defeatedList().size)
        }

        @Test
        fun `a list with defeated bosses will yield a defeated list with the names of only the defeated bosses`() {
            assertEquals(5, populatedBosses!!.numberOfBosses())
            val defeated = populatedBosses!!.defeatedList()
            assertEquals(2, defeated.size)
            assertTrue(defeated.toString().lowercase().contains("king"))
            assertTrue(defeated.toString().lowercase().contains("eye"))
            assertFalse(defeated.toString().lowercase().contains("eater"))
            assertFalse(defeated.toString().lowercase().contains("brain"))
            assertFalse(defeated.toString().lowercase().contains("queen"))
        }
    }

    @Nested
    inner class FindBoss {
        @Test
        fun `a list with no bosses will return null regardless of index`() {
            assertNull(emptyBosses!!.findBoss(-5))
            assertNull(emptyBosses!!.findBoss(0))
            assertNull(emptyBosses!!.findBoss(20))
        }

        @Test
        fun `a list with bosses will only return a boss with a valid index`() {
            assertEquals(5, populatedBosses!!.numberOfBosses())
            assertNull(populatedBosses!!.findBoss(-1))
            assertNotNull(populatedBosses!!.findBoss(0))
            assertNotNull(populatedBosses!!.findBoss(1))
            assertNotNull(populatedBosses!!.findBoss(2))
            assertNotNull(populatedBosses!!.findBoss(3))
            assertNotNull(populatedBosses!!.findBoss(4))
            assertNull(populatedBosses!!.findBoss(5))
        }
    }
}