package plugins

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.uppercase() else it.toString()
}