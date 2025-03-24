package com.swapit.company.data.datasource.remote

import com.swapit.company.data.datasource.remote.service.ChatService
import com.swapit.company.data.datasource.remote.service.LoginService
import com.swapit.company.data.datasource.remote.service.ProductService
import com.swapit.company.data.datasource.remote.service.ReportService
import com.swapit.company.data.datasource.remote.service.SwapService
import com.swapit.company.data.datasource.remote.service.UserService
import retrofit2.create

object ServiceModule {
    val loginService: LoginService by lazy {
        RetrofitModule.retrofit().create()
    }

    val productService: ProductService by lazy {
        RetrofitModule.retrofit().create()
    }

    val userService: UserService by lazy {
        RetrofitModule.retrofit().create()
    }

    val swapRequestService: SwapService by lazy {
        RetrofitModule.retrofit().create()
    }

    val chatService: ChatService by lazy {
        RetrofitModule.retrofit().create()
    }

    val reportService: ReportService by lazy {
        RetrofitModule.retrofit().create()
    }
}
