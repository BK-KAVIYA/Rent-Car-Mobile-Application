package com.example.sourthenlankacarrental;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void showToast(Context context,String massage){
        Toast.makeText(context,massage,Toast.LENGTH_SHORT).show();
    }
}
