package com.phn.task.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Category (

  @SerializedName("id"         ) var id         : Int?    = null,
  @SerializedName("name"       ) var name       : String? = null,
  @SerializedName("image"      ) var image      : String? = null,
  @SerializedName("creationAt" ) var creationAt : String? = null,
  @SerializedName("updatedAt"  ) var updatedAt  : String? = null

):Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readValue(Int::class.java.classLoader) as? Int,
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString()
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeString(name)
    parcel.writeString(image)
    parcel.writeString(creationAt)
    parcel.writeString(updatedAt)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Category> {
    override fun createFromParcel(parcel: Parcel): Category {
      return Category(parcel)
    }

    override fun newArray(size: Int): Array<Category?> {
      return arrayOfNulls(size)
    }
  }
}