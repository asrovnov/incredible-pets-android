package ru.app.incredible.pets.ui.cat

import kotlinx.android.synthetic.main.toolbar.view.*
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.ui.common.BaseScreen

class CatScreen : BaseScreen<CatPm>() {

    override val screenLayout = R.layout.cat_screen

    override fun providePresentationModel(): CatPm = getKoin().get()

    override fun onInitView() {
        super.onInitView()

        view?.toolbar?.setTitle(R.string.cat)
    }

    override fun onBindPresentationModel(pm: CatPm) {
        super.onBindPresentationModel(pm)
    }
}