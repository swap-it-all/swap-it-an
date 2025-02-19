package com.example.swapit

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk

class SwapItApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
