package net.imoya.android.util.sample

import android.os.Parcel
import android.os.Parcelable
import androidx.core.os.ParcelCompat

class SampleDataContainer(var name: String = "") : Parcelable {

    class SubContainer(var name: String = "") : Parcelable {
        constructor(parcel: Parcel) : this(parcel.readString() ?: "")

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SubContainer> {
            override fun createFromParcel(parcel: Parcel): SubContainer {
                return SubContainer(parcel)
            }

            override fun newArray(size: Int): Array<SubContainer?> {
                return arrayOfNulls(size)
            }
        }
    }

    var subContainer: SubContainer? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString() ?: throw RuntimeException("name not found")
        subContainer = ParcelCompat.readParcelable(
            parcel,
            SubContainer::class.java.classLoader!!,
            SubContainer::class.java
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(subContainer, 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SampleDataContainer: $name / ${subContainer?.name}"
    }

    companion object CREATOR : Parcelable.Creator<SampleDataContainer> {
        override fun createFromParcel(parcel: Parcel): SampleDataContainer {
            return SampleDataContainer(parcel)
        }

        override fun newArray(size: Int): Array<SampleDataContainer?> {
            return arrayOfNulls(size)
        }
    }
}