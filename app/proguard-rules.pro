# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/anupcowkur/Development/Android/sdk/tools/proguard/proguard-android.txt
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
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


# Ignore warnings: https://github.com/square/okhttp/wiki/FAQs
-dontwarn com.squareup.okhttp.internal.huc.**
# Ignore warnings: https://github.com/square/okio/issues/60
-dontwarn okio.**
# Ignore warnings: https://github.com/square/retrofit/issues/435
-dontwarn com.google.appengine.api.urlfetch.**

#Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

#Okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

#Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }

-keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }

#Dagger
#-keep class * extends dagger.internal.Binding
#-keep class * extends dagger.internal.ModuleAdapter
#-keep class * extends dagger.internal.*﻿
#-keep class * extends dagger.internal.BindingsGroup
#-dontwarn dagger.internal.**

-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.anupcowkur.mvpsample.model.** { *; }

