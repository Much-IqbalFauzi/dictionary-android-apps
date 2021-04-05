package com.midtest.dictionary

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import com.google.gson.GsonBuilder
import com.midtest.dictionary.adapter.HistoryItemAdapter
import com.midtest.dictionary.model.Histories
import com.midtest.dictionary.model.History
import okhttp3.*
import java.io.IOException


class HistoryTranslate : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_translate)
        val username = intent.getStringExtra("username").toString()
        GetHistories(username)

        findViewById<ImageView>(R.id.back_ico).setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    fun SetListAdapter(data: List<History>) {
        val adapter = HistoryItemAdapter(this, data as ArrayList<History>)
        val historyList: GridView = findViewById(R.id.history_grid)
        historyList.adapter = adapter
    }

    fun GetHistories(username: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://192.168.43.30:8089/history/username/$username")
                .method("GET", null)
                .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, Histories::class.java)

                this@HistoryTranslate.runOnUiThread {
                    SetListAdapter(data.Histories)
                }
            }
        })
    }
}