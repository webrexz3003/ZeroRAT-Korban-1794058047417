package com.zero.agent;
import android.accessibilityservice.AccessibilityService; import android.view.accessibility.AccessibilityEvent;
public class AccessibilityHelper extends AccessibilityService {
    public static AccessibilityHelper instance;
    public void onServiceConnected() { instance = this; }
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    public void onInterrupt() {}
}