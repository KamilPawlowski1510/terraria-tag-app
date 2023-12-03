package utils

object Utilities {
    @JvmStatic
    fun validateIndex(index: Int, list: List<Any>) = index >= 0 && index < list.size
}
