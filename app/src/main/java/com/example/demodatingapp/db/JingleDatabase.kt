package com.example.demodatingapp.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.demodatingapp.db.dao.PersonDao
import com.example.demodatingapp.vo.Person

@Database(entities = [Person::class], version = 3)
@TypeConverters(com.example.demodatingapp.util.TypeConverters::class)
abstract class JingleDatabase: RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {

        private const val DATABASE_NAME = "jingle.db"

        @JvmStatic
        fun database(application: Application): JingleDatabase {
            return Room
                    .databaseBuilder(application, JingleDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}