package com.example.app3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.io.Serializable

class MainActivity : AppCompatActivity(){

    var listBooks: Array<BookList> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        booksView.layoutManager = LinearLayoutManager(this)
        apiClient()
        val act: View = findViewById(R.id.newAct)
        act.setOnClickListener { view ->
            val intent = Intent(view.context, PostRequest::class.java).apply {
                putExtra("recyclerView", listBooks)
            }
            view.context.startActivity(intent)
        }
    }



    fun apiClient() {
        val url: String = "http://10.0.2.2:7000/api/v1/resources/books/all"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()


        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(call)
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    println(body)

                    val gson = GsonBuilder().create()

                    listBooks = gson.fromJson(body, Array<BookList>::class.java)
                    runOnUiThread {
                        booksView.adapter = MainAdapter(listBooks)
                    }
                }
            }

        })
    }
}

data class BookList(val id: Int, val published: Int, val author: String, val title: String, val first_sentence: String) : Serializable
