package de.traendy.nocontact

import android.app.Application
import de.traendy.featureflag.RuntimeBehavior

class NoContactContactApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RuntimeBehavior.initialize(applicationContext, true)
    }
}