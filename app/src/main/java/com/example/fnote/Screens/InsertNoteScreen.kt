package com.example.fnote.Screens

import android.R.attr.contentDescription
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fnote.Notes
import com.example.fnote.ui.theme.colorBlack
import com.example.fnote.ui.theme.colorGrey
import com.example.fnote.ui.theme.colorLightGrey
import com.example.fnote.ui.theme.colorRed
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun InsertNoteScreen(navHostController: NavHostController, id1: String?) {
    val context= LocalContext.current
    val db= FirebaseFirestore.getInstance()
    val notesDb=db.collection("notes")
    val title= remember { mutableStateOf("") }
    val description= remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        if (!id1.isNullOrEmpty() && id1 != "defaultId") {
            notesDb.document(id1).get().addOnSuccessListener { documentSnapshot ->
                val singaledata = documentSnapshot.toObject(Notes::class.java)
                singaledata?.let {
                    title.value = it.title
                    description.value = it.description
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (title.value.isEmpty() || description.value.isEmpty()){
                    Toast.makeText(context,"Enter valid Data",Toast.LENGTH_SHORT).show()
                }else{
                    val myNotesId: String = if (!id1.isNullOrEmpty() && id1 != "defaultId") {
                        id1
                    } else {
                        notesDb.document().id
                    }

                    val notes= Notes(myNotesId, title.value, description.value)
                    notesDb.document(myNotesId).set(notes).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(context,"Notes Saved Successfully",Toast.LENGTH_SHORT).show()
                            navHostController.popBackStack()
                        }else{
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
                contentColor = Color.White,
                containerColor = colorRed,
                shape = RoundedCornerShape(corner = CornerSize(100.dp))
            )
            {
                Image(imageVector = Icons.Default.Done,
                contentDescription = "Done",
                // Explicitly set the tint color to white
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White))
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorBlack)
        ) {
            Column (modifier= Modifier.padding(15.dp)){
                Text(text = "Insert Data",
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif))
                Spacer(modifier = Modifier.height(20.dp))
                TextField(textStyle = TextStyle(color = Color.White, fontSize = 18.sp), colors = TextFieldDefaults.colors
                    (focusedContainerColor = colorGrey,
                    unfocusedContainerColor = colorGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White),
                    shape= RoundedCornerShape(corner = CornerSize(10.dp)),
                    label = {
                        Text(text = "Enter your Title", style = TextStyle(fontSize = 18.sp, color = colorLightGrey))
                    }, value = title.value, onValueChange = {
                        title.value=it
                    },modifier= Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(30.dp))
                TextField(textStyle = TextStyle(color = Color.White, fontSize = 18.sp),colors = TextFieldDefaults.colors
                    (focusedContainerColor = colorGrey,
                    unfocusedContainerColor = colorGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White),
                    shape= RoundedCornerShape(corner = CornerSize(10.dp)),
                    label = {
                        Text(text = "Enter your Description", style = TextStyle(fontSize = 18.sp, color = colorLightGrey))
                    }, value = description.value, onValueChange = {
                        description.value=it
                    },modifier= Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f))
            }
        }
    }
}