package de.traendy.featureflag

enum class TestSetting(
    override val key: String,
    override val title: String,
    override val explanation: String,
    override val defaultValue: Boolean = false
) : Feature {
    DEBUG_LOGGING(
        "testsetting.debuglogging",
        "Enable logging",
        "Print all app logging to console",
        defaultValue = true
    )
}
