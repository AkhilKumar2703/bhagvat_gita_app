package com.techmania.shreebhagavadgita.datasource.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SavedChapters::class], version = 1, exportSchema = false)
@TypeConverters(AppTypeConverters::class)

abstract class AppDatabase : RoomDatabase(){
    abstract fun savedChapterDao() : SavedChaptersDao




    companion object{
        @Volatile
        var INSTANCE : AppDatabase? = null

        fun getDatabaseInstance(context : Context) : AppDatabase?{
            val tempInstance = INSTANCE
            if(INSTANCE != null){
                return tempInstance
            }
            synchronized(this){
                val roomDb = Room.databaseBuilder(context,AppDatabase::class.java,"AppDatabase").fallbackToDestructiveMigration().build()
                INSTANCE = roomDb
                return roomDb
            }
        }
    }
}