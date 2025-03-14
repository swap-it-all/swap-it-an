package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.chat.ChatListResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomProductResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatSwapProductResponse
import com.example.swapit.domain.model.chat.Chat
import com.example.swapit.domain.model.chat.ChatList
import com.example.swapit.domain.model.chat.ChatRoom
import com.example.swapit.domain.model.chat.ChatRoomProduct
import com.example.swapit.domain.model.chat.ChatSwapProduct
import kotlinx.serialization.Serializable

fun ChatSwapProductResponse.toDomain(): ChatSwapProduct {
    return ChatSwapProduct(
        goodsId = this.goodsId,
        title = this.title,
        requesterNickname = this.requesterNickname,
    )
}

fun ChatRoomProductResponse.toDomain(): ChatRoomProduct {
    return ChatRoomProduct(
        nickname = this.nickname,
        goodsId = this.goodsId,
        title = this.title,
        category = this.category,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}



fun ChatResponse.toDomain(): Chat {
    return Chat(
        chatsId = this.chatsId,
        chatType = this.chatType,
        content = this.content,
        senderId = this.senderId,
        createdAt = this.createdAt,
        requesterGoods = this.requesterGoods,
    )
}

fun ChatListResponse.toDomain(): ChatList {
    return ChatList(
        chatList = this.chatList,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        size = this.size,
    )
}

fun ChatRoomResponse.toDomain(): ChatRoom {
    return ChatRoom(
        chatroomsId = this.chatroomsId,
        usersId = this.usersId,
        profileImageUrl = this.profileImageUrl,
        nickname = this.nickname,
        recentChat = this.recentChat,
        recentChatTime = this.recentChatTime,
    )
}

