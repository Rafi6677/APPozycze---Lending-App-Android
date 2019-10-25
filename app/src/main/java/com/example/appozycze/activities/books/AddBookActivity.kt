package com.example.appozycze.activities.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appozycze.R
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.BookEntity
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        cancelSavingBook_Button.setOnClickListener {
            finish()
        }

        saveBook_Button.setOnClickListener {
            if (checkIfInputsFilledCorrect()) {
                saveBookToDB()
            }
        }
    }

    private fun checkIfInputsFilledCorrect() : Boolean
    {
        if (bookTitle_AddBookInput.text.toString() == "" || bookAuthor_AddBookInput.text.toString() == "") {
            Toast.makeText(this, "Pola muszą być wypełnione.", Toast.LENGTH_SHORT)
                .show()

            return false
        } else {
            return true
        }
    }

    private fun saveBookToDB()
    {
        val title = bookTitle_AddBookInput.text.toString()
        val author = bookAuthor_AddBookInput.text.toString()
        val status = "AT_HOME"
        val lendTo = ""

        val bookEntity = BookEntity(title, author, status, lendTo)

        Thread{
            AppDB.getInstance(this)!!.bookDao().saveBook(bookEntity)
        }.start()

        Toast.makeText(this, "Dodano książkę", Toast.LENGTH_SHORT)
            .show()
    }
}
