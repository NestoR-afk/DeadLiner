package com.example.deadliner.repository

import androidx.lifecycle.LiveData
import com.example.deadliner.data.DeadlineDao
import com.example.deadliner.model.Deadline
import io.reactivex.Observable

class DeadlineRepository(private val deadlineDao: DeadlineDao) {

    val allDeadlines: LiveData<List<Deadline>> = deadlineDao.getDeadlines()

    suspend fun addDeadline(deadline: Deadline) {
        deadlineDao.addDeadline(deadline)
    }

    suspend fun deleteDeadline(deadline: Deadline) {
        deadlineDao.deleteDeadline(deadline)
    }

    suspend fun updateDeadline(deadline: Deadline) {
        deadlineDao.updateDeadline(deadline)
    }
}