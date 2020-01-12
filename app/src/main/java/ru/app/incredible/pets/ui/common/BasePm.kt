package ru.app.incredible.pets.ui.common

import androidx.annotation.CallSuper
import me.dmdev.rxpm.*
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationalPm
import ru.app.incredible.pets.BackMessage

abstract class BasePm : PresentationModel(), NavigationalPm {

    open val backAction = action<Unit>()

    override val navigationMessages = command<NavigationMessage>()

    protected fun sendMessage(message: NavigationMessage) {
        navigationMessages.consumer.accept(message)
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()

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
}