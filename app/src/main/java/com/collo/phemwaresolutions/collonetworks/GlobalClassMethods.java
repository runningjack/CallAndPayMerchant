package com.collo.phemwaresolutions.collonetworks;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by EntralogIT on 2017-03-16.
 */

public class GlobalClassMethods extends Activity {

    boolean checkConnectivity() {
        //check network status at onLoad
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected;
    }
}
