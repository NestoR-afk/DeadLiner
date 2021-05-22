package com.example.deadliner.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Subject
 *
 * @property id
 * @property name
 * @property type
 * @property place
 * @property teacher
 * @property date
 * @property howOften
 * @constructor Create empty Subject
 */
@Entity(tableName = "subjects_table")
@Parcelize
class Subject(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val name: String,
        val type: String,
        val place: String,
        val teacher: String,
        val date: Long,
        val howOften: Int,
        val isFromServer: Boolean
) : Parcelable