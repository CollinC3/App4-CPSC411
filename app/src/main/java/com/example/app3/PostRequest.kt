package com.example.app3

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.io.Serializable

class PostRequest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_request)
        val listBooks: Array<BookList> = intent.getSerializableExtra("recyclerView") as Array<BookList>
        val submitButton: Button = findViewById(R.id.submit)
        submitButton.setOnClickListener{ submit(listBooks) }
    }

    private fun submit(listBooks: Array<BookList>) {
        //put the post into to api in here
        val title: EditText = findViewById(R.id.title)
        val author: EditText = findViewById(R.id.author)
        val year: EditText = findViewById(R.id.year)
        val sentence: EditText = findViewById(R.id.sentence)
        val newBook = Book(year.text.toString().toInt(), author.text.toString(), title.text.toString(), sentence.text.toString())
        val gson = GsonBuilder().create()

        var bookJson = gson.toJson(newBook)
        println(bookJson)

        val url: String = "http://10.0.2.2:7000/api/v1/resources/books/"
        val okHttpClient = OkHttpClient()
        val requestBody = bookJson.toRequestBody()
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
                println("failure")
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                println("Success")
                println(response)
            }
        })
        MainAdapter(listBooks).notifyItemInserted(MainAdapter(listBooks).itemCount)
        finish()
    }
}

data class Book(val published: Int, val author: String, val title: String, val first_sentence: String)