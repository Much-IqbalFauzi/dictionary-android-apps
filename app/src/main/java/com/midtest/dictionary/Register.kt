package com.midtest.dictionary

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.midtest.dictionary.model.User
import com.midtest.dictionary.model.UserData
import com.midtest.dictionary.model.UserLogin
import okhttp3.*
import java.io.IOException
import kotlin.properties.Delegates


class Register : Activity() {
    private var status: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.regis_username).toString()
        val nama = findViewById<EditText>(R.id.regis_name).toString()
        val email = findViewById<EditText>(R.id.regis_email).toString()
        val password = findViewById<EditText>(R.id.regis_password).toString()
        val bio = findViewById<EditText>(R.id.regis_bio).toString()

        findViewById<Button>(R.id.regis_register).setOnClickListener(View.OnClickListener {
            if (username.isEmpty() and nama.isEmpty() and email.isEmpty() and password.isEmpty() and bio.isEmpty()) {
                Toast.makeText(this, "Fill all field", Toast.LENGTH_LONG).show()
            } else {
                val userData = UserData(username, nama, email, password, bio)
                NewUser(userData)
                if (status) Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                else Toast.makeText(this, "Register Fail", Toast.LENGTH_LONG).show()
            }
        })

        findViewById<Button>(R.id.regis_cancel).setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    fun NewUser(userData: UserData) {
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            "{\n        " +
                    "\"username\": \"${userData.username}\",\n        " +
                    "\"nama\": \"${userData.nama}\",\n        " +
                    "\"email\": \"${userData.email}\",\n        " +
                    "\"password\": \"${userData.password}\",\n        " +
                    "\"bio\": \"${userData.bio}\"\n}"
        )
        val request = Request.Builder()
            .url("http://192.168.43.30:8089/user/save")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                print("faill request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, User::class.java)

                this@Register.runOnUiThread {
                    if (data != null) status = true
                    println("==============================================")
                    println(data.User)
                }
            }
        })
    }
}