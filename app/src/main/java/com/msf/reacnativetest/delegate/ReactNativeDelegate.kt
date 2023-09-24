package com.msf.reacnativetest.delegate

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import com.facebook.react.ReactDelegate
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactRootView
import com.msf.reacnativetest.BuildConfig

class ReactNativeDelegate(
    private val appKey: String,
    private val reactNativeHost: ReactNativeHost
): IReactDelegate {

    private lateinit var delegate: ReactDelegate

    private var activity: Activity? = null

    private val reactNativeRequestCodes = listOf(
        1
    )

    override fun loadApp(activity: Activity, action: (ReactRootView) -> Unit) {
        this.activity = activity
        delegate = ReactDelegate(activity, reactNativeHost, appKey, null, BuildConfig.IS_NEW_ARCHITECTURE_ENABLED)
        delegate.loadApp()
        action(delegate.reactRootView)
    }


    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        delegate.onHostPause()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        delegate.onHostResume()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        this.activity = null
        delegate.onHostDestroy()
    }

    override fun onBackPress(action: () -> Unit) {
        if (!delegate.onBackPressed()) {
            action.invoke()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return if (reactNativeRequestCodes.contains(requestCode)) {
            delegate.onActivityResult(requestCode, resultCode, data, true)
            true
        } else {
            false
        }
    }
}