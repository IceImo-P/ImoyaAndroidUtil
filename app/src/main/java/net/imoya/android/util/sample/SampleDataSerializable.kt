package net.imoya.android.util.sample

import java.io.Serializable

class SampleDataSerializable(var name: String = "") : Serializable {
    override fun toString(): String {
        return "SampleDataSerializable: $name"
    }
}