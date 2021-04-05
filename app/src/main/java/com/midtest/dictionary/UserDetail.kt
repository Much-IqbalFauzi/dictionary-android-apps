package com.midtest.dictionary

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.midtest.dictionary.model.User
import com.midtest.dictionary.model.UserData
import okhttp3.*
import java.io.IOException


class UserDetail : Activity() {
    lateinit var username: TextView
    lateinit var password: TextView
    lateinit var email: TextView
    lateinit var name: TextView
    lateinit var bio: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        username = findViewById(R.id.detail_username)
        password = findViewById(R.id.detail_password)
        email = findViewById(R.id.detail_email)
        name = findViewById(R.id.detail_name)
        bio = findViewById(R.id.detail_bio)

        val usernameIntent = intent.getStringExtra("username").toString()
        GetUser(usernameIntent)

        findViewById<ImageView>(R.id.back_ico).setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }

    fun SetUserData(user: UserData) {

        username.text = user.username
        password.setText(user.password)
        email.setText(user.email)
        name.setText(user.nama)
        bio.setText(user.bio)
    }

    fun GetUser(username: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://192.168.43.30:8089/user/username/$username")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Faill")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, User::class.java)

                this@UserDetail.runOnUiThread {
                   SetUserData(data.User)
                }
            }
        })
    }
}