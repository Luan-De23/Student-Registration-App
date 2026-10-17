package com.example.studentregistration.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Students): Long

    @Query("SELECT * FROM student WHERE id = :studentId")
    suspend fun getStudentById(studentId: Long): Students?
}