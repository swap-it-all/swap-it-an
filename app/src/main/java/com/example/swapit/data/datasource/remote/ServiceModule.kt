package com.example.swapit.data.datasource.remote

import com.example.swapit.data.datasource.remote.service.LoginService
import retrofit2.create

object ServiceModule {
    val loginService: LoginService by lazy {
        RetrofitModule.retrofit().create()
    }
}
