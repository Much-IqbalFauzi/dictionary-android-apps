package com.midtest.dictionary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.midtest.dictionary.model.LoggedUser
import com.midtest.dictionary.model.User
import com.midtest.dictionary.model.UserData
import com.midtest.dictionary.model.UserLogin
import okhttp3.*
import java.io.IOException
import kotlin.properties.Delegates


class Register : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.regis_username)
        val nama = findViewById<EditText>(R.id.regis_name)
        val email = findViewById<EditText>(R.id.regis_email)
        val password = findViewById<EditText>(R.id.regis_password)
        val bio = findViewById<EditText>(R.id.regis_bio)

        findViewById<Button>(R.id.regis_register).setOnClickListener(View.OnClickListener {
            if (username.text.isEmpty() or nama.text.isEmpty() or email.text.isEmpty() or password.text.isEmpty() or bio.text.isEmpty()) {
                Toast.makeText(this, "Fill all field", Toast.LENGTH_LONG).show()
            } else {
                val userData = UserData(username.text.toString(),
                        nama.text.toString(), email.text.toString(), password.text.toString(), bio.text.toString())
                NewUser(userData)
            }
        })
        findViewById<Button>(R.id.regis_cancel).setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    fun AutoLog(username: String) {
        if (username.isEmpty()) {
            Toast.makeText(this, "Registe Fail", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            val intent = Intent(this@Register, Home::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()
        }
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
            .post(body)
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
                    AutoLog(data.User.username)
                }
            }
        })
    }
}