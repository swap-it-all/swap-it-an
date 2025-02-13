package com.example.swapit

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class SwapItApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
