package com.example.mentalhealth.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert
    suspend fun insert(entry: JournalEntry): Long

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM journal_entries ORDER BY createdAtEpochMs DESC")
    fun observeAll(): Flow<List<JournalEntry>>
}
