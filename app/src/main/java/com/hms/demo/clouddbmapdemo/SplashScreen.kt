package com.hms.demo.clouddbmapdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.agconnect.function.AGCFunctionException
import com.huawei.agconnect.function.AGConnectFunction
import com.huawei.agconnect.function.FunctionResult
import com.huawei.hmf.tasks.OnCompleteListener
import com.huawei.hmf.tasks.Task
import com.huawei.hms.aaid.HmsInstanceId

class SplashScreen : AppCompatActivity(), GetTokenAsync.OnTokenTaskListener,
    OnCompleteListener<FunctionResult> {

    val TAG="Splash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if(AGConnectAuth.getInstance().currentUser!=null){
            GetTokenAsync(this).execute()
        }
        else{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onToken(b: Bundle) {
        if(b.getInt(Constants.CODE)==Constants.ERROR){
            Log.e("GetToken","Failure")
            finish()
        }
        else{
            val token=b.getString(Constants.DATA)
            cloudFunctionRegister(token!!)
        }
    }

    private fun cloudFunctionRegister(token: String) {
        Log.e(TAG,token)
        val user=AGConnectAuth.getInstance().currentUser
        val map:HashMap<String,String> =HashMap()
        map["userId"]=""+user.uid
        map["token"]=token
        val function: AGConnectFunction = AGConnectFunction.getInstance()
        function.wrap("register-${'$'}latest")
            .call(map).addOnCompleteListener(this)
    }

    override fun onComplete(task: Task<FunctionResult>?) {
        if (task != null) {
            if (task.isSuccessful) {
                val value=task.result.value
                Log.e(TAG,value)
                val intent=Intent(this,NavDrawer::class.java)
                startActivity(intent)
                finish()
            } else {
                val e = task.exception
                if (e is AGCFunctionException) {

                    val errCode = e.code
                    val message = e.message
                    Log.e(TAG, e.toString())
                }
                // ...
            }
        }
    }
}
