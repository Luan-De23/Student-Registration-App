package com.example.studentregistration.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student")
data class Students(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val course: String,
    val year: Int
)
