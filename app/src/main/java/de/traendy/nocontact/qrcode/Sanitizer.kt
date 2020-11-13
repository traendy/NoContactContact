package de.traendy.nocontact.qrcode

fun replaceSpaces(text: String): String {
    return text.replace(" ", "%20", true)
}