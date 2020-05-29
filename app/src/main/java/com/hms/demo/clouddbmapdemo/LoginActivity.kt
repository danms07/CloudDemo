package com.hms.demo.clouddbmapdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.HwIdAuthProvider
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val REQUEST_SIGN_IN_LOGIN_CODE = 1003
    val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val hwButn=findViewById<Button>(R.id.hw_btn)
        hwButn.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGN_IN_LOGIN_CODE) {
            //Huawei login success
            val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
            if (authHuaweiIdTask.isSuccessful) {
                val huaweiAccount = authHuaweiIdTask.result
                /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run.  */
                /** */
                val accessToken = huaweiAccount.accessToken
                val credential = HwIdAuthProvider.credentialWithToken(accessToken)
                AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener {
                    // onSuccess
                    val intent=Intent(this,SplashScreen::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    // onFail
                    Log.e(TAG, it.toString())
                }
            } else {
                Log.i(
                    TAG,
                    "signIn get code failed: " + (authHuaweiIdTask.exception as ApiException).statusCode
                )
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.hw_btn -> {
                val mAuthParam =
                    HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                        .setIdToken()
                        .setAccessToken()
                        .createParams()

                val mAuthManager = HuaweiIdAuthManager.getService(this, mAuthParam)
                startActivityForResult(
                    mAuthManager.signInIntent,
                    REQUEST_SIGN_IN_LOGIN_CODE
                )
            }
        }
    }
}
