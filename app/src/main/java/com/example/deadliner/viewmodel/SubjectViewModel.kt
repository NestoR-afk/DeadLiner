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

class SubjectViewModel(application: Application) : AndroidViewModel(application) {

    val allSubjects: LiveData<List<Subject>>

    private val subjectRepository: SubjectRepository

    init {
        val subjectDao = AppDatabase.getDatabase(application).subjectDao()
        subjectRepository = SubjectRepository(subjectDao)
        allSubjects = subjectRepository.allSubjects
    }

    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.addSubject(subject)
        }
    }

    fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.updateSubject(subject)
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.deleteSubject(subject)
        }
    }

    fun getSubjectsByDate(date: Date): MutableList<Subject> {
        val subjects = mutableListOf<Subject>()
        val subjectCalendar = Calendar.getInstance()
        val todayCalendar = Calendar.getInstance()
        todayCalendar.time = date
        if (allSubjects.value != null) {
            for (subj in allSubjects.value!!) {
                subjectCalendar.timeInMillis = subj.date
                val range = todayCalendar.get(Calendar.DAY_OF_YEAR) - subjectCalendar.get(Calendar.DAY_OF_YEAR)
                if ((range % subj.howOften == 0) || (range == 0))
                    subjects.add(subj)
            }
        }
        return subjects
    }
}