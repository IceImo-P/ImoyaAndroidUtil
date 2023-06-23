package net.imoya.android.util

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

/**
 * [Bundle] utilities
 *
 * @author IceImo-P
 */
object BundleUtil {
    /**
     * Bundle.getParcelable for any Android versions
     */
    @JvmStatic
    fun <T> getParcelable(bundle: Bundle, key: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableT(bundle, key, clazz)
        } else {
            getParcelableLegacy(bundle, key)
        }
    }

    /**
     * Bundle.getParcelable for older Android versions
     */
    @Suppress("DEPRECATION")
    @JvmStatic
    fun <T> getParcelableLegacy(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key)
    }

    /**
     * Bundle.getParcelable for TIRAMISU or newer Android versions
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @JvmStatic
    fun <T> getParcelableT(bundle: Bundle, key: String, clazz: Class<T>): T? {
        return bundle.getParcelable(key, clazz)
    }
}