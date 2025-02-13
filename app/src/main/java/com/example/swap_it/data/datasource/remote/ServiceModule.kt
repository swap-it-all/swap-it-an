package com.example.swap_it.data.datasource.remote

import com.example.swap_it.data.datasource.remote.service.LoginService
import retrofit2.create

object ServiceModule {
    val loginService: LoginService by lazy {
        RetrofitModule.retrofit().create()
    }
}
