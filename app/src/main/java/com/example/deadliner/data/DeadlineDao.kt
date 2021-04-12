package com.example.deadliner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.deadliner.model.Deadline
import io.reactivex.Observable


@Dao
interface DeadlineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDeadline(deadline: Deadline)

    @Update
    suspend fun updateDeadline(deadline: Deadline)

    @Delete
        suspend fun deleteDeadline(deadline: Deadline)

    @Query("SELECT * FROM deadlines_table ORDER BY date")
         fun getDeadlines() : LiveData<List<Deadline>>

}
