package com.example.demodatingapp

import android.app.Application
import com.example.demodatingapp.db.JingleDatabase
import com.example.demodatingapp.db.dao.PersonDao

class JingleApplication: Application() {
    fun personDao(): PersonDao {
        return JingleDatabase.database(this).personDao()
    }
}
