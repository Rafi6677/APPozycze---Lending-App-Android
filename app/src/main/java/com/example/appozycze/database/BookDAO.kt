package com.example.appozycze.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDAO {

    @Insert
    fun saveBook(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getBooks(): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE bookID LIKE :id")
    fun getSingleBook(id: Int): BookEntity

}