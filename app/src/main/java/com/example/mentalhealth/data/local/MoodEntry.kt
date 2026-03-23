package com.example.mentalhealth.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** 1–5 scale */
    val moodScore: Int,
    val note: String?,
    val createdAtEpochMs: Long = System.currentTimeMillis(),
)
