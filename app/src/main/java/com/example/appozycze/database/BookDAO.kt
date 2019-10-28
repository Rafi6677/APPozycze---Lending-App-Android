package com.example.appozycze.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface BookDAO {

    @Insert
    fun saveBook(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getBooks(): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE bookID LIKE :id")
    fun getSingleBook(id: Int): BookEntity

    @Query("UPDATE BookEntity SET bookLendDate = :date, bookStatus = :status, bookLendTo = :lendTo WHERE bookID LIKE :id")
    fun changeBookStatusToBorrowed(id: Int, status: String, lendTo: String, date: String)

    @Query("UPDATE BookEntity SET bookStatus = :status, bookLendTo = :lendTo WHERE bookID LIKE :id")
    fun changeBookStatusToReturned(id: Int, status: String, lendTo: String)

    @Query("UPDATE BookEntity SET bookTitle = :title, bookAuthor = :author WHERE bookID LIKE :id")
    fun updateBookData(id: Int, title: String, author: String)

}