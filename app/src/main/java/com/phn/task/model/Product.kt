package com.phn.task.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Product (

  @SerializedName("id"          ) var id          : Int?              = null,
  @SerializedName("title"       ) var title       : String?           = null,
  @SerializedName("price"       ) var price       : Int?              = null,
  @SerializedName("description" ) var description : String?           = null,
  @SerializedName("images"      ) var images      : ArrayList<String> = arrayListOf(),
  @SerializedName("creationAt"  ) var creationAt  : String?           = null,
  @SerializedName("updatedAt"   ) var updatedAt   : String?           = null,
  @SerializedName("category"    ) var category    : Category?         = Category()

) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readValue(Int::class.java.classLoader) as? Int,
    parcel.readString(),
    parcel.readValue(Int::class.java.classLoader) as? Int,
    parcel.readString(),
    parcel.createStringArrayList() ?: arrayListOf(),
    parcel.readString(),
    parcel.readString(),
    parcel.readParcelable(Category::class.java.classLoader)
  )
  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeString(title)
    parcel.writeValue(price)
    parcel.writeString(description)
    parcel.writeStringList(images)
    parcel.writeString(creationAt)
    parcel.writeString(updatedAt)
    parcel.writeParcelable(category, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Product> {
    override fun createFromParcel(parcel: Parcel): Product {
      return Product(parcel)
    }

    override fun newArray(size: Int): Array<Product?> {
      return arrayOfNulls(size)
    }
  }

}