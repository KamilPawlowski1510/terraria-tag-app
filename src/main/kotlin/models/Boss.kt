package models

/**
 * Represents a boss entity with specific attributes.
 *
 * @property name The name of the boss.
 * @property defeated Indicates whether the boss has been defeated or not. Default is `false`.
 * @property next Indicates whether the boss is the next one to be encountered. Default is `false`.
 */
data class Boss(var name: String, var defeated: Boolean = false, var next: Boolean = false) {

    // References for the colour:
    // https://discuss.kotlinlang.org/t/printing-in-colors/22492
    // https://gist.github.com/mgumiero9/665ab5f0e5e7e46cb049c1544a00e29f

    /**
     * Generates a string representation of the boss with color coding based on its status.
     *
     * @return A formatted string with color indicating the boss's status.
     */
    override fun toString(): String {
        val color = if (next) {
            "\u001B[34m"
        } else if (defeated) {
            "\u001B[32m"
        } else {
            "\u001b[31m"
        }
        val reset = "\u001B[0m"
        return "$color$name$reset"
    }
}
