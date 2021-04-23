package com.example.deadliner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.deadliner.model.Deadline
import io.reactivex.Observable

/**
 * Deadline dao
 *
 * @constructor Create empty Deadline dao
 */
@Dao
interface DeadlineDao {
    /**
     * Add deadline
     *
     * @param deadline
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDeadline(deadline: Deadline)

    /**
     * Update deadline
     *
     * @param deadline
     */
    @Update
    suspend fun updateDeadline(deadline: Deadline)

    /**
     * Delete deadline
     *
     * @param deadline
     */
    @Delete
        suspend fun deleteDeadline(deadline: Deadline)

    /**
     * Get deadlines
     *
     * @return
     */
    @Query("SELECT * FROM deadlines_table ORDER BY date")
         fun getDeadlines() : LiveData<List<Deadline>>

}
