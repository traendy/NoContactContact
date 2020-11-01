package de.traendy.featureflag

import android.content.Context
import android.content.SharedPreferences

class RuntimeFeatureFlagProvider : FeatureFlagProvider {

    private val preferences: SharedPreferences

    override val priority = MEDIUM_PRIORITY

    constructor(applicationContext: Context) {
        preferences =
            applicationContext.getSharedPreferences("runtime.featureflags", Context.MODE_PRIVATE)
    }

    override fun isFeatureEnabled(feature: Feature): Boolean =
        preferences.getBoolean(feature.key, feature.defaultValue)

    override fun hasFeature(feature: Feature): Boolean = true

    fun setFeatureEnabled(feature: Feature, enabled: Boolean) =
        preferences.edit().putBoolean(feature.key, enabled).apply()
}
