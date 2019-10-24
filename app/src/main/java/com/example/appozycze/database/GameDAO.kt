package com.example.appozycze.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface GameDAO {

    @Insert
    fun saveGameToDB(book: BookEntity) {

    }

}