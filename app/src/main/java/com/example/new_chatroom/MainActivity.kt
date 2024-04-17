package com.example.new_chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            Log.i("Min", "hello")
            try {
                val textInput = findViewById<TextInputEditText>(R.id.editText)
                val message = hashMapOf(
                    "name" to "Yourself",
                    "message" to textInput.text.toString(),
                    "timesStamp" to System.currentTimeMillis()
                )
                FirebaseDatabase.getInstance().reference.child("chat").push().setValue(message)
                textInput.text = null
            } catch (e: Exception){
                e.printStackTrace();
                Log.e("MainActivity", "Error message")
                Log.d("MainActivity", "Debug message")
                Log.i("MainActivity", "Info message")
                Log.w("MainActivity", "Warning message")
            }
        }
    }
}