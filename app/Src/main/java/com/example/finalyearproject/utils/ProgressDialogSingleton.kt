package com.example.finedelivery.utils

import android.app.ProgressDialog
import android.content.Context

object ProgressDialogSingleton {
    private var progressDialog: ProgressDialog? = null

    fun getInstance(context: Context,t:String): ProgressDialog {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
            progressDialog?.setTitle("Please wait")
            progressDialog?.setMessage(t)
            progressDialog?.setCancelable(false)
            progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }
        return progressDialog!!
    }
}
