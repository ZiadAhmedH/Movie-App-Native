// MovieModel.kt
import android.os.Parcel
import android.os.Parcelable

data class MovieModel(
    val id: Int,
    val title: String,
    val year: Int,
    val rating: Double,
    val summary: String,
    val medium_cover_image: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,


    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeInt(year)
        parcel.writeDouble(rating)
        parcel.writeString(summary)
        parcel.writeString(medium_cover_image)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel = MovieModel(parcel)
        override fun newArray(size: Int): Array<MovieModel?> = arrayOfNulls(size)
    }
}
