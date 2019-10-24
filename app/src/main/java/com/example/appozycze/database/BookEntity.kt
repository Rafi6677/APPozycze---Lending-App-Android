package com.example.appozycze.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BookEntity {

    @PrimaryKey
    var bookID: Int = 0

    @ColumnInfo(name = "bookTitle")
    var bookTitle: String = ""

    @ColumnInfo(name = "bookAuthor")
    var bookAuthor: String = ""

    @ColumnInfo(name = "bookStatus")
    var bookStatus: String = ""

    @ColumnInfo(name = "bookLendTo")
    var bookLendTo: String = ""

}