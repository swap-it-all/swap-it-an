package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatListResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomInfoResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomListResponse
import com.example.swapit.data.datasource.remote.service.ChatService

class RemoteChatDataSource(private val service: ChatService) {
    suspend fun createChatRoom(goodsId: GoodsIdRequest): BaseResponse<Long> = service.createChatRoom(goodsId)

    suspend fun createSwapChatRoom(tradesId: TradesIdRequest): BaseResponse<Long> = service.createSwapChatRoom(tradesId)

    suspend fun chatRoomList(): BaseResponse<ChatRoomListResponse> = service.chatRoomList()

    suspend fun chatList(chatroomId: Long): BaseResponse<ChatListResponse> = service.chatList(chatroomId)

    suspend fun chatRoomInfo(chatroomId: Long): BaseResponse<ChatRoomInfoResponse> = service.chatRoomInfo(chatroomId)
}
