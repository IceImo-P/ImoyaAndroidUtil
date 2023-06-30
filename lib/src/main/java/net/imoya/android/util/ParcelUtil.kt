package net.imoya.android.util

import android.os.Build
import android.os.Parcel
import androidx.annotation.RequiresApi

/**
 * [Parcel] utilities
 *
 * @author IceImo-P
 */
@Suppress("unused")
object ParcelUtil {
    /**
     * [Parcel.readParcelable] for any Android versions
     */
    @JvmStatic
    fun <T> readParcelable(parcel: Parcel, loader: ClassLoader, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            readParcelableT(parcel, loader, clazz)
        } else {
            readParcelableLegacy(parcel, loader)
        }
    }

    /**
     * [Parcel.readParcelable] for older Android versions
     */
    @Suppress("deprecation")
    @JvmStatic
    fun <T> readParcelableLegacy(parcel: Parcel, loader: ClassLoader): T? {
        return parcel.readParcelable(loader)
    }

    /**
     * [Parcel.readParcelable] for TIRAMISU or newer Android versions
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @JvmStatic
    fun <T> readParcelableT(parcel: Parcel, loader: ClassLoader, clazz: Class<T>): T? {
        return parcel.readParcelable(loader, clazz)
    }}