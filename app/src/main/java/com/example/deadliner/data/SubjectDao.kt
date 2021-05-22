package com.example.deadliner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.deadliner.model.Subject

/**
 * Subject dao
 *
 * @constructor Create empty Subject dao
 */
@Dao
interface SubjectDao {
    /**
     * Add subject
     *
     * @param subject
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubject(subject: Subject)

    /**
     * Update subject
     *
     * @param subject
     */
    @Update
    suspend fun updateSubject(subject: Subject)

    /**
     * Delete subject
     *
     * @param subject
     */
    @Delete
    suspend fun deleteSubject(subject: Subject)

    /**
     * Get subjects
     *
     * @return
     */
    @Query("SELECT * FROM subjects_table ORDER BY date")
    fun getSubjects(): LiveData<List<Subject>>

    @Query("DELETE FROM subjects_table WHERE isFromServer = 1")
    suspend fun deleteServerSubjects()
}