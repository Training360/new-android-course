package com.example.demodatingapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demodatingapp.vo.Person

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(persons: List<Person>)

    @Query("SELECT * FROM person")
    fun findAll(): LiveData<List<Person>>

    @Query("SELECT * FROM person WHERE id = :id")
    fun findById(id: Int): LiveData<Person>

}