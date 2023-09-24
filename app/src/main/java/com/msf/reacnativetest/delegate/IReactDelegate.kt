package com.msf.reacnativetest.delegate

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.DefaultLifecycleObserver
import com.facebook.react.ReactRootView

interface IReactDelegate: DefaultLifecycleObserver {
    fun onBackPress(action: () -> Unit)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    fun loadApp(activity: Activity, action: (ReactRootView) -> Unit)
}