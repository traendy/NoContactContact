package de.traendy.nocontact

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import de.traendy.featureflag.RuntimeBehavior
import de.traendy.featureflag.TestSetting
import timber.log.Timber
import timber.log.Timber.DebugTree

class NoContactContactApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RuntimeBehavior.initialize(applicationContext, BuildConfig.DEBUG)
        if(RuntimeBehavior.isFeatureEnabled(TestSetting.DEBUG_LOGGING)){
            Timber.plant(DebugTree())
        }else{
            Timber.plant(DeadTree())
        }
    }

    class DeadTree : Timber.Tree() {
        @SuppressLint("LogNotTimber")
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if(priority == Log.ERROR){
                Log.e(tag, message, t)
            }
        }

    }
}