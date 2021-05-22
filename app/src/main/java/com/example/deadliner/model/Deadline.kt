package com.example.deadliner.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Deadline
 *
 * @property id
 * @property subject
 * @property description
 * @property date
 * @constructor Create empty Deadline
 */
@Entity(tableName = "deadlines_table")
@Parcelize
class Deadline(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val subject: String,
        val description: String,
        val date: Date?
) : Parcelable