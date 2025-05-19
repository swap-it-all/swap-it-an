package com.swapit.oopswap.data.datasource.remote

import com.swapit.oopswap.data.datasource.remote.service.AlertService
import com.swapit.oopswap.data.datasource.remote.service.ChatService
import com.swapit.oopswap.data.datasource.remote.service.LoginService
import com.swapit.oopswap.data.datasource.remote.service.ProductService
import com.swapit.oopswap.data.datasource.remote.service.ReportService
import com.swapit.oopswap.data.datasource.remote.service.SwapService
import com.swapit.oopswap.data.datasource.remote.service.UserService
import retrofit2.Retrofit
import retrofit2.create

object ServiceModule {

    private lateinit var retrofit: Retrofit

    // 앱 시작(MainActivity)에서 한 번만 호출
    fun init(retrofitInstance: Retrofit) {
        retrofit = retrofitInstance
    }

    val loginService: LoginService by lazy {
        retrofit.create()
    }

    val productService: ProductService by lazy {
        retrofit.create()
    }

    val userService: UserService by lazy {
        retrofit.create()
    }

    val swapRequestService: SwapService by lazy {
        retrofit.create()
    }

    val chatService: ChatService by lazy {
        retrofit.create()
    }

    val reportService: ReportService by lazy {
        retrofit.create()
    }

    val alertService: AlertService by lazy {
        retrofit.create()
    }
}
