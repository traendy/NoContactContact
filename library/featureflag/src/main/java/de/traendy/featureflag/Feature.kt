package de.traendy.featureflag

interface Feature {
    val key: String
    val title: String
    val explanation: String
    val defaultValue: Boolean
}
