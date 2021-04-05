package com.midtest.dictionary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.midtest.dictionary.model.LoggedInUser
import com.midtest.dictionary.model.LoggedUser
import com.midtest.dictionary.model.UserData
import com.midtest.dictionary.model.UserLogin
import okhttp3.*
import java.io.IOException


class Login : Activity() {
    private var status: String = "waiting"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.login_username)
        val password = findViewById<EditText>(R.id.login_password)

        findViewById<Button>(R.id.login_login).setOnClickListener(View.OnClickListener {

            if (username.text.isEmpty() or password.text.isEmpty()) {
                Toast.makeText(this, "Fill username and Password", Toast.LENGTH_LONG).show()
            } else {
                LoginCheck(username.text.toString(), password.text.toString())
            }
        })

        findViewById<TextView>(R.id.login_register).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Login, Register::class.java))
        })
    }

    fun StatusCheck(status: String?, username: String?) {
        when(status) {
            "OK"-> {
                val intentData = Intent(this@Login, Home::class.java)
                intentData.putExtra("username", username)
                startActivity(intentData)
                finish()
            }
            else -> Toast.makeText(this, "Login Fail", Toast.LENGTH_LONG).show()
        }
    }

    fun LoginCheck(username: String, password: String) {
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            "{\"username\": \"$username\",\"password\": \"$password\"}"
        )
        val request = Request.Builder()
            .url("http://192.168.43.30:8089/user/login")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, UserLogin::class.java)

                this@Login.runOnUiThread {
                    StatusCheck(data.status, data.user.username)
                }
            }
        })
    }
}
