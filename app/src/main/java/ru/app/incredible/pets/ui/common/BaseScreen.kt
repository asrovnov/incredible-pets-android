package ru.app.incredible.pets.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import me.dmdev.rxpm.base.PmFragment

abstract class BaseScreen<PM : BaseScreenPm> : PmFragment<PM>(), BackButtonHandler {

    abstract val screenLayout: Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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