package ru.app.incredible.pets.ui.common

import ru.app.incredible.pets.system.ResourceHelper
import ru.app.incredible.pets.R
import ru.app.incredible.pets.domain.exceptions.BackendException
import ru.app.incredible.pets.domain.exceptions.InternetUnavailableException

fun getErrorMessage(throwable: Throwable, resourceHelper: ResourceHelper): String? {
    return when (throwable) {
        is InternetUnavailableException -> resourceHelper.getString(R.string.dialog_error_no_internet_connection_msg)
        is BackendException -> throwable.userMessage
        else -> "Error: ${resourceHelper.getString(R.string.dialog_error_unknown_error_msg)}"
    }
}