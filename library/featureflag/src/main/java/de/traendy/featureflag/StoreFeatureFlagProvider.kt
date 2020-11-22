package de.traendy.featureflag

class StoreFeatureFlagProvider : FeatureFlagProvider {

    override val priority = MIN_PRIORITY

    @Suppress("ComplexMethod")
    override fun isFeatureEnabled(feature: Feature): Boolean {
        if (feature is FeatureFlag) {
            // No "else" branch here -> choosing the default
            // option for release must be an explicit choice
            return when (feature) {
                FeatureFlag.BOTTOM_SHEET_ADD_DIALOG -> false
                FeatureFlag.SHARE_CONTENT -> false
                FeatureFlag.MAIL_QR_CODE -> false
                FeatureFlag.TWITTER_QR_CODE -> false
                FeatureFlag.INSTAGRAM_QR_CODE -> false
                FeatureFlag.WLAN_QR_CODE -> false
                FeatureFlag.CONTACT_QR_CODE -> false
            }
        } else {
            // TestSettings should never be shipped to users
            return when (feature as TestSetting) {
                else -> false
            }
        }
    }

    override fun hasFeature(feature: Feature): Boolean = true
}
