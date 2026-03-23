package com.example.mentalhealth.data.journal

import com.example.mentalhealth.data.local.JournalDao
import com.example.mentalhealth.data.local.JournalEntry
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val dao: JournalDao) {

    fun observeEntries(): Flow<List<JournalEntry>> = dao.observeAll()

    suspend fun addEntry(title: String?, body: String) {
        val trimmed = body.trim()
        require(trimmed.isNotEmpty()) { "body required" }
        val t = title?.trim()?.takeIf { it.isNotEmpty() }
        dao.insert(JournalEntry(title = t, body = trimmed))
    }

    suspend fun deleteEntry(id: Long) {
        dao.deleteById(id)
    }
}
