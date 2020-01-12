package ru.app.incredible.pets.ui.dogs


import ru.app.incredible.pets.R
import org.koin.android.ext.android.get
import ru.app.incredible.pets.ui.common.BaseScreen

class DogsScreen : BaseScreen<DogsPm>() {

    override val screenLayout = R.layout.dogs_screen

    override fun providePresentationModel(): DogsPm = get()
}