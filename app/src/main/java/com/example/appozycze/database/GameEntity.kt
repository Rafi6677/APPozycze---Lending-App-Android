package com.example.appozycze.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GameEntity {

    @PrimaryKey(autoGenerate = true)
    var gameID: Int = 0

    @ColumnInfo(name = "gameTitle")
    var gameTitle: String = ""

    @ColumnInfo(name = "gamePlatform")
    var gamePlatform: String = ""

    @ColumnInfo(name = "gameStatus")
    var gameStatus: String = ""

    @ColumnInfo(name = "gameLendTo")
    var gameLendTo: String = ""

    @ColumnInfo(name = "gameLendDate")
    var gameLendDate: String = ""

    constructor()

    constructor(title: String, platform: String, status: String, lendTo: String) {
        gameTitle = title
        gamePlatform = platform
        gameStatus = status
        gameLendTo = lendTo
        gameLendDate = ""
    }

}