package com.example.new_chatroom

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatroom_line.data.Chat
import com.example.chatroom_line.data.ChatAdapter
import com.example.chatroom_line.data.DataSource
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val database = FirebaseDatabase.getInstance().reference.child("chat")

        val dataSource = DataSource.getDataSource(resources)
        val chatList = dataSource.getChatList().value?.toMutableList() ?: mutableListOf()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this)

        adapter = ChatAdapter(this, chatList ?: emptyList(), recyclerView)
        recyclerView.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val chatList = mutableListOf<Chat>()
                for (postSnapshot in dataSnapshot.children) {
                    // Retrieve post details
                    val message = postSnapshot.child("message").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    val timestamp = postSnapshot.child("timestamp").getValue(Long::class.java)
                    val newTimestamp: Long = timestamp ?: 0L
                    // Create a Chat object
                    val chat = Chat(name, message, newTimestamp)

                    // Insert the chat into the sorted position in the list
                    insertSorted(chatList, chat)
                }
                runOnUiThread {
                    adapter.updateData(chatList)
                    for (chat in chatList){
                        chat.name?.let { Log.d("name", it) }
                        chat.message?.let { Log.d("message", it) }
                        Log.d("timestamp", chat.timestamp.toString())
                    }
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
                    "timestamp" to System.currentTimeMillis()
                )
                FirebaseDatabase.getInstance().reference.child("chat").push().setValue(message)
                textInput.text = null
                Log.d("Sending", "Send message successfully.")
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    private fun insertSorted(chatList: MutableList<Chat>, chat: Chat) {
        var i = chatList.size - 1
        while (i >= 0 && chatList[i].timestamp > chat.timestamp) {
            i--
        }
        chatList.add(i + 1, chat)
    }
}