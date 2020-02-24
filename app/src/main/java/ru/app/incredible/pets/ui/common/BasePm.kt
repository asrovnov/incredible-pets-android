package ru.app.incredible.pets.ui.common

import androidx.annotation.CallSuper
import io.reactivex.Observable
import me.dmdev.rxpm.*
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationalPm
import me.dmdev.rxpm.widget.dialogControl
import ru.app.incredible.pets.BackMessage
import ru.app.incredible.pets.data.gateway.PetGateway
import ru.app.incredible.pets.domain.Pet
import ru.app.incredible.pets.system.ResourceHelper

abstract class BasePm(
    private val petGateway: PetGateway
) : PresentationModel(), NavigationalPm {

    open val currentPet = state<Int>()

    open val backAction = action<Unit>()

    val loadingErrorDialog = dialogControl<String, Unit>()

    override val navigationMessages = command<NavigationMessage>()

    protected fun sendMessage(message: NavigationMessage) {
        navigationMessages.consumer.accept(message)
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()

        petGateway.getSelectedPet()
            .subscribe(currentPet.consumer)
            .untilDestroy()

        backAction.observable
            .map { BackMessage() }
            .subscribe(navigationMessages.consumer)
            .untilDestroy()
    }

    infix fun <T> T.passTo(state: State<T>) {
        state.consumer.accept(this)
    }

    infix fun <T> T.passTo(command: Command<T>) {
        command.consumer.accept(this)
    }

    protected fun showErrorMessage(throwable: Throwable, resourceHelper: ResourceHelper) {
        getErrorMessage(throwable, resourceHelper)?.let { loadingErrorDialog.show(it) }
    }

    protected fun getPet() : Observable<Pet> {
        return currentPet.observable
            .map { Pet(it) }
    }
}