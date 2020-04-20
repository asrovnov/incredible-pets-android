package ru.app.incredible.pets.ui.common

import androidx.annotation.CallSuper
import me.dmdev.rxpm.*
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationalPm
import me.dmdev.rxpm.widget.dialogControl
import ru.app.incredible.pets.BackMessage
import ru.app.incredible.pets.system.ResourceHelper

abstract class BasePm : PresentationModel(), NavigationalPm {

    open val backAction = action<Unit>()

    val loadingErrorDialog = dialogControl<String, Unit>()

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

    protected fun showErrorMessage(throwable: Throwable, resourceHelper: ResourceHelper) {
        getErrorMessage(throwable, resourceHelper)?.let { loadingErrorDialog.show(it) }
    }
}