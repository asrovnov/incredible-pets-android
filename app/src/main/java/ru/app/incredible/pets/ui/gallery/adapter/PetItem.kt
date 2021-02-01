package ru.app.incredible.pets.ui.gallery.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.app.incredible.pets.domain.Identifiable

@Parcelize
data class PetItem(
    val image: String
) : Identifiable, Parcelable {
    override val id: Long get() = image.length.toLong()
}