package com.example.appozycze.viewmodels

import com.example.appozycze.R
import com.example.appozycze.database.GameEntity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.game_item_row.view.*

class GameItem(val game: GameEntity): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.game_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.firstColumnRow_TextView.text = game.gameTitle
        viewHolder.itemView.secondColumnRow_TextView.text = game.gamePlatform

        if (game.gameStatus == "AT_HOME") {
            viewHolder.itemView.thirdColumnRow_CheckBox.isChecked = true
        } else {
            viewHolder.itemView.thirdColumnRow_CheckBox.isChecked = false
        }
    }

}