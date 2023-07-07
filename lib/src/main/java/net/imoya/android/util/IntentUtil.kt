package net.imoya.android.util

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.content.IntentCompat
import java.io.Serializable

/**
 * [Intent] utilities
 *
 * @author IceImo-P
 */
object IntentUtil {
    /**
     * [Intent.getParcelableExtra] for any Android versions
     */
    @Deprecated(
        "Use IntentCompat.getParcelableExtra",
        replaceWith = ReplaceWith("IntentCompat.getParcelableExtra(intent, key, clazz)")
    )
    @JvmStatic
    fun <T : Parcelable> getParcelableExtra(intent: Intent, key: String, clazz: Class<T>): T? {
        return IntentCompat.getParcelableExtra(intent, key, clazz)
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
    @Deprecated(
        "Use IntentCompat.getParcelableArrayListExtra",
        replaceWith = ReplaceWith("IntentCompat.getParcelableArrayListExtra(intent, key, clazz)")
    )
    fun <T : Parcelable> getParcelableArrayListExtra(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): ArrayList<out T>? {
        return IntentCompat.getParcelableArrayListExtra(intent, key, clazz)
    }

    /**
     * [Intent.getSerializableExtra] for any Android versions
     */
    @JvmStatic
    inline fun <reified T : Serializable> getSerializableExtra(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializableExtraT(intent, key, clazz)
        } else {
            getSerializableExtraLegacy(intent, key) as T
        }
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
     * [Intent.getSerializableExtra] for older Android versions
     */
    @Suppress("deprecation")
    @JvmStatic
    fun getSerializableExtraLegacy(intent: Intent, key: String): Serializable? {
        return intent.getSerializableExtra(key)
    }

    /**
     * [Intent.getParcelableArrayExtra] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Parcelable> getParcelableArrayExtraT(
        intent: Intent,
        key: String,
        clazz: Class<T>
    ): Array<out T>? {
        return intent.getParcelableArrayExtra(key, clazz)
    }

    /**
     * [Intent.getSerializableExtra] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Serializable> getSerializableExtraT(intent: Intent, key: String, clazz: Class<T>): T? {
        return intent.getSerializableExtra(key, clazz)
    }
}