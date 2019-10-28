package com.example.appozycze.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [(BookEntity::class), (GameEntity::class)], version = 2)
abstract class AppDB : RoomDatabase() {

    abstract fun bookDao(): BookDAO

    companion object {
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB? {
            if (instance == null) {
                synchronized(AppDB::class) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDB::class.java, "app_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //PopulateDbAsync(instance).execute()
            }
        }
    }

}