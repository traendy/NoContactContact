package de.traendy.featureflag


const val MIN_PRIORITY = 1
const val MEDIUM_PRIORITY = 2
const val HIGH_PRIORITY = 3
interface FeatureFlagProvider {
    val priority: Int
    fun isFeatureEnabled(feature: Feature): Boolean
    fun hasFeature(feature: Feature): Boolean
}
