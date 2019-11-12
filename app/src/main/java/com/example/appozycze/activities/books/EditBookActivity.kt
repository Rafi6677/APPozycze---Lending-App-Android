package com.example.appozycze.activities.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appozycze.R
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.BookEntity
import kotlinx.android.synthetic.main.activity_edit_book.*
import java.text.SimpleDateFormat
import java.util.*

class EditBookActivity : AppCompatActivity() {

    private var id = 0
    private var book: BookEntity ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        supportActionBar!!.title = "Edycja książki"

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
                    lendName_BookEditTextInput.isClickable = true

                    lendBook_EditButton.setOnClickListener {

                    }
                } else {
                    bookStatus_EditTextView.text = "Wypożyczona dla:"
                    bookDateStatus_EditTextView.visibility = View.VISIBLE
                    bookDateStatus_EditTextView.text = "Dnia: ${book!!.bookLendDate}"
                    lendName_BookEditTextInput.setText(book!!.bookLendTo)
                    lendName_BookEditTextInput.isClickable = false
                    lendBook_EditButton.text = "Zgłoś zwrot"

                    lendBook_EditButton.setOnClickListener {

                    }
                }

                lendBook_EditButton.setOnClickListener {
                    if (book!!.bookStatus == "AT_HOME") {
                        if (lendName_BookEditTextInput.text.toString() == "") {
                            Toast.makeText(this, "Musi być podane imię i nazwisko", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val date = Date()
                            val formatter = SimpleDateFormat("dd-MM-yyyy")
                            val dateText: String = formatter.format(date)

                            Thread{
                                AppDB.getInstance(this)!!.bookDao()
                                    .changeBookStatusToBorrowed(id, "BORROWED", lendName_BookEditTextInput.text.toString(), dateText)
                            }.start()

                            Toast.makeText(this, "Książka wypożyczona", Toast.LENGTH_SHORT)
                                .show()

                            finish()
                            val intent = Intent(this, BooksActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    } else {
                        Thread{
                            AppDB.getInstance(this)!!.bookDao()
                                .changeBookStatusToReturned(id, "AT_HOME", "")
                        }.start()

                        Toast.makeText(this, "Książka zwrócona", Toast.LENGTH_SHORT)
                            .show()

                        finish()
                        val intent = Intent(this, BooksActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }

                saveBook_EditButton.setOnClickListener {
                    val title = bookTitle_EditBookInput.text.toString()
                    val author = bookAuthor_EditBookInput.text.toString()
                    if (title == book!!.bookTitle && author == book!!.bookAuthor) {
                        Toast.makeText(this, "Nie dokonano żadnych zmian.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Thread{
                            AppDB.getInstance(this)!!.bookDao()
                                .updateBookData(id, title, author)
                        }.start()

                        finish()
                        val intent = Intent(this, BooksActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }

                deleteBook_EditButton.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("UWAGA")
                        .setMessage("Czy na pewno chcesz usunąć tę książkę?")
                        .setPositiveButton("OK") { _, _ ->
                            Thread{
                                AppDB.getInstance(this)!!.bookDao().deleteBook(book!!)
                            }.start()

                            Toast.makeText(this, "Książka została usunięta.", Toast.LENGTH_SHORT)
                                .show()

                            finish()
                            val intent = Intent(this, BooksActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        .setNegativeButton("Anuluj") { _, _ -> }
                        .show()
                }
            }
        }.start()
    }
}
