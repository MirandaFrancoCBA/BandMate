package com.example.bandmate.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var selectedRole by mutableStateOf<String?>(null)
        private set

    fun selectRole(role: String) {
        selectedRole = role
    }
}