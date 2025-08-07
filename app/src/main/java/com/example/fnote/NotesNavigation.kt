package com.example.fnote

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fnote.Screens.NotesScreen
import com.example.fnote.Screens.SplashScreen
import com.example.fnote.Screens.InsertNoteScreen

@Composable
fun NotesNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "splash") {
        composable(NotesNavigationItem.SplashScreen.route) {
            SplashScreen(navHostController)
        }
        composable (NotesNavigationItem.HomeScreen.route){
            NotesScreen(navHostController)
        }
        composable (NotesNavigationItem.InsertNotesScreen.route+"?id={id}"){
            val id=it.arguments?.getString("id")
            InsertNoteScreen(  navHostController,id)
        }
    }
}

