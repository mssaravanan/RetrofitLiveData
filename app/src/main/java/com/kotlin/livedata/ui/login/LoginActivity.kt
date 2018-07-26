package com.kotlin.livedata.ui.login


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.kotlin.livedata.R
import com.kotlin.livedata.constants.DevicePreferences
import com.kotlin.livedata.model.LoginResponse
import com.kotlin.livedata.ui.searchwine.SearchActivity
import com.kotlin.livedata.util.Status
import com.kotlin.livedata.utility.Utility
import com.kotlin.livedata.viewmodel.APIRequestData
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {


    val str:String?=null


    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DevicePreferences.isContainKey(this@LoginActivity, "userId")) {
            var intent = Intent(this@LoginActivity, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_main)

        buttonLogin.setOnClickListener(View.OnClickListener {
            if (isValid()) {
                var loginJson = JSONObject()
                loginJson.put("Username", userNameEdt.getText().toString())
                loginJson.put("Password", passwordEdt.getText().toString())
                loginJson.put("UserAccountTypeId", 1)
                loginJson.put("SocialId", "")
                loginJson.put("DeviceID", "")
                loginJson.put("DeviceType", 1)
                loginJson.put("DeviceToken","")
                loginJson.put("OSName", "Android")
                loginJson.put("OSVersion", Build.VERSION.RELEASE)
                var requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), loginJson.toString())
               // var apiRequestData: APIRequestData("ddd",requestBody)
                var apiRequestData= APIRequestData(requestBody = requestBody)
                viewModel.setLogin(apiRequestData)

            }

        })

        viewModel.getResponse<LoginResponse>()?.observe(this, Observer {

            try {
                when (it!!.status) {
                    Status.LOADING -> {
                        Utility.showToast(this, "Loading")
                    }

                    Status.ERROR -> {
                        Utility.showToast(this, it.message ?: "")
                    }

                    Status.SUCCESS -> {
                        var loginResponse: LoginResponse = it.data!!
                        if (loginResponse.Status) {
                            DevicePreferences.addKey(this@LoginActivity, "userId", loginResponse.Data?.UserId ?: 0)
                            DevicePreferences.addKey(this@LoginActivity, "userToken", loginResponse?.AccessToken?:"")
                            Utility.showToast(this, loginResponse.Data!!.EmailId ?: "")
                            var intent = Intent(this@LoginActivity, SearchActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Utility.showToast(this, loginResponse.Message ?: "")
                        }

                    }

                    Status.NO_NETWORK -> {
                        //In case of now network it automatically call again - In this case , show view with retry option
                        viewModel.retry()
                    }
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        })

    }

    //Check Validation
    fun isValid(): Boolean {
        if (userNameEdt.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter user name", Toast.LENGTH_LONG).show()
            return false
        }

        if (passwordEdt.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter user name", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}
