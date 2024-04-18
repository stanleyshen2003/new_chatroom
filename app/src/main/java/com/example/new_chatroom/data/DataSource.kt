package com.example.chatroom_line.data

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialChatList = chatList(resources)
    private val chatsLiveData = MutableLiveData(initialChatList)

    fun getChatList(): MutableLiveData<List<Chat>> {
        return chatsLiveData
    }

    fun appendChat(chat: Chat) {
        val currentList = chatsLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(chat)
        chatsLiveData.value = currentList.toList()
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}