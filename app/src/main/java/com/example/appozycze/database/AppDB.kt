package com.example.appozycze.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(BookEntity::class), (GameEntity::class)], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun saveBook() : BookDAO

}