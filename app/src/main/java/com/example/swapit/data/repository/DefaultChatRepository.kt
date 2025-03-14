package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteChatDataSource
import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatListResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomListResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomProductResponse
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.chat.ChatList
import com.example.swapit.domain.model.chat.ChatRoom
import com.example.swapit.domain.model.chat.ChatRoomProduct
import com.example.swapit.domain.repository.ChatRepository

class DefaultChatRepository(private val remoteSource: RemoteChatDataSource) : ChatRepository {
    override suspend fun createChatRoom(goodsId: GoodsIdRequest): BaseResponse<Long> {
        return remoteSource.createChatRoom(goodsId)
    }

    override suspend fun createSwapChatRoom(tradesId: TradesIdRequest): BaseResponse<Long> {
        return remoteSource.createSwapChatRoom(tradesId)
    }

    override suspend fun chatRoomList(): List<ChatRoom> {
        return remoteSource.chatRoomList().results.chatRoomList.map { it.toDomain() }
    }

    override suspend fun chatList(chatroomId: Long): ChatList {
        return remoteSource.chatList(chatroomId).results.toDomain()
    }

    override suspend fun chatRoomProduct(chatroomId: Long): ChatRoomProduct {
        return remoteSource.chatRoomProduct(chatroomId).results.toDomain()
    }
}