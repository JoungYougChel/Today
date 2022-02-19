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

# 파트너 앱에 들어있었음
#-keep class androidx.appcompat.widget.** { *; }

# If you keep the line number information, uncomment this to
# hide the original source file name.
# 소스 파일 변수 명 바꾸는 옵션
-renamesourcefileattribute SourceFile

# Uncomment this to preserve the line number information for
# debugging stack traces.
# 소스 파일의 라인을 섞지 않는 옵션 (이거 안해주면 나중에 stacktrace보고 어느 line에서 오류가 난 것인지 확인 불가)
-keepattributes SourceFile,LineNumberTable

# 모델은 제외해야한다 (제외하지 않을 경우gson이나 retrofit에서 오류남)
# 빨간줄 떠도 정상
-keep class io.ohmyapp.model.** {*;}
-keep class io.ohmyapp.server.** {*;}


# Retrofit2
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
# Retrofit2

#카카오 api 관련
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep class * extends com.google.gson.TypeAdapter

# sqlite
-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }
-keep class net.sqlcipher.** { *; }
-dontwarn net.sqlcipher.**
# sqlite

# roomdb (androidx)
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
# roomdb

#glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public class * extends com.bumptech.glide.module.AppGlideModule
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


#glide



## okhttp3
#-dontwarn okhttp3.**
#-dontwarn okio.**
#-dontnote okhttp3.**
## okhttp3


## support v4 ,v7
#-dontwarn android.support.**
## support v4 ,v7


## gson (gson 컨버터와 다른 것)
###---------------Begin: proguard configuration for Gson  ----------
## Gson uses generic type information stored in a class file when working with fields. Proguard
## removes such information by default, so configure it to keep all of it.
#-keepattributes Signature
#
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#
## Gson specific classes
#-dontwarn sun.misc.**
##-keep class com.google.gson.stream.** { *; }
#
## Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }
#
## Prevent proguard from stripping interface information from TypeAdapterFactory,
## JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
#
###---------------End: proguard configuration for Gson  ----------
## gson (gson 컨버터와 다른 것)



