package com.example.appozycze.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDAO {

    @Insert
    fun saveGameToDB(game: GameEntity)

    @Query("SELECT * FROM GameEntity")
    fun getGames(): List<GameEntity>

    @Query("SELECT * FROM GameEntity WHERE gameID LIKE :id")
    fun getSingleGame(id: Int): GameEntity

    @Query("UPDATE GameEntity SET gameLendDate = :date, gameStatus = :status, gameLendTo = :lendTo WHERE gameID LIKE :id")
    fun changeGameStatusToBorrowed(id: Int, status: String, lendTo: String, date: String)

    @Query("UPDATE GameEntity SET gameStatus = :status, gameLendTo = :lendTo WHERE gameID LIKE :id")
    fun changeGameStatusToReturned(id: Int, status: String, lendTo: String)

    @Query("UPDATE GameEntity SET gameTitle = :title, gamePlatform = :platform WHERE gameID LIKE :id")
    fun updateGameData(id: Int, title: String, platform: String)

    @Query("DELETE FROM GameEntity")
    fun deleteAllGames()

    @Delete
    fun deleteGame(game: GameEntity)
}