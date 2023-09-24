package com.msf.reacnativetest

import android.app.Application
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(), ReactApplication {

    @Inject
    lateinit var myReactNativeHost: ReactNativeHost

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this,false)
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return myReactNativeHost
    }

}