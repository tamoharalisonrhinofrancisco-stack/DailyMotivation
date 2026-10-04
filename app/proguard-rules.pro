# Keep Android Keystore classes
-keep class javax.crypto.** { *; }
-keep class java.security.** { *; }
-keep class android.security.keystore.** { *; }

# Keep our security classes
-keep class com.rhino.dailymotivation.security.** { *; }
-keep class com.rhino.dailymotivation.data.** { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Remove logging noise
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
