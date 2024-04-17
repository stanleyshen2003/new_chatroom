package com.example.new_chatroom

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val database = FirebaseDatabase.getInstance().reference.child("chat")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e("no error", "datachanged")
                for (postSnapshot in dataSnapshot.children) {
                    // Retrieve post details
                    val postTitle = postSnapshot.child("message").getValue(String::class.java)
                    val postContent = postSnapshot.child("name").getValue(String::class.java)
                    Log.d(TAG, "Post Title: $postTitle")
                    Log.d(TAG, "Post Content: $postContent")

                    // TODO: handle the post (e.g., display it in your UI)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        // button event listener
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
                Log.d("Sending", "Send message successfully.")
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}