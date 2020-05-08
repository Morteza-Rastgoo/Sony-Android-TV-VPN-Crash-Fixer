package ir.mortezarastgoo.androidtvtools.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class AccessibilityKeyDetector : AccessibilityService() {
    private val TAG = "AccessKeyDetector"
    public override fun onKeyEvent(event: KeyEvent): Boolean {
        Log.d(TAG, "Key pressed via accessibility is: " + event.keyCode)
        //This allows the key pressed to function normally after it has been used by your app.
        return super.onKeyEvent(event)
    }

    override fun onServiceConnected() {
        Log.d(TAG, "Service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent $event")

    }
    override fun onInterrupt() {
        Log.d(TAG, "onAccessibilityEvent")

    }
}