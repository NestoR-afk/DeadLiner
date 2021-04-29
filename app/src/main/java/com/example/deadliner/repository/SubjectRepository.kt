package com.example.deadliner.repository

import androidx.lifecycle.LiveData
import com.example.deadliner.data.SubjectDao
import com.example.deadliner.model.Subject
import java.util.*

class SubjectRepository(private val subjectDao: SubjectDao) {

    val allSubjects: LiveData<List<Subject>> = subjectDao.getSubjects()

    suspend fun addSubject(subject: Subject) {
        subjectDao.addSubject(subject)
    }

    suspend fun deleteSubject(subject: Subject) {
        subjectDao.deleteSubject(subject)
    }

    suspend fun updateSubject(subject: Subject) {
        subjectDao.updateSubject(subject)
    }
}