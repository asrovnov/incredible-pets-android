package ru.app.incredible.pets.ui.main

import me.dmdev.rxpm.action
import ru.app.incredible.pets.data.gateway.PetGateway
import ru.app.incredible.pets.ui.common.BasePm

class MainBottomBarPm(
    private val petGateway: PetGateway
) : BasePm(petGateway) {

    val selectedPet = action<Int>()

    override fun onCreate() {
        super.onCreate()

        selectedPet.observable
            .distinctUntilChanged()
            .subscribe { petGateway.setSelectedPet(it) }
            .untilDestroy()
    }
}