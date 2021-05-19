package com.example.deadliner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.deadliner.model.Subject

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubject(subject: Subject)

    @Update
    suspend fun updateSubject(subject: Subject)

    @Delete
    suspend fun deleteSubject(subject: Subject)

    @Query("SELECT * FROM subjects_table ORDER BY date")
    fun getSubjects(): LiveData<List<Subject>>
}