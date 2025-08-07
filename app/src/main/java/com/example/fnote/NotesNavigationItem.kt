package com.example.fnote

sealed class NotesNavigationItem(val route:String){
    object SplashScreen:NotesNavigationItem("splash")
    object HomeScreen:NotesNavigationItem("home")
    object InsertNotesScreen:NotesNavigationItem("create_notes")


}