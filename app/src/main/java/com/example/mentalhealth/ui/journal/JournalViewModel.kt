package com.example.mentalhealth.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mentalhealth.data.journal.JournalRepository
import com.example.mentalhealth.data.local.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JournalViewModel(
    private val repository: JournalRepository,
) : ViewModel() {

    val entries: Flow<List<JournalEntry>> = repository.observeEntries()

    fun addEntry(title: String?, body: String) {
        viewModelScope.launch {
            repository.addEntry(title, body)
        }
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteEntry(id)
        }
    }
}

class JournalViewModelFactory(
    private val repository: JournalRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            return JournalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
