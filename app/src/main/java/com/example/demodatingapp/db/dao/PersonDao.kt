package com.example.demodatingapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(person: Person)
}