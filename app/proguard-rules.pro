############################################
# 📦 앱 전반 보호: 난독화 유지 + 필요한 부분 보존
############################################

# 패키지 전체 난독화는 유지하지만, 내부 public/static 필드 및 클래스 유지
-keep class com.swapit.** { *; }

############################################
# 🧠 리플렉션 대비: 필드명 유지 (예: TokenNotFound)
############################################

# TokenNotFound 필드 유지
-keepclassmembers class ** {
    public static final java.lang.String TokenNotFound;
}

# 모든 public static final String 필드 보존 (보다 범용적인 방법)
-keepclassmembers class ** {
    public static final java.lang.String *;
}

############################################
# 🧪 디버깅 편의: 로그 추적용 소스 위치/줄 번호 유지
############################################

-keepattributes SourceFile,LineNumberTable

############################################
# ⚠️ 주요 라이브러리 보존 설정
############################################

# WebView 내 JS 인터페이스 클래스가 있다면 아래 활성화
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
#}

# OkHttp 관련 경고 제거 (R8 최적화 시)
-dontwarn okhttp3.**
-dontwarn okio.**

# Retrofit (인터페이스 기반 호출 사용 시)
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }

# Gson - JSON 직렬화 클래스
-keep class com.google.gson.stream.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Kotlin Metadata
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }

# ViewModel, DI (예: Hilt, Dagger 등) 사용 시
-dontwarn dagger.**
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }

# Firebase 관련 설정 (Crashlytics 등)
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }

# Kakao SDK (OAuth 등)
-dontwarn com.kakao.**
-keep class com.kakao.** { *; }

############################################
# ✅ 기타 안전 장치
############################################

# javax.lang.model.* (빌드 타임 관련 - 런타임 미사용)
-dontwarn javax.lang.model.**
-dontwarn javax.annotation.processing.**

# SQLite JDBC (사용 시)
-keep class org.sqlite.** { *; }
-dontwarn org.sqlite.**

# AutoValue / JavaPoet (빌드 타임 코드 생성)
-keep class com.squareup.javapoet.** { *; }
-dontwarn com.squareup.javapoet.**

-keep class com.google.auto.common.** { *; }
-dontwarn com.google.auto.common.**

# Guava 컬렉션
-dontwarn com.google.common.collect.**

# javax.annotation (Firebase 등 일부 라이브러리에서 사용)
-keep class javax.annotation.** { *; }

############################################
# 🧹 경고 무시 (권장하지 않지만 일시적으로 사용 가능)
############################################

#-ignorewarnings
