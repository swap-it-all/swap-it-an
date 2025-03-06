package com.example.swapit.data.datasource.remote

import com.example.swapit.data.datasource.remote.service.LoginService
import com.example.swapit.data.datasource.remote.service.ProductService
import com.example.swapit.data.datasource.remote.service.ShoppingDetailService
import com.example.swapit.data.datasource.remote.service.ShoppingService
import com.example.swapit.data.datasource.remote.service.SwapService
import com.example.swapit.data.datasource.remote.service.UserService
import retrofit2.create

object ServiceModule {
    val loginService: LoginService by lazy {
        RetrofitModule.retrofit().create()
    }

    val productService: ProductService by lazy {
        RetrofitModule.retrofit().create()
    }

    val shoppingService: ShoppingService by lazy {
        RetrofitModule.retrofit().create()
    }

    val userService: UserService by lazy {
        RetrofitModule.retrofit().create()
    }

    val shoppingDetailService: ShoppingDetailService by lazy {
        RetrofitModule.retrofit().create()
    }

    val swapRequestService: SwapService by lazy {
        RetrofitModule.retrofit().create()
    }
}
