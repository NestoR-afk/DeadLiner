package com.example.deadliner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.deadliner.data.AppDatabase
import com.example.deadliner.model.Subject
import com.example.deadliner.repository.SubjectRepository
import kotlinx.coroutines.launch
import java.util.*

/**
 * Subject view model
 *
 * @constructor
 *
 * @param application
 */
class SubjectViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * All subjects
     */
    val allSubjects: LiveData<List<Subject>>

    /**
     * Subject repository
     */
    private val subjectRepository: SubjectRepository

    init {
        val subjectDao = AppDatabase.getDatabase(application).subjectDao()
        subjectRepository = SubjectRepository(subjectDao)
        allSubjects = subjectRepository.allSubjects
    }

    /**
     * Add subject
     *
     * @param subject
     */
    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.addSubject(subject)
        }
    }

    /**
     * Update subject
     *
     * @param subject
     */
    fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.updateSubject(subject)
        }
    }

    /**
     * Delete subject
     *
     * @param subject
     */
    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.deleteSubject(subject)
        }
    }

    /**
     * Get subjects by date
     *
     * @param date
     * @return
     */
    fun getSubjectsByDate(date: Date): MutableList<Subject> {
        /**
         * Subjects
         */
        val subjects = mutableListOf<Subject>()

        /**
         * Subject calendar
         */
        val subjectCalendar = Calendar.getInstance()

        /**
         * Today calendar
         */
        val todayCalendar = Calendar.getInstance()
        todayCalendar.time = date
        if (allSubjects.value != null) {
            for (subj in allSubjects.value!!) {
                subjectCalendar.time = subj.date
                val range = todayCalendar.get(Calendar.DAY_OF_YEAR) - subjectCalendar.get(Calendar.DAY_OF_YEAR)
                if ((range % subj.howOften == 0) || (range == 0))
                    subjects.add(subj)
            }
        }
        return subjects
    }
}