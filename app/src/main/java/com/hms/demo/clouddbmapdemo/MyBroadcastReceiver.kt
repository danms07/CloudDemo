package com.hms.demo.clouddbmapdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


class MyBroadcastReceiver(val listener: OnBroadcastReceivedListener): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!=null){
            val message=intent.getStringExtra(Constants.DATA)
            if(message!=null)
                listener.onBroadastReceived(message)
        }

    }

    fun register(context:Context){
        val filter = IntentFilter()
        filter.addAction(ACTION_TOKEN)
        context.registerReceiver(this,filter)
    }

    public interface OnBroadcastReceivedListener{
        fun onBroadastReceived(message:String)
    }


    companion object{
        @JvmField val ACTION_TOKEN="ACTION_TOKEN"
    }
}