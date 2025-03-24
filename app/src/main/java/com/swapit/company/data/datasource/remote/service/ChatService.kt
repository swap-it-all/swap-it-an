package com.swapit.company.data.datasource.remote.service

import com.swapit.company.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.swapit.company.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatListResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatRoomInfoResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatRoomListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {
    @POST("api/user/chatroom")
    suspend fun createChatRoom(
        @Body goodsId: GoodsIdRequest,
    ): BaseResponse<Long>

    @POST("api/user/swap-chatroom")
    suspend fun createSwapChatRoom(
        @Body tradesId: TradesIdRequest,
    ): BaseResponse<Long>

    @GET("api/user/chatroom")
    suspend fun chatRoomList(): BaseResponse<ChatRoomListResponse>

    @GET("api/user/chatroom/{chatroomId}")
    suspend fun chatList(
        @Path("chatroomId") chatroomId: Long,
    ): BaseResponse<ChatListResponse>

    @GET("api/user/chatroom/{chatroomId}/info")
    suspend fun chatRoomInfo(
        @Path("chatroomId") chatroomId: Long,
    ): BaseResponse<ChatRoomInfoResponse>
}
