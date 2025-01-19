package com.example.finedelivery.utils

import android.content.Context
import android.widget.Toast

object ToastObj {
    fun s(context: Context,t:String){
        Toast.makeText(context, t, Toast.LENGTH_SHORT).show()
    }
    fun l(context: Context,t:String){
        Toast.makeText(context, t, Toast.LENGTH_LONG).show()
    }
}