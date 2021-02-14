package ru.app.incredible.pets

import me.dmdev.rxpm.navigation.NavigationMessage
import ru.app.incredible.pets.ui.gallery.adapter.PetItem
import java.util.ArrayList

class BackMessage : NavigationMessage

class PetsOpenScreen : NavigationMessage
class GalleryOpenScreen : NavigationMessage
class OpenFullImageScreen(val imageId: String, val listImage: ArrayList<PetItem>): NavigationMessage