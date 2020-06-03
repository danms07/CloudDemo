package com.hms.demo.clouddbmapdemo

import android.content.Intent
import android.os.Handler
import android.util.Log
import com.huawei.hms.push.HmsMessageService

class MyPushService: HmsMessageService()  {
    override fun onNewToken(token: String) {
        Log.e("OnNewToken",token)
        val intent= Intent()
        intent.action= MyBroadcastReceiver.ACTION_TOKEN
        intent.putExtra(Constants.DATA,token)
        val handler= Handler()
        handler.postDelayed({sendBroadcast(intent)},1000)

        /*val builder= AlertDialog.Builder(this)
        builder.setTitle("onNewToken")
        builder.setMessage(token)
        builder.setPositiveButton("OK"
        ) { dialog, which -> dialog.dismiss()}
        builder.create().show()*/
    }
}