package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteChatDataSource
import com.example.swapit.data.datasource.RemoteSwapDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatListResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomListResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatRoomProductResponse
import com.example.swapit.data.repository.DefaultChatRepository
import com.example.swapit.data.repository.DefaultSwapRepository
import com.example.swapit.domain.model.chat.ChatList
import com.example.swapit.domain.model.chat.ChatRoom
import com.example.swapit.domain.model.chat.ChatRoomProduct

interface ChatRepository {

    suspend fun createChatRoom(goodsId: GoodsIdRequest): BaseResponse<Long>

    suspend fun createSwapChatRoom(tradesId: TradesIdRequest): BaseResponse<Long>

    suspend fun chatRoomList(): List<ChatRoom>

    suspend fun chatList(chatroomId: Long): ChatList

    suspend fun chatRoomProduct(chatroomId: Long): ChatRoomProduct

    companion object {
        private var instance: ChatRepository? = null

        fun instance(): ChatRepository {
            if (instance == null) {
                instance =
                    DefaultChatRepository(
                        remoteSource = RemoteChatDataSource(ServiceModule.chatService),
                    )
            }
            return instance!!
        }
    }
}