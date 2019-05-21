package com.react_native_screenshot_notifier
import com.facebook.react.bridge.*
import com.facebook.react.common.MapBuilder
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.abangfadli.shotwatch.ShotWatch
import com.abangfadli.shotwatch.ScreenshotData
import android.view.WindowManager
class react_native_screenshot_notifier(internal var reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    //@ReactClassName=react_native_screenshot_notifier
    val _name: String = "react_native_screenshot_notifier"
    var _shotwatch:ShotWatch? = null
    override fun getName(): String { return _name }
    @ReactMethod
    fun test(s: String, p: Promise)  {
        val m = Arguments.createMap()
        m.putString("message", s + " and such")
        p.resolve(m)
    }
    @ReactMethod 
    fun disableScreenshots(p: Promise) {
        this.getCurrentActivity()?.runOnUiThread({
            this.getCurrentActivity()?.getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        })
        val m = Arguments.createMap()
        m.putString("success", "ok")
        p.resolve(m)
    }
    @ReactMethod 
    fun enableScreenshots(p: Promise) {
        this.getCurrentActivity()?.runOnUiThread({
            this.getCurrentActivity()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        })
        val m = Arguments.createMap()
        m.putString("success", "ok")
        p.resolve(m)
    }
    @ReactMethod
    fun start(p: Promise) {
        this._shotwatch =  ShotWatch(this.reactContext.getContentResolver(),  object: ShotWatch.Listener {
            override fun onScreenShotTaken(screenshotData: ScreenshotData?) {
                val m = Arguments.createMap()
                m.putString("id", screenshotData?.getId().toString())
                m.putString("fileName", screenshotData?.getFileName())
                m.putString("path", screenshotData?.getPath())
                sendEvent("screenshotTaken", m)
            }
        })
        val m = Arguments.createMap()
        m.putString("success", "ok")
        p.resolve(m)
    }

    @ReactMethod
    fun resume(p: Promise) {
        this._shotwatch?.let {
            it.register()
            val m = Arguments.createMap()
            m.putString("success", "ok")
            p.resolve(m)
        } ?: run {
            p.reject("Shotwatch not initialized", "")
        }
    }

    @ReactMethod
    fun pause(p: Promise) {
        this._shotwatch?.let {
            it.unregister()
            val m = Arguments.createMap()
            m.putString("success", "ok")
            p.resolve(m)
        } ?: run {
            p.reject("Shotwatch not initialized", "")
        }
    }

    fun sendEvent(message: String, args:WritableMap?) {
        this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java).emit(message, args)
    }
}
