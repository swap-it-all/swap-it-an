package com.swapit.oopswap

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.swapit.oopswap.data.auth.TokenStateManager
import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import com.swapit.oopswap.data.datasource.remote.RetrofitModule
import com.swapit.oopswap.data.datasource.remote.ServiceModule

class SwapItApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // ─── ① 먼저 전역 Context 세팅
        appContext = this

        /*// ─── ② 로컬 저장소에서 토큰 읽어서 TokenStateManager에 올려놓기
        val localLoginDataSource = LocalLoginDataSource(appContext)
        TokenStateManager.initializeFromLocal(localLoginDataSource)

        // ─── ③ Retrofit + AuthInterceptor / Authenticator 세팅
        //       여기서 onLogout 은 TokenStateManager.Idle 처리만 해도 충분
        val retrofit = RetrofitModule.retrofit(onLogout = {
            TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Idle
        })
        ServiceModule.init(retrofit)*/

        val logoutCallback = {
            // ① 토큰 상태를 Idle 로 변경 → isLoggedIn(false) 로 전파
            TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Idle
        }

        val retrofit = RetrofitModule.retrofit(onLogout = logoutCallback)
        ServiceModule.init(retrofit)

        // 초기 토큰 상태 로딩
        TokenStateManager.initializeFromLocal(LocalLoginDataSource(appContext))

        // ─── ④ 기타 SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
