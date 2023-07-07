package net.imoya.android.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.os.BundleCompat
import java.io.Serializable

/**
 * [Bundle] utilities
 *
 * @author IceImo-P
 */
object BundleUtil {
    /**
     * Bundle.getParcelable for any Android versions
     */
    @Deprecated(
        "Use BundleCompat.getParcelable",
        replaceWith = ReplaceWith("BundleCompat.getParcelable(bundle, key, clazz)")
    )
    @JvmStatic
    fun <T : Parcelable> getParcelable(bundle: Bundle, key: String, clazz: Class<T>): T? {
        return BundleCompat.getParcelable(bundle, key, clazz)
    }

    /**
     * [Bundle.getParcelableArray] for any Android versions
     */
    @JvmStatic
    inline fun <reified T : Parcelable> getParcelableArray(
        bundle: Bundle,
        key: String,
        clazz: Class<T>
    ): Array<out T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableArrayT(bundle, key, clazz)
        } else {
            getParcelableArrayLegacy(bundle, key)
        }
    }

    /**
     * [Bundle.getSerializable] for any Android versions
     */
    @JvmStatic
    inline fun <reified T : Serializable> getSerializable(
        bundle: Bundle,
        key: String,
        clazz: Class<T>
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializableT(bundle, key, clazz)
        } else {
            getSerializableLegacy(bundle, key) as T
        }
    }

    /**
     * [Bundle.getParcelableArray] for older Android versions
     */
    @Suppress("deprecation")
    inline fun <reified T : Parcelable> getParcelableArrayLegacy(
        bundle: Bundle,
        key: String
    ): Array<out T>? {
        val extra: Array<Parcelable> = bundle.getParcelableArray(key) ?: return null
        val result = arrayListOf<T>()
        extra.forEach { result.add(it as T) }
        return result.toTypedArray()
    }

    /**
     * [Bundle.getSerializable] for older Android versions
     */
    @Suppress("deprecation")
    @JvmStatic
    fun getSerializableLegacy(bundle: Bundle, key: String): Serializable? {
        return bundle.getSerializable(key)
    }

    /**
     * [Bundle.getParcelableArray] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Parcelable> getParcelableArrayT(
        bundle: Bundle,
        key: String,
        clazz: Class<T>
    ): Array<out T>? {
        return bundle.getParcelableArray(key, clazz)
    }

    /**
     * [Bundle.getSerializable] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Serializable> getSerializableT(bundle: Bundle, key: String, clazz: Class<T>): T? {
        return bundle.getSerializable(key, clazz)
    }
}