package com.example.deadliner.repository

import androidx.lifecycle.LiveData
import com.example.deadliner.data.DeadlineDao
import com.example.deadliner.model.Deadline
import io.reactivex.Observable
import java.util.*

/**
 * Deadline repository
 *
 * @property deadlineDao
 * @constructor Create empty Deadline repository
 */
class DeadlineRepository(private val deadlineDao: DeadlineDao) {
    /**
     * All deadlines
     */
    val allDeadlines: LiveData<List<Deadline>> = deadlineDao.getDeadlines()

    /**
     * Add deadline
     *
     * @param deadline
     */
    suspend fun addDeadline(deadline: Deadline) {
        deadlineDao.addDeadline(deadline)
    }

    /**
     * Delete deadline
     *
     * @param deadline
     */
    suspend fun deleteDeadline(deadline: Deadline) {
        deadlineDao.deleteDeadline(deadline)
    }

    /**
     * Update deadline
     *
     * @param deadline
     */
    suspend fun updateDeadline(deadline: Deadline) {
        deadlineDao.updateDeadline(deadline)
    }

    suspend fun deleteServerDeadlines(){
        deadlineDao.deleteServerDeadlines()
    }
}