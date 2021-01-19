package ru.app.incredible.pets.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import me.dmdev.rxpm.base.PmFragment
import me.dmdev.rxpm.widget.bindTo
import ru.app.incredible.pets.R

abstract class BaseScreen<PM : BasePm> : PmFragment<PM>(), BackButtonHandler {

    abstract val screenLayout: Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(screenLayout, container, false).also {
            (it as? ViewGroup)?.isClickable = true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInitView()
    }

    @CallSuper
    override fun onBindPresentationModel(pm: PM) {
        pm.loadingErrorDialog bindTo { message, _ ->
            AlertDialog.Builder(view!!.context)
                .setMessage(message)
                .setPositiveButton(R.string.close, null)
                .create()
        }
    }

    override fun onResume() {
        super.onResume()
        view?.requestApplyInsets()
    }

    open fun onInitView() {}

    override fun onBackPressed(): Boolean {
        return false
    }
}