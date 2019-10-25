package com.example.appozycze.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BookEntity {

    @PrimaryKey(autoGenerate = true)
    var bookID: Int = 0

    @ColumnInfo(name = "bookTitle")
    var bookTitle: String = ""

    @ColumnInfo(name = "bookAuthor")
    var bookAuthor: String = ""

    @ColumnInfo(name = "bookStatus")
    var bookStatus: String = ""

    @ColumnInfo(name = "bookLendTo")
    var bookLendTo: String = ""

    constructor()

    constructor(title: String, author: String, status: String, lendTo: String) {
        bookTitle = title
        bookAuthor = author
        bookStatus = status
        bookLendTo = lendTo
    }

}