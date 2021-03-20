package com.midtest.dictionary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Login : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.login_login).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Login, Home::class.java))
        })

        findViewById<TextView>(R.id.login_register).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Login, Register::class.java))
        })
    }
}
