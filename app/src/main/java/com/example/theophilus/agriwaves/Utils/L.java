package com.example.theophilus.agriwaves.Utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Theophilus on 7/14/2017.
 */

public class L {

    public static void m(Context context, String message){
        Log.d("ZEO", message);
    }

    public static void l(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void s(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void snack(View view, String msg){

    }
}
