package com.midtest.dictionary

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class Translate : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        LangList()
    }

    fun SpinnerValue(data: Lang): Unit {
        val langFrom: Spinner = findViewById(R.id.trans_spinner_from)
        val langTo: Spinner = findViewById(R.id.trans_spinner_target)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, data.Languages)
        langFrom.adapter = adapter
        langTo.adapter = adapter
    }

    fun LangList() {
        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
                .url("http://192.168.43.30:8089/translate/listlanguage")
                .method("GET", null)
                .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Fail Request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, Lang::class.java)

                this@Translate.runOnUiThread {
                    SpinnerValue(data)
                }
            }
        })
    }
}

class Lang(val Languages : List<String>)

