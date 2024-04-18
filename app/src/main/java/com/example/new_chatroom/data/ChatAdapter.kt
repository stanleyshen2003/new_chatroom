package com.example.chatroom_line.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_chatroom.R


class ChatAdapter(private val context: Context, private var chatList: List<Chat>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val leftChatTextView: TextView = itemView.findViewById(R.id.left_chat_textview)
        private val rightChatTextView: TextView = itemView.findViewById(R.id.right_chat_textview)
        private val leftChatLayout: LinearLayout = itemView.findViewById(R.id.left_chat_layout)
        private val rightChatLayout: LinearLayout = itemView.findViewById(R.id.right_chat_layout)
        fun bind(chatMessage: Chat) {
            if (chatMessage.name == "Yourself") {
                leftChatLayout.visibility = LinearLayout.GONE
                rightChatTextView.visibility = View.VISIBLE
                leftChatTextView.visibility = View.GONE
                rightChatTextView.text = chatMessage.message
            } else {
                rightChatLayout.visibility = LinearLayout.GONE
                leftChatTextView.visibility = View.VISIBLE
                rightChatTextView.visibility = View.GONE
                leftChatTextView.text = chatMessage.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_row, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = chatList[position]
        holder.bind(chatMessage)
        if (position == chatList.size - 1) {
            recyclerView.smoothScrollToPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun updateData(newChatList: List<Chat>) {
        chatList = newChatList.toMutableList()
        notifyDataSetChanged()
    }

}
