package net.imoya.android.util

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.os.ParcelCompat

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
    @Deprecated(
        "Use ParcelCompat.readParcelable",
        replaceWith = ReplaceWith("ParcelCompat.readParcelable(parcel, loader, clazz)")
    )
    @JvmStatic
    fun <T : Parcelable> readParcelable(parcel: Parcel, loader: ClassLoader, clazz: Class<T>): T? {
        return ParcelCompat.readParcelable(parcel, loader, clazz)
    }

    /**
     * [Parcel.readParcelableArray] for any Android versions
     */
    @JvmStatic
    inline fun <reified T : Parcelable> readParcelableArray(
        parcel: Parcel,
        loader: ClassLoader,
        clazz: Class<T>
    ): Array<out T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            readParcelableArrayT(parcel, loader, clazz)
        } else {
            readParcelableArrayLegacy(parcel, loader)
        }
    }

    /**
     * [Parcel.readParcelableArray] for older Android versions
     */
    @Suppress("deprecation")
    inline fun <reified T : Parcelable> readParcelableArrayLegacy(
        parcel: Parcel,
        loader: ClassLoader
    ): Array<out T>? {
        val extra: Array<Parcelable> = parcel.readParcelableArray(loader) ?: return null
        val result = arrayListOf<T>()
        extra.forEach { result.add(it as T) }
        return result.toTypedArray()
    }

    /**
     * [Parcel.readParcelableArray] for TIRAMISU or newer Android versions
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun <T : Parcelable> readParcelableArrayT(
        parcel: Parcel,
        loader: ClassLoader,
        clazz: Class<T>
    ): Array<out T>? {
        return parcel.readParcelableArray(loader, clazz)
    }
}