package com.example.deadliner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.deadliner.data.AppDatabase
import com.example.deadliner.model.Deadline
import com.example.deadliner.repository.DeadlineRepository
import io.reactivex.Observable
import kotlinx.coroutines.launch

/**
 * Deadline view model
 *
 * @constructor
 *
 * @param application
 */
class DeadlineViewModel(application: Application) : AndroidViewModel(application) {

    val allDeadlines: LiveData<List<Deadline>>

    private val deadlineRepository: DeadlineRepository
    init {
        val deadlineDao = AppDatabase.getDatabase(application).deadlineDao()
        deadlineRepository = DeadlineRepository(deadlineDao)
        allDeadlines = deadlineRepository.allDeadlines
    }

    /**
     * Add deadline
     *
     * @param deadline
     */
    fun addDeadline(deadline: Deadline){
        viewModelScope.launch {
            deadlineRepository.addDeadline(deadline)
        }
    }

    /**
     * Update deadline
     *
     * @param deadline
     */
    fun updateDeadline(deadline: Deadline){
        viewModelScope.launch {
            deadlineRepository.updateDeadline(deadline)
        }
    }

    /**
     * Delete deadline
     *
     * @param deadline
     */
    fun deleteDeadline(deadline: Deadline){
        viewModelScope.launch {
            deadlineRepository.deleteDeadline(deadline)
        }
    }


}