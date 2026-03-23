package com.example.mentalhealth.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodEntryDao {

    @Insert
    suspend fun insert(entry: MoodEntry): Long

    @Query("SELECT COUNT(*) FROM mood_entries")
    fun observeCount(): Flow<Int>

    @Query("SELECT * FROM mood_entries ORDER BY createdAtEpochMs DESC LIMIT 10")
    fun observeRecent(): Flow<List<MoodEntry>>
}
