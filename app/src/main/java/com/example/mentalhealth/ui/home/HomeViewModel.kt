package com.example.mentalhealth.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mentalhealth.data.mood.MoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moodRepository: MoodRepository,
) : ViewModel() {

    val moodLogCount: Flow<Int> = moodRepository.observeMoodLogCount()

    fun logQuickMood() {
        viewModelScope.launch {
            moodRepository.logMood(score = 3, note = null)
        }
    }
}

class HomeViewModelFactory(
    private val moodRepository: MoodRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(moodRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
