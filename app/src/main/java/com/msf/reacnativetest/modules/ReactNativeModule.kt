package com.msf.reacnativetest.modules

import android.content.Context
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JSIModulePackage
import com.facebook.react.bridge.JSIModuleProvider
import com.facebook.react.bridge.JSIModuleSpec
import com.facebook.react.bridge.JSIModuleType
import com.facebook.react.bridge.UIManager
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.fabric.ComponentFactory
import com.facebook.react.fabric.CoreComponentsRegistry
import com.facebook.react.fabric.EmptyReactNativeConfig
import com.facebook.react.fabric.FabricJSIModuleProvider
import com.facebook.react.uimanager.ViewManagerRegistry
import com.msf.reacnativetest.MyApplication
import com.msf.reacnativetest.delegate.IReactDelegate
import com.msf.reacnativetest.delegate.ReactNativeDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ReactNativeModule {

    @Singleton
    @Provides
    fun provideReactNativeHost(@ApplicationContext appContext: Context): ReactNativeHost = object : DefaultReactNativeHost(appContext as MyApplication) {
        override fun getUseDeveloperSupport() = BuildConfig.DEBUG
        override fun getPackages(): List<ReactPackage> {
            return PackageList(this).packages
        }

        override fun getJSMainModuleName(): String {
            return "index"
        }

        override val isHermesEnabled: Boolean
            get() = com.msf.reacnativetest.BuildConfig.IS_HERMES_ENABLED

        override val isNewArchEnabled: Boolean
            get() = com.msf.reacnativetest.BuildConfig.IS_NEW_ARCHITECTURE_ENABLED

        override fun getJSIModulePackage(): JSIModulePackage {
            return JSIModulePackage { reactContext, _ ->
                val spec = object : JSIModuleSpec<UIManager> {
                    override fun getJSIModuleType(): JSIModuleType {
                        return JSIModuleType.UIManager
                    }
                    override fun getJSIModuleProvider(): JSIModuleProvider<UIManager> {
                        val componentFactory = ComponentFactory()
                        CoreComponentsRegistry.register(componentFactory)
                        val reactInstanceManager = reactInstanceManager

                        val viewManagerRegistry = ViewManagerRegistry(
                            reactInstanceManager.getOrCreateViewManagers(
                                reactContext
                            )
                        )
                        return FabricJSIModuleProvider(
                            reactContext,
                            componentFactory,
                            EmptyReactNativeConfig(),
                            viewManagerRegistry
                        )
                    }
                }
                listOf(
                    spec
                )
            }
        }

    }

    @Singleton
    @Provides
    fun provideReactNativeDelegate(
        @ReactNativeComponentName componentName: String,
        reactNativeHost: ReactNativeHost
    ): IReactDelegate = ReactNativeDelegate(appKey = componentName, reactNativeHost = reactNativeHost)

    @Singleton
    @Provides
    @ReactNativeComponentName
    fun provideReactComponentName(): String = "MyReactNativeComponent"
}