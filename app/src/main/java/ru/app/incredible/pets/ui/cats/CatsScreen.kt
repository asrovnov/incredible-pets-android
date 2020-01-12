package ru.app.incredible.pets.ui.cats

import ru.app.incredible.pets.R
import org.koin.android.ext.android.get
import ru.app.incredible.pets.ui.common.BaseScreen

class CatsScreen : BaseScreen<CatsPm>() {

    override val screenLayout = R.layout.cats_screen

    override fun providePresentationModel(): CatsPm = get()
}