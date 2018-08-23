package com.example.theophilus.agriwaves.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Theophilus on 10/28/2017.
 */

public class NetworkState {

    private static NetworkState instance;

    private NetworkState(){

    }

    public static NetworkState getInstance(){
        if(NetworkState.instance.equals(null)){
            NetworkState.instance   = new NetworkState();
        }
        return NetworkState.instance;
    }

    public boolean checkNetwork(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo     = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
