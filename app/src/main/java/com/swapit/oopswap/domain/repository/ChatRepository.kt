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
    suspend fun createChatRoom(goodsId: GoodsIdRequest): BaseResponse<Long>

    suspend fun createSwapChatRoom(tradesId: TradesIdRequest): BaseResponse<Long>

    suspend fun chatRoomList(): List<ChatRoom>

    suspend fun chatList(chatroomId: Long): ChatList

    suspend fun chatRoomInfo(chatroomId: Long): ChatRoomInfo

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
