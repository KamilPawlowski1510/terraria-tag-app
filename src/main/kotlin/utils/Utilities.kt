package utils

/**
 * This object provides utility methods for common operations.
 */
object Utilities {
    /**
     * Validates whether the given index is within the bounds of the specified list.
     *
     * @param index The index to be validated.
     * @param list The list for which the index is validated.
     * @return `true` if the index is valid, `false` otherwise.
     */
    @JvmStatic
    fun validateIndex(index: Int, list: List<Any>) = index >= 0 && index < list.size
}
