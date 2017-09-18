package com.example.ccdez.news314;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 功能：网络检测
 */

public class NetworkDetect {

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            Log.d("Network", "isNetworkConnected:detect ");
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

}
