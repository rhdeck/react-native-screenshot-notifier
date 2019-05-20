package com.react_native_screenshot_notifier

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.ArrayList

//#StartReactPackage
class com_react_native_screenshot_notifier : ReactPackage {
  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> { 
    val modules = ArrayList<NativeModule>()
    modules.add(react_native_screenshot_notifier(reactContext))
    return modules
  }
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*,*>> {
     return emptyList<ViewManager<*,*>>()
  }
}
//#EndReactPackage
