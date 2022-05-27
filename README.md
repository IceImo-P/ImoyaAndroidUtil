# ImoyaAndroidUtil library

[VoiceClock](https://imoya.net/android/voiceclock) より切り出した、 Android 向けのユーティリティコード集です。

## Installation

### Android application with Android Studio

1. Install [ImoyaAndroidLog](https://github.com/IceImo-P/ImoyaAndroidLog) with [this section](https://github.com/IceImo-P/ImoyaAndroidLog#android-application-with-android-studio).
2. Download `imoya-android-util-release-[version].aar` from [Releases](https://github.com/IceImo-P/ImoyaAndroidUtil/releases) page.
3. Place `imoya-android-util-release-[version].aar` in `libs` subdirectory of your app module.
4. Add dependencies to your app module's `build.gradle`:

    ```groovy
    dependencies {
        // (other dependencies)
        implementation files('libs/imoya-android-util-release-[version].aar')
        // (other dependencies)
    }
    ```

5. Sync project with Gradle.

### Android library with Android Studio

* You can see [ImoyaAndroidMediaLib](https://github.com/IceImo-P/ImoyaAndroidMediaLib) as a typical implementation.

1. Install [ImoyaAndroidLog](https://github.com/IceImo-P/ImoyaAndroidLog) with [this section](https://github.com/IceImo-P/ImoyaAndroidLog#android-library-with-android-studio).
2. Download `imoya-android-util-release-[version].aar` from [Releases](https://github.com/IceImo-P/ImoyaAndroidUtil/releases) page.
3. Create `imoya-android-util` subdirectory in your project's root directory.
4. Place `imoya-android-util-release-[version].aar` in `imoya-android-util` directory.
5. Create `build.gradle` file in `imoya-android-util` directory and set content as below:

    ```text
    configurations.maybeCreate("default")
    artifacts.add("default", file('imoya-android-util-release-[version].aar'))
    ```

6. Add the following line to the `settings.gradle` file in your project's root directory:

    ```text
    include ':imoya-android-util'
    ```

7. Add dependencies to your library module's `build.gradle`.

    ```groovy
    dependencies {
        // (other dependencies)
        implementation project(':imoya-android-util')
        // (other dependencies)
    }
    ```

8. Sync project with Gradle.

## Logging

By default, ImoyaAndroidUtil does not output logs.

If you want to see ImoyaAndroidUtil's log, please do the following steps:

1. Make string resource `imoya_android_util_log_level` for setup minimum output log level.

    ```xml
    <resources>
        <!-- (other resources) -->

        <string name="imoya_android_util_log_level" translatable="false">info</string>

        <!-- (other resources) -->
    </resources>
    ```

   * The values and meanings are shown in the following table:
     | value | meanings |
     | --- | --- |
     | `none` | Output nothing |
     | `all` | Output all log |
     | `v` or `verbose` | Output VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT log |
     | `d` or `debug` | Output DEBUG, INFO, WARN, ERROR, ASSERT log |
     | `i` or `info` | Output INFO, WARN, ERROR, ASSERT log |
     | `w` or `warn` | Output WARN, ERROR, ASSERT log |
     | `e` or `error` | Output ERROR, ASSERT log |
     | `assert` | Output ASSERT log |
2. Call `net.imoya.android.util.UtilLog.init` method at starting your application or Activity.
   * Sample(Kotlin):

       ```kotlin
       import android.app.Application
       import net.imoya.android.util.UtilLog
       
       class MyApplication : Application() {
           override fun onCreate() {
               super.onCreate()
            
               UtilLog.init(getApplicationContext())
            
               // ...
           }

           // ...
       }
       ```

   * Sample(Java):

       ```java
       import android.app.Application;
       import net.imoya.android.util.UtilLog;
       
       public class MyApplication extends Application {
           @Override
           public void onCreate() {
               super.onCreate();
            
               UtilLog.init(this.getApplicationContext());
            
               // ...
           }

           // ...
       }
       ```

## License

Apache license 2.0
