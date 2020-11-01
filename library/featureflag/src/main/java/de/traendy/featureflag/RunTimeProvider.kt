package de.traendy.featureflag

import android.content.Context
import androidx.annotation.VisibleForTesting
import java.util.concurrent.CopyOnWriteArrayList

object RuntimeBehavior {

    @VisibleForTesting
    internal val providers = CopyOnWriteArrayList<FeatureFlagProvider>()

    @JvmStatic
    fun initialize(context: Context, isDebugBuild: Boolean) {
        if (isDebugBuild) {
            val runtimeFeatureFlagProvider = RuntimeFeatureFlagProvider(context)
            addProvider(RuntimeFeatureFlagProvider(context))

//            if (runtimeFeatureFlagProvider.isFeatureEnabled(TestSetting.DEBUG_LOGGING)) {
//                addProvider(FirebaseFeatureFlagProvider(true))
//            }
        } else {
            addProvider(StoreFeatureFlagProvider())
        }
    }

    @JvmStatic
    fun isFeatureEnabled(feature: Feature): Boolean {
        return providers.filter { it.hasFeature(feature) }
            .sortedBy(FeatureFlagProvider::priority)
            .firstOrNull()
            ?.isFeatureEnabled(feature)
            ?: feature.defaultValue
    }

    @JvmStatic
    fun addProvider(provider: FeatureFlagProvider) = providers.add(provider)
}
