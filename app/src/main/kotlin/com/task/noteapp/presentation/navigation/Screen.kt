package com.task.noteapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Note : Screen("note")
}
