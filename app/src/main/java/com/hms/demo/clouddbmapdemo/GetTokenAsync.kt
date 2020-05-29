package com.hms.demo.clouddbmapdemo

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import java.lang.Exception


class GetTokenAsync(private val context: Context): AsyncTask<Void, Void, Bundle>() {
    private val TAG="GetToken"
    override fun onPreExecute() {
        super.onPreExecute()
        if(context !is OnTokenTaskListener){
            throw Exception("${context.javaClass.name} must implement OnTokenTaskListener")
        }
    }

    override fun doInBackground(vararg params: Void?): Bundle {
        val b=Bundle()
        b.putInt(Constants.CODE,Constants.ERROR)
        try {
            // read from agconnect-services.json
            val appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id")
            val token = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
            Log.i(TAG, "get token:$token")
            if (!TextUtils.isEmpty(token)) {
                b.putInt(Constants.CODE, Constants.OK)
                b.putString(Constants.DATA, token)
            }
            else b.putString(Constants.DATA,"Token is empty")
        } catch (e: ApiException) {
            b.putString(Constants.DATA,e.toString())
        }

        return b
    }

    override fun onPostExecute(result: Bundle) {
        super.onPostExecute(result)
        val listener=context as OnTokenTaskListener
        listener.onToken(result)
    }

    interface OnTokenTaskListener{
        fun onToken(b:Bundle)
    }

}