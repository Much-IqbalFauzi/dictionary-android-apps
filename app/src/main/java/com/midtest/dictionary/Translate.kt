package com.midtest.dictionary

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.midtest.dictionary.model.HistoryResult
import okhttp3.*
import java.io.IOException


class Translate : Activity() {
    lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        val textFromField: EditText = findViewById(R.id.trans_text)
        username = intent.getStringExtra("username").toString()
        LangList()

        findViewById<FloatingActionButton>(R.id.trans_swap_spinner).setOnClickListener(View.OnClickListener {
            val spinerFrom: Spinner = findViewById(R.id.trans_spinner_from)
            val spinnerTarget: Spinner = findViewById(R.id.trans_spinner_target)

            val index = spinnerTarget.selectedItemPosition
            spinnerTarget.setSelection(spinerFrom.selectedItemPosition)
            spinerFrom.setSelection(index)
        })

        findViewById<Button>(R.id.trans_translate).setOnClickListener(View.OnClickListener {
            val inputSplit = textFromField.text.toString().replace(" ", "%20")
            TranslateText(GetSpinnerFrom(), GetSpinnerTarget(), inputSplit)
        })

        findViewById<ImageView>(R.id.back_ico).setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    fun SaveHistory(from: String, target: String, textFrom: String, textTarget: String) {
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
                mediaType,
                "{\"languageIdFrom\": \"$from\",\"languageIdTo\": \"$target\",\"messageFrom\": \"$textFrom\",\"messageTo\": \"$textTarget\",\"user\": \"$username\"\n}")
        val request = Request.Builder()
                .url("http://192.168.43.30:8089/history/save")
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
                val data = gson.fromJson(body, HistoryResult::class.java)

                this@Translate.runOnUiThread {
                    Toast.makeText(this@Translate, "Save Success", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun SpinnerValue(data: Lang): Unit {
        val langFrom: Spinner = findViewById(R.id.trans_spinner_from)
        val langTo: Spinner = findViewById(R.id.trans_spinner_target)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, data.Languages)
        langFrom.adapter = adapter
        langTo.adapter = adapter
    }

    fun GetSpinnerFrom(): String {
        val spinner: Spinner = findViewById(R.id.trans_spinner_from)
        return spinner.getItemAtPosition(spinner.selectedItemPosition).toString()
    }

    fun GetSpinnerTarget(): String {
        val spinner: Spinner = findViewById(R.id.trans_spinner_target)
        return spinner.getItemAtPosition(spinner.selectedItemPosition).toString()
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

    fun SetTextResult(result: TranslateData) {
        val textResultField: TextView = findViewById(R.id.trans_result)
        textResultField.text = result.messageTo
    }

    fun TranslateText(langFrom: String, langTarget: String, text: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://192.168.43.30:8089/translate/$langFrom/$langTarget/$text")
                .method("GET", null)
                .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, TranslateResult::class.java)

                this@Translate.runOnUiThread {
                    SetTextResult(data.Result)
                    SaveHistory(data.Result.languageIdFrom, data.Result.languageIdTo, data.Result.messageFrom, data.Result.messageTo)
                }
            }
        })
    }
}

class Lang(val Languages : List<String>)

class TranslateResult(val Result: TranslateData)

class TranslateData(val languageIdFrom: String, val languageIdTo: String, val messageFrom: String, val messageTo: String)

