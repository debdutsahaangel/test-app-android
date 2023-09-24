package com.msf.reacnativetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.msf.reacnativetest.delegate.IReactDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), DefaultHardwareBackBtnHandler {

    @Inject
    lateinit var reactNativeDelegate: IReactDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(reactNativeDelegate)
        reactNativeDelegate.loadApp(this) {
            setContentView(it)
        }
        Log.d("SOME_TEST_LOGS", "Testing logs")
    }

    override fun onBackPressed() {
        reactNativeDelegate.onBackPress {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!reactNativeDelegate.onActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }
}