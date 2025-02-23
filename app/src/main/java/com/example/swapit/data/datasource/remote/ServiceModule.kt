package com.example.swapit.data.datasource.remote

import com.example.swapit.data.datasource.remote.service.LoginService
import com.example.swapit.data.datasource.remote.service.ProductService
import retrofit2.create

object ServiceModule {
    val loginService: LoginService by lazy {
        RetrofitModule.retrofit().create()
    }

    val productService: ProductService by lazy {
        RetrofitModule.retrofit().create()
    }
}
