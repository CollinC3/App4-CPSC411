package com.example.app3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_info.view.*

class MainAdapter(val listBooks: List<BookList>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return listBooks.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val bookInfo = layoutInflater.inflate(R.layout.book_info,parent, false)
        return CustomViewHolder(bookInfo)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val books = listBooks[position]
        holder?.view?.title?.text = books.title
        holder?.view?.author?.text = books.author
        holder?.view?.year?.text = books.published.toString()
        holder?.itemView.setOnClickListener {
            Toast.makeText(holder.view.context, books.first_sentence, Toast.LENGTH_LONG).show()
        }
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}