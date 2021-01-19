package ru.app.incredible.pets.ui.full_image

import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.R
import ru.app.incredible.pets.ui.common.BaseScreen
import ru.app.incredible.pets.ui.pets.PetsPm

class FullImageScreen : BaseScreen<PetsPm>() {

    override val screenLayout = R.layout.screen_full_image

    override fun providePresentationModel(): PetsPm = getKoin().get()
}