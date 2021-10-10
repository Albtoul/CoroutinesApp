package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var txview:TextView
    lateinit var getadvice:Button
    val Url = "https://api.adviceslip.com/advice"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txview=findViewById(R.id.textView)
        getadvice=findViewById(R.id.button)

    getadvice.setOnClickListener {
        Api()

    }



    }
    private fun Api()
    {

        CoroutineScope(Dispatchers.IO).launch {

            val data = async {

                randomAdvice()

            }.await()

            if (data.isNotEmpty())
            {

                update(data)
            }

        }

    }

    private fun randomAdvice():String{

        var res=""
        try {

            res = URL(Url).readText(Charsets.UTF_8)

        }catch (e:Exception)
        {
            println("Error $e")

        }
        return res

    }

    private suspend fun update(data:String)
    {
        withContext(Dispatchers.Main)
        {

            val jsonObject = JSONObject(data)
            val slip = jsonObject.getJSONObject("slip")
            val id = slip.getInt("id")
            val advice = slip.getString("advice")

            txview.text = advice

        }

    }


}
