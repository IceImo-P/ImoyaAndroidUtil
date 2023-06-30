package net.imoya.android.util

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Parcelable

/**
 * [Intent] utilities
 *
 * @author IceImo-P
 */
@Suppress("unused")
object IntentUtil {
    /**
     * [Intent.getParcelableExtra] for any Android versions
     */
    @JvmStatic
    fun <T : Parcelable> getParcelableExtra(intent: Intent, key: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableExtraT(intent, key, clazz)
        } else {
            getParcelableExtraLegacy(intent, key)
        }
    }

    /**
     * [Intent.getParcelableArrayExtra] for any Android versions
     */
    @JvmStatic
    inline fun <reified T : Parcelable> getParcelableArrayExtra(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): Array<out T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableArrayExtraT(intent, key, clazz)
        } else {
            getParcelableArrayExtraLegacy(intent, key)
        }
    }

    /**
     * [Intent.getParcelableArrayListExtra] for any Android versions
     */
    fun <T : Parcelable> getParcelableArrayListExtra(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): ArrayList<out T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableArrayListExtraT(intent, key, clazz)
        } else {
            getParcelableArrayListExtraLegacy(intent, key)
        }
    }

    /**
     * [Intent.getParcelableExtra] for older Android versions
     */
    @Suppress("deprecation")
    @JvmStatic
    fun <T : Parcelable> getParcelableExtraLegacy(intent: Intent, key: String): T? {
        return intent.getParcelableExtra(key)
    }

    /**
     * [Intent.getParcelableArrayExtra] for older Android versions
     */
    @Suppress("deprecation")
    inline fun <reified T : Parcelable> getParcelableArrayExtraLegacy(
        intent: Intent,
        key: String
    ): Array<out T>? {
        val extra: Array<Parcelable> = intent.getParcelableArrayExtra(key) ?: return null
        val result = arrayListOf<T>()
        extra.forEach { result.add(it as T) }
        return result.toTypedArray()
    }

    /**
     * [Intent.getParcelableArrayListExtra] for older Android versions
     */
    @Suppress("deprecation")
    @JvmStatic
    fun <T : Parcelable> getParcelableArrayListExtraLegacy(
        intent: Intent,
        key: String
    ): ArrayList<out T>? {
        return intent.getParcelableArrayListExtra(key)
    }

    /**
     * [Intent.getParcelableExtra] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    fun <T> getParcelableExtraT(intent: Intent, key: String, clazz: Class<T>): T? {
        return intent.getParcelableExtra(key, clazz)
    }

    /**
     * [Intent.getParcelableArrayExtra] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Parcelable> getParcelableArrayExtraT(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): Array<out T>? {
        return intent.getParcelableArrayExtra(key, clazz)
    }

    /**
     * [Intent.getParcelableArrayListExtra] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Parcelable> getParcelableArrayListExtraT(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): ArrayList<out T>? {
        return intent.getParcelableArrayListExtra(key, clazz)
    }
}