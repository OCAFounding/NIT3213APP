# =============================================================================
# ProGuard Rules for NIT3213App
# =============================================================================
# 
# ProGuard is a code shrinking, obfuscation, and optimization tool used in
# Android release builds. It helps reduce APK size and protect your code.
#
# This file contains project-specific ProGuard rules that control:
# - Which classes/methods to keep (prevent from being obfuscated)
# - Which classes/methods to remove (dead code elimination)
# - How to handle reflection and dynamic code
#
# For more details, see:
# http://developer.android.com/guide/developing/tools/proguard.html
# =============================================================================

# =============================================================================
# WEBVIEW AND JAVASCRIPT INTERFACE RULES
# =============================================================================
# If your project uses WebView with JavaScript, uncomment the following
# and specify the fully qualified class name to the JavaScript interface class:
#
# -keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
# }
#
# Example for a JavaScript interface class:
# -keepclassmembers class com.example.nit3213app.WebViewInterface {
#    public *;
# }

# =============================================================================
# DEBUGGING AND STACK TRACE RULES
# =============================================================================
# Uncomment this to preserve the line number information for debugging stack traces.
# This is useful for crash reporting and debugging, but increases APK size slightly.
# -keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to hide the original source file name.
# This provides additional obfuscation by hiding your source file names in stack traces.
# -renamesourcefileattribute SourceFile

# =============================================================================
# ANDROID-SPECIFIC RULES
# =============================================================================
# Keep Android-specific classes and methods that might be accessed via reflection
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# =============================================================================
# REFLECTION AND ANNOTATION RULES
# =============================================================================
# Keep classes and methods that might be accessed via reflection
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# =============================================================================
# THIRD-PARTY LIBRARY RULES
# =============================================================================
# Add specific rules for third-party libraries here as needed
# 
# Example for Retrofit:
# -keepattributes Signature, InnerClasses, EnclosingMethod
# -keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
# -keepclassmembers,allowshrinking,allowobfuscation interface * {
#     @retrofit2.http.* <methods>;
# }
# -keep,allowobfuscation,allowshrinking class retrofit2.Response
# -keep,allowobfuscation,allowshrinking class retrofit2.Response$Body

# Example for Gson:
# -keepattributes Signature
# -keepattributes *Annotation*
# -keep class sun.misc.Unsafe { *; }
# -keep class com.google.gson.** { *; }

# =============================================================================
# CUSTOM APPLICATION RULES
# =============================================================================
# Add rules specific to your NIT3213App here
# 
# Example: Keep all classes in your main package
# -keep class com.example.nit3213app.** { *; }
#
# Example: Keep specific classes that use reflection
# -keep class com.example.nit3213app.MainActivity { *; }
#
# Example: Keep classes with specific annotations
# -keep @com.example.nit3213app.KeepClass class * { *; }

# =============================================================================
# PERFORMANCE OPTIMIZATION RULES
# =============================================================================
# Remove logging statements in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# =============================================================================
# ADDITIONAL RESOURCES
# =============================================================================
# For more ProGuard rules and examples, see:
# - https://github.com/krschultz/android-proguard-snippets
# - https://www.guardsquare.com/en/products/proguard/manual/usage
# - https://developer.android.com/studio/build/shrink-code
