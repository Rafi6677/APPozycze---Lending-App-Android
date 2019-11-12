package com.example.appozycze.activities.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.appozycze.R
import com.example.appozycze.activities.MainActivity
import com.example.appozycze.viewmodels.BookFilter
import kotlinx.android.synthetic.main.fragment_book_filter.*

class BookFilterFragment : DialogFragment() {
/*
    private var currentFilter: BookFilter = BooksActivity.bookFilter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_book_filter, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (currentFilter) {
            BookFilter.Author -> bookAuthorFilter_RadioButton.isChecked = true
            BookFilter.Title -> bookTitleFilter_RadioButton.isChecked = true
            BookFilter.Status -> bookStatusFilter_RadioButton.isChecked = true
        }

        bookFilter_RadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = group.findViewById(checkedId)

            when (radioButton.tag) {
                "author" -> BooksActivity.bookFilter = BookFilter.Author
                "title" -> BooksActivity.bookFilter = BookFilter.Title
                "status" -> BooksActivity.bookFilter = BookFilter.Status
            }
        }
    }
*/
}