package com.example.appozycze.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BookDAO {

    @Insert
    fun saveBookToDB(book: BookEntity) {

    }

}