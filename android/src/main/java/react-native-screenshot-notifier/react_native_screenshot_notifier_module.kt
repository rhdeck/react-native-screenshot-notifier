package com.react_native_screenshot_notifier
import com.facebook.react.bridge.*
import com.facebook.react.common.MapBuilder
import com.abangfadli.shotwatch.ShotWatch
import com.abankfadli.shotwatch.ScreenshotData
class react_native_screenshot_notifier(internal var reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    //@ReactClassName=react_native_screenshot_notifier
    val _name: String = "react_native_screenshot_notifier"
    val _shotwatch:ShotWatch? 
    override fun getName(): String { return _name }
    @ReactMethod
    fun test(s: String, p: Promise)  {
        val m = Arguments.createMap();
        m.putString("message", s + " and such")
        p.resolve(m)
    }
    @ReactMethod
    fun start(p: Promise) {
        this._shotwatch = new ShotWatch(this.reactContext.getContentResolver(), new ShotWatch.Listener() {
            override fun onScreenShotTaken(screenshotData: ScreenshotData) {
                sendEvent("screenshotTaken", null)
            }
        })
        val m = Arguments.createMap();
        m.putString("success", "ok")
        p.resolve(m)
    }

    @ReactMethod
    fun resume(p: Promise) {
        this._shotwatch.register()
        val m = Arguments.createMap();
        m.putString("success", "ok")
        p.resolve(m)
    }

    @ReactMethod
    fun pause(p: Promise) {
        this._shotwatch.unregister()
        val m = Arguments.createMap();
        m.putString("success", "ok")
        p.resolve(m)
    }

    fun sendMessage(message: String, args:WriteableMap?) {
        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEmitter.class).emit(message, args)
    }
}
