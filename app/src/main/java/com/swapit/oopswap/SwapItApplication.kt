package com.swapit.oopswap

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.swapit.oopswap.data.auth.TokenStateManager
import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource

class SwapItApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // 🔥 여기서 토큰 상태 초기화
        val localLoginDataSource = LocalLoginDataSource(appContext)
        TokenStateManager.initializeFromLocal(localLoginDataSource)
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
