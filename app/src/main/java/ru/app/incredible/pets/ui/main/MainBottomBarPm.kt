package ru.app.incredible.pets.ui.main

import ru.app.incredible.pets.ui.common.BasePm

class MainBottomBarPm : BasePm() {

    override fun onCreate() {
        super.onCreate()
    }

    enum class Page(val position: Int) {
        DOGS(0), CATS(1)
    }
}