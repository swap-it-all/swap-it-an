package com.swapit.oopswap.domain.repository

import com.swapit.oopswap.data.datasource.RemoteChatDataSource
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.repository.DefaultChatRepository
import com.swapit.oopswap.domain.model.chat.ChatList
import com.swapit.oopswap.domain.model.chat.ChatRoom
import com.swapit.oopswap.domain.model.chat.ChatRoomInfo

interface ChatRepository {
    suspend fun createChatRoom(goodsId: GoodsIdRequest): Result<BaseResponse<Long>>

    suspend fun createSwapChatRoom(tradesId: TradesIdRequest): Result<BaseResponse<Long>>

    suspend fun chatRoomList(): Result<List<ChatRoom>>

    suspend fun chatList(chatroomId: Long): Result<ChatList>

    suspend fun chatRoomInfo(chatroomId: Long): Result<ChatRoomInfo>

    companion object {
        private var instance: ChatRepository? = null

        fun instance(onLogout: () -> Unit = {}): ChatRepository {
            if (instance == null) {
                instance =
                    DefaultChatRepository(
                        remoteSource = RemoteChatDataSource(ServiceModule.chatService),
                        onLogout = onLogout
                    )
            }
            return instance!!
        }
    }
}
