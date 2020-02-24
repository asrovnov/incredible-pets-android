package ru.app.incredible.pets.data.gateway

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

class PetGateway {

    companion object {
        const val PET_DOG = 0
        const val PET_CAT = 1
    }

    private val selectedPet: BehaviorRelay<Int> = BehaviorRelay.createDefault(PET_DOG)

    fun getSelectedPet(): Observable<Int> = selectedPet.hide()

    fun setSelectedPet(petType: Int) {
        if (selectedPet.hasValue().not() || selectedPet.value != petType) {
            selectedPet.accept(petType)
        }
    }
}