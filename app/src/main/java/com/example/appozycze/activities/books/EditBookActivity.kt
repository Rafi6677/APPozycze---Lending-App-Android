package com.example.appozycze.activities.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appozycze.R
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.BookEntity
import kotlinx.android.synthetic.main.activity_edit_book.*

class EditBookActivity : AppCompatActivity() {

    private var id = 0
    private var book: BookEntity ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        id = intent.getIntExtra(BooksActivity.BOOK_KEY, 0)

        Thread{
            book = AppDB.getInstance(this)!!.bookDao().getSingleBook(id)

            runOnUiThread {
                bookTitle_EditBookInput.setText(book!!.bookTitle)
                bookAuthor_EditBookInput.setText(book!!.bookAuthor)

                if (book!!.bookStatus == "AT_HOME") {
                    bookStatus_EditTextView.text = "Wypożycz dla:"
                    bookDateStatus_EditTextView.visibility = View.INVISIBLE
                    lendBook_EditButton.text = "Wypożycz"

                    lendBook_EditButton.setOnClickListener {

                    }
                } else {
                    bookStatus_EditTextView.text = "Wypożyczona dla:"
                    bookDateStatus_EditTextView.visibility = View.VISIBLE
                    bookDateStatus_EditTextView.text = "Dnia: 26-10-2019"
                    lendBook_EditButton.text = "Zgłoś zwrot"

                    lendBook_EditButton.setOnClickListener {

                    }
                }
            }
        }.start()


    }
}
