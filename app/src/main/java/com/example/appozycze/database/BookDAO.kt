package com.example.appozycze.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDAO {

    @Insert
    fun saveBook(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getBooks(): List<BookEntity>

}