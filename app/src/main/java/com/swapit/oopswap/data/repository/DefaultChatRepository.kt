package com.swapit.oopswap.data.repository

import com.swapit.oopswap.data.datasource.RemoteChatDataSource
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.mapper.toDomain
import com.swapit.oopswap.domain.model.chat.ChatList
import com.swapit.oopswap.domain.model.chat.ChatRoom
import com.swapit.oopswap.domain.model.chat.ChatRoomInfo
import com.swapit.oopswap.domain.repository.ChatRepository

class DefaultChatRepository(
    private val remoteSource: RemoteChatDataSource,
    private val onLogout: () -> Unit
) : ChatRepository {

    override suspend fun createChatRoom(goodsId: GoodsIdRequest): Result<BaseResponse<Long>> =
        safeApiCall(onLogout) {
            remoteSource.createChatRoom(goodsId)
        }

    override suspend fun createSwapChatRoom(tradesId: TradesIdRequest): Result<BaseResponse<Long>> =
        safeApiCall(onLogout) {
            remoteSource.createSwapChatRoom(tradesId)
        }

    override suspend fun chatRoomList(): Result<List<ChatRoom>> =
        safeApiCall(onLogout) {
            remoteSource.chatRoomList().results.chatRoomList.map { it.toDomain() }
        }

    override suspend fun chatList(chatroomId: Long): Result<ChatList> =
        safeApiCall(onLogout) {
            remoteSource.chatList(chatroomId).results.toDomain()
        }

    override suspend fun chatRoomInfo(chatroomId: Long): Result<ChatRoomInfo> =
        safeApiCall(onLogout) {
            remoteSource.chatRoomInfo(chatroomId).results.toDomain()
        }
}
