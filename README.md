# ImoyaAndroidUtil library

[VoiceClock](https://imoya.net/android/voiceclock) より切り出した、 Android 向けのユーティリティコード集です。

## Installation

### For GitHub users using Android Studio (using GitHub packages) (recommended)

* This solution is highly recommended as it allows you to view documents and use code completion.
* You can see [ImoyaAndroidDialogLib](https://github.com/IceImo-P/ImoyaAndroidDialogLib) as a typical implementation.

1. Prepare a GitHub personal access token with `read:packages` permission.
    * If you do not have such a token, please create one by referring to the following page: [Creating a personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
2. Create file named `github.properties` in Your project root directory.
3. Set the following content in `github.properties`:

    ```text
    gpr.user=[Your GitHub user ID]
    gpr.token=[Your personal access token]
    ```

4. Add GitHub Packages repository to:
    * `settings.gradle` in Your project root directory:

        ```groovy
        dependencyResolutionManagement {
            // other settings

            repositories {
                // other dependency such as google(), mavenCentral(), etc.

                def githubProperties = new Properties()
                githubProperties.load(new FileInputStream(file("github.properties")))
                maven {
                    name = "GitHubPackages-ImoyaAndroidUtil"
                    url = uri("https://maven.pkg.github.com/IceImo-P/ImoyaAndroidUtil")
                    credentials {
                        username = githubProperties.getProperty("gpr.user") ?: System.getenv("GPR_USER")
                        password = githubProperties.getProperty("gpr.token") ?: System.getenv("GPR_TOKEN")
                    }
                }
            }
        }
        ```

    * or `build.gradle` in Your project root directory:

        ```groovy
        allprojects {
            repositories {
                // other dependency such as google(), mavenCentral(), etc.

                def githubProperties = new Properties()
                githubProperties.load(new FileInputStream(rootProject.file("github.properties")))
                maven {
                    name = "GitHubPackages-ImoyaAndroidUtil"
                    url = uri("https://maven.pkg.github.com/IceImo-P/ImoyaAndroidUtil")
                    credentials {
                        username = githubProperties.getProperty("gpr.user") ?: System.getenv("GPR_USER")
                        password = githubProperties.getProperty("gpr.token") ?: System.getenv("GPR_TOKEN")
                    }
                }
            }

            // other settings
        }
        ```

5. Add dependencies to your module's `build.gradle`:

    ```groovy
    dependencies {
        // (other dependencies)
        implementation 'net.imoya.android.log:imoya-android-log:1.1.1'
        implementation 'net.imoya.android.util:imoya-android-util:1.6.2'
        // (other dependencies)
    }
    ```

6. Sync project with Gradle.

### For non-GitHub users, Android application with Android Studio (using aar)

1. Install [ImoyaAndroidLog](https://github.com/IceImo-P/ImoyaAndroidLog) with reading [this section](https://github.com/IceImo-P/ImoyaAndroidLog#for-non-github-users-android-application-with-android-studio-using-aar).
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

### For non-GitHub users, Android library with Android Studio (using aar)

1. Install [ImoyaAndroidLog](https://github.com/IceImo-P/ImoyaAndroidLog) with reading [this section](https://github.com/IceImo-P/ImoyaAndroidLog#for-non-github-users-android-library-with-android-studio-using-aar).
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
