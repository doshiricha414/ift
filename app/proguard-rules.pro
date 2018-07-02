# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Amit\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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

########################################################################
#
# proguard configuration for v7 support library
#
-dontwarn android.support.v7.**
#-keep class android.support.v7.** { *; }
#-keep interface android.support.v7.** { *; }
########################################################################
#
# proguard configuration for v4 support library
#
-dontwarn android.support.v4.**
#-keep class android.support.v4.** { *; }
#-keep interface android.support.v4.** { *; }
#########################################################################
# proguard configuration for Picasso
#
-dontwarn com.squareup.picasso.**
########################################################################
#
# proguard configuration for Jackson library
#
-dontwarn org.codehaus.jackson.**
########################################################################
#
# other proguard configuration
#
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclassmembers
#-dontoptimize
-allowaccessmodification
-dontpreverify
#-overloadaggressively
-mergeinterfacesaggressively
-repackageclasses ''
-optimizations class/marking/final, class/unboxing/enum, class/merging/vertical, class/merging/horizontal
-optimizations method/marking/static, method/marking/private, method/marking/final, method/removal/parameter, method/inlining/short, method/inlining/unique
-optimizations code/merging, code/simplification/variable, code/simplification/string, code/simplification/field, code/simplification/arithmetic, code/simplification/branch, code/simplification/advanced, code/removal/variable, code/removal/simple, code/removal/advanced, code/removal/exception, code/allocation/variable
-optimizations field/marking/private
-optimizationpasses 5

-adaptresourcefilenames    **.properties,**.gif,**.jpg,**.png
-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF

#  this configuration will help to rename only field & methods names, it wont change line numbers. Therefore you can easily identify errors
#  rename "SourceFile" with the filename which you want to debug
#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable
