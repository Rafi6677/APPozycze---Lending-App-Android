package com.example.appozycze.activities.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appozycze.R
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.BookEntity
import com.example.appozycze.viewmodels.BookItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_books.*
import androidx.recyclerview.widget.DividerItemDecoration



class BooksActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<ViewHolder>()
    private var booksList: List<BookEntity> ?= null

    companion object {
        const val BOOK_KEY = "bookKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        addBook_Button.setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        booksList_RecyclerView.setHasFixedSize(true)
        booksList_RecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(booksList_RecyclerView.context, layoutManager.orientation)
        booksList_RecyclerView.addItemDecoration(dividerItemDecoration)

        setupData()
    }

    private fun setupData() {
        adapter.clear()

        Thread{
            booksList = AppDB.getInstance(this)!!.bookDao().getBooks()
            booksList!!.sortedBy {
                it.bookAuthor
            }

            booksList!!.forEach {
                adapter.add(BookItem(it))
            }

            adapter.setOnItemClickListener { item, _ ->
                val bookItem = item as BookItem
                val id = bookItem.book.bookID
                val intent = Intent(this, EditBookActivity::class.java)
                intent.putExtra(BOOK_KEY, id)
                startActivity(intent)
            }

            runOnUiThread {
                booksList_RecyclerView.adapter = adapter
            }

        }.start()
    }
}
