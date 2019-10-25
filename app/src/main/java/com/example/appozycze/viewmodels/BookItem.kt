package com.example.appozycze.viewmodels

import com.example.appozycze.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_row.view.*

class BookItem : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.firstColumnRow_TextView.text = ""
        viewHolder.itemView.secondColumnRow_TextView.text = ""
        viewHolder.itemView.thirdColumnRow_TextView.text = ""
    }

}