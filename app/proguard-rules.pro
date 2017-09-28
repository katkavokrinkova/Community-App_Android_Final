# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Lightful\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


 -dontwarn com.squareup.okhttp.internal.**
 -dontwarn okio.**
 -dontwarn retrofit2.**
 -keep class okhttp3.** { *; }
 -keep interface okhttp3.** { *; }
 -dontwarn okhttp3.**

# ------------------------------------------
# RETROFIT config
# ------------------------------------------

-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclassmembernames interface * {
    @retrofit.http.* <methods>;
}
-keepattributes Signature
-keepattributes Exceptions

# ------------------------------------------
# OKIO config
# ------------------------------------------

-dontwarn okio.**
-keep class okio.** { *; }

# ------------------------------------------
# GCM config
# ------------------------------------------
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

##== Gson ==
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }