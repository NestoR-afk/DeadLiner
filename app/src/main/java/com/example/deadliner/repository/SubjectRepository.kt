package com.example.deadliner.repository

import androidx.lifecycle.LiveData
import com.example.deadliner.data.SubjectDao
import com.example.deadliner.model.Subject
import java.util.*

/**
 * Subject repository
 *
 * @property subjectDao
 * @constructor Create empty Subject repository
 */
class SubjectRepository(private val subjectDao: SubjectDao) {
    /**
     * All subjects
     */
    val allSubjects: LiveData<List<Subject>> = subjectDao.getSubjects()

    /**
     * Add subject
     *
     * @param subject
     */
    suspend fun addSubject(subject: Subject) {
        subjectDao.addSubject(subject)
    }

    /**
     * Delete subject
     *
     * @param subject
     */
    suspend fun deleteSubject(subject: Subject) {
        subjectDao.deleteSubject(subject)
    }

    /**
     * Update subject
     *
     * @param subject
     */
    suspend fun updateSubject(subject: Subject) {
        subjectDao.updateSubject(subject)
    }
}