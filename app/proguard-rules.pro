# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.swapit.** { *; }
-keepclassmembers class ** {
    public static final java.lang.String KAKAO_NATIVE_APP_KEY;
    public static final java.lang.String SWAP_IT_BASE_URL;
    public static final java.lang.String kakao_oauth_host;
    public static final java.lang.String google_client_id;
}
-dontwarn okhttp3.**

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ==============================
# R8 Missing class 해결용 규칙
# ==============================

# javax.lang.model.* (Annotation Processor 관련 - 런타임 불필요)
-dontwarn javax.lang.model.**
-dontwarn javax.annotation.processing.**
-dontwarn javax.lang.model.util.**

# java.sql (예: java.sql.JDBCType - Android SDK에서는 누락되어 있음)
-dontwarn java.sql.**

# SQLite JDBC 관련 (만약 사용하지 않는다면 dontwarn, 실제 사용 시 keep 추가)
-dontwarn org.sqlite.**
-keep class org.sqlite.** { *; }

# com.squareup.javapoet (빌드 타임 코드 생성용, 런타임 불필요)
-dontwarn com.squareup.javapoet.**
-keep class com.squareup.javapoet.** { *; }

# com.google.auto.common (AutoValue/Service용 어노테이션 유틸)
-dontwarn com.google.auto.common.**
-keep class com.google.auto.common.** { *; }

# Guava 내부에서 참조하는 경우 (가끔 함께 사용)
-dontwarn com.google.common.collect.**

# javax.lang.model.element.* 클래스들 keep (일부 경우에 필요)
-keep class javax.lang.model.** { *; }
-keep interface javax.lang.model.** { *; }
-keep class javax.annotation.** { *; }

# 기타 (참고용)
# -ignorewarnings   // 필요 시 임시적으로 사용 (주의 필요)
