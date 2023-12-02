package models

data class Boss(var name:String, var defeated:Boolean = false) {

    //References for the colour:
    //https://discuss.kotlinlang.org/t/printing-in-colors/22492
    //https://gist.github.com/mgumiero9/665ab5f0e5e7e46cb049c1544a00e29f
    override fun toString(): String {
        val color = if (defeated) "\u001B[32m" else "\u001b[31m"
        val reset = "\u001B[0m"
        return "$color$name$reset"
    }
}