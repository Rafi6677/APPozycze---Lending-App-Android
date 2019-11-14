package com.example.appozycze.viewmodels

import com.example.appozycze.R
import com.example.appozycze.database.GameEntity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.book_item_row.view.*

class GameItem(val game: GameEntity): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.game_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.firstColumnRow_TextView.text = ""
        viewHolder.itemView.secondColumnRow_TextView.text = ""
        viewHolder.itemView.thirdColumnRow_CheckBox.isChecked = true
    }

}