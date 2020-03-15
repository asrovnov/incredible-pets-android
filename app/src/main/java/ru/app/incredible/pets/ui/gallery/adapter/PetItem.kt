package ru.app.incredible.pets.ui.gallery.adapter

import ru.app.incredible.pets.domain.Identifiable

data class PetItem(
    val image: String
) : Identifiable {
    override val id: Long get() = image.length.toLong()
}