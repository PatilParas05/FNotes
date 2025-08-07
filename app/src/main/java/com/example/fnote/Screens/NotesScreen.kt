package com.example.fnote.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fnote.ui.theme.colorBlack
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.fnote.Notes
import com.example.fnote.ui.theme.colorGrey
import com.example.fnote.ui.theme.colorLightGrey
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.PopupProperties
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.fnote.NotesNavigationItem
import com.google.firebase.firestore.CollectionReference


@Composable
fun NotesScreen(navHostController: NavHostController) {
    val db= FirebaseFirestore.getInstance()
    val notesDb=db.collection("notes")
    val notesList= remember(){
        mutableStateListOf<Notes>()
    }
    val isLoading= remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit){
    notesDb.addSnapshotListener { value, error ->
        if (error==null){
            val data =value?.toObjects(Notes::class.java)
            notesList.clear()
            if (data != null) notesList.addAll(data)
            isLoading.value = false
        }else{
            isLoading.value = false
        }
    }
}
    Scaffold (
     floatingActionButton = {
         FloatingActionButton(contentColor = Color.White,containerColor = Color.Red,
             shape = RoundedCornerShape(corner = CornerSize(100.dp)),
              onClick = {

        navHostController.navigate(NotesNavigationItem.InsertNotesScreen.route+"?id=")
              }) {
             Icon(imageVector =  Icons.Default.Add, contentDescription = "Add")
         }
     }
    ){ innnerpadding->
        Box(modifier= Modifier
            .padding(innnerpadding) 
            .fillMaxSize()
            .background(colorBlack)
        )
            {

            Column (modifier= Modifier.padding(15.dp).fillMaxWidth()){
                Text(text = "Create Notes",
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif
                    )
                )
                if(isLoading.value){ // Check isLoading state
                    Box(modifier= Modifier.fillMaxSize()){
                        CircularProgressIndicator(modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center)
                            )
                    }
                }else{
                    // Display content when not loading
                }

                LazyColumn{
                    items(notesList){
                        notes->
                        ListItems( notes,notesDb,navHostController )
                    }
                    }
                 }
            }
        }
    }


@Composable
fun ListItems(notes: Notes, notesDb: CollectionReference, navHostController: NavHostController) {
    val context= LocalContext.current
    var expanded by remember {mutableStateOf(false)}
Box(modifier = Modifier
    .fillMaxWidth()
    .padding(10.dp)
    .clip(shape = RoundedCornerShape(corner = CornerSize(20.dp)))
    .background(colorGrey)
)
{
    Box(modifier = Modifier.align(Alignment.TopEnd)) {
        Icon(imageVector = Icons.Default.MoreVert,
            contentDescription = "More", tint = Color.White,
            modifier = Modifier.padding(10.dp).clickable {
                expanded = true
            })
        DropdownMenu(modifier = Modifier
            .background(color=Color.White),
            properties= PopupProperties(clippingEnabled = false),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Update") }, onClick = {
               navHostController.navigate(NotesNavigationItem.InsertNotesScreen.route+"?id=${notes.id}")
                expanded=false
            })
            DropdownMenuItem(text = { Text("Delete") }, onClick = {
                notes.id

                val alertDialogBuilder= android.app.AlertDialog.Builder(context)
                alertDialogBuilder.setTitle("Confirm Delete")
                alertDialogBuilder.setMessage("Are you sure you want to delete this note?")
                alertDialogBuilder.setPositiveButton("Yes"){ dialog, _ ->
                        notesDb.document(notes.id).delete()
                        dialog.dismiss()
                    Toast.makeText(context,"Note Deleted Successfully".toString(),Toast.LENGTH_SHORT).show()
                }
                alertDialogBuilder.setNegativeButton("No"){ dialog, _ ->
                        dialog.dismiss()
                }
                val dialog = alertDialogBuilder.create()
                dialog.show()
                expanded = false
            })
        }
    }
    Column (modifier = Modifier.padding(20.dp)){
        Text(text=notes.title,style = TextStyle(fontSize = 16.sp, color = Color.White, )
        )
        Text(text=notes.description, style = TextStyle(fontSize = 12.sp, color = colorLightGrey), // Corrected color name
            modifier = Modifier.padding(top=10.dp))

    }
}
    }
