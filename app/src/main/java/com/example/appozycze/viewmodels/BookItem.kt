package com.example.appozycze.viewmodels

import com.example.appozycze.R
import com.example.appozycze.database.BookEntity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_row.view.*

class BookItem(val book: BookEntity) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.firstColumnRow_TextView.text = book.bookAuthor
        viewHolder.itemView.secondColumnRow_TextView.text = book.bookTitle

        if (book.bookStatus == "AT_HOME") {
            viewHolder.itemView.thirdColumnRow_CheckBox.isChecked = true
        } else {
            viewHolder.itemView.thirdColumnRow_CheckBox.isChecked = false
        }

    }

}