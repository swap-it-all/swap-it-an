package com.swapit.company.data.mapper

import com.swapit.company.data.datasource.remote.dto.response.chat.ChatListResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatRoomInfoResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatRoomResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatSwapProductResponse
import com.swapit.company.domain.model.chat.Chat
import com.swapit.company.domain.model.chat.ChatList
import com.swapit.company.domain.model.chat.ChatRoom
import com.swapit.company.domain.model.chat.ChatRoomInfo
import com.swapit.company.domain.model.chat.ChatSwapProduct

fun ChatSwapProductResponse.toDomain(): ChatSwapProduct {
    return ChatSwapProduct(
        goodsId = this.goodsId,
        title = this.title,
        requesterNickname = this.requesterNickname,
    )
}

fun ChatRoomInfoResponse.toDomain(): ChatRoomInfo {
    return ChatRoomInfo(
        goodsId = this.goodsId,
        title = this.title,
        category = this.category,
        price = this.price,
        imageUrl = this.imageUrl,
        usersId = this.usersId,
        nickname = this.nickname,
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
        unReadChatCount = this.unReadChatCount,
        chatroomId = this.chatroomId,
        profileImageUrl = this.profileImageUrl,
        nickname = this.nickname,
        recentChat = this.recentChat,
        recentChatTime = this.recentChatTime,
    )
}
