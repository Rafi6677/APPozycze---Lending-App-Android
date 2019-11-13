package com.example.appozycze.activities.books

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.example.appozycze.activities.MainActivity
import com.example.appozycze.viewmodels.BookFilter


class BooksActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<ViewHolder>()
    private var booksList: List<BookEntity> ?= null
    private var bookFilter = BookFilter.Author

    companion object {
        const val BOOK_KEY = "bookKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        supportActionBar!!.title = "Książki"

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
            val sortedList: List<BookEntity>
            booksList = AppDB.getInstance(this)!!.bookDao().getBooks()

            when (bookFilter) {
                BookFilter.Author -> {
                    println("autor")
                    sortedList = booksList!!.sortedBy {
                        it.bookAuthor
                    }
                }
                BookFilter.Title -> {
                    println("tytuł")
                    sortedList =booksList!!.sortedBy {
                        it.bookTitle
                    }
                }
                BookFilter.Status -> {
                    println("status")
                    sortedList = booksList!!.sortedBy {
                        it.bookStatus
                    }
                }
            }

            sortedList.forEach {
                adapter.add(BookItem(it))
            }

            adapter.setOnItemClickListener { item, _ ->
                val bookItem = item as BookItem
                val id = bookItem.book.bookID
                val intent = Intent(this, EditBookActivity::class.java)
                intent.putExtra(BOOK_KEY, id)
                startActivity(intent)
            }

            adapter.setOnItemLongClickListener { item, _ ->
                val bookItem = item as BookItem

                AlertDialog.Builder(this)
                    .setTitle("UWAGA")
                    .setMessage("Czy na pewno chcesz usunąć tę książkę?")
                    .setPositiveButton("OK") { _, _ ->
                        Thread{
                            AppDB.getInstance(this)!!.bookDao().deleteBook(item.book)

                            runOnUiThread {
                                adapter.clear()
                                setupData()
                            }
                        }.start()

                        Toast.makeText(this, "Książka została usunięta.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Anuluj") { _, _ -> }
                    .show()

                item.isLongClickable
            }

            runOnUiThread {
                booksList_RecyclerView.adapter = adapter
            }

        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.books, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.books_filter -> {
                openFilterDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        super.onBackPressed()
    }

    private fun openFilterDialog(){
        val values = arrayOf<CharSequence>(" Autor ", " Tytuł ", " Status ")
        var checkedItem: Int = -1

        checkedItem = when (bookFilter) {
            BookFilter.Author -> 0
            BookFilter.Title -> 1
            BookFilter.Status -> 2
        }

        AlertDialog.Builder(this)
            .setTitle("Sortuj wg:")
            .setSingleChoiceItems(values, checkedItem, DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> {
                        bookFilter = BookFilter.Author
                        dialog.dismiss()
                        setupData()
                    }
                    1 -> {
                        bookFilter = BookFilter.Title
                        dialog.dismiss()
                        setupData()
                    }
                    2 -> {
                        bookFilter = BookFilter.Status
                        dialog.dismiss()
                        setupData()
                    }
                }
            })
            .show()
    }
}
