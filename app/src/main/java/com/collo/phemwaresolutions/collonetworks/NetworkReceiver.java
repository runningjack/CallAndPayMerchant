package com.collo.phemwaresolutions.collonetworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by EntralogIT on 2017-03-16.
 */
public class NetworkReceiver extends BroadcastReceiver {
    public ConnectivityManager mConnMgr;

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            //check if wifi is available
            boolean isWIFIAvailable = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();

            //check if mobile data is available
            boolean isGSMAvailable = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            if (isWIFIAvailable) {
                Toast.makeText(context, "WIFI Network Detected!", Toast.LENGTH_LONG).show();
            } else if (isGSMAvailable) {
                Toast.makeText(context, "GSM Network Detected!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "No Network Detected, Ensure to connect to a network!", Toast.LENGTH_LONG).show();
            }
        }


    }
}
