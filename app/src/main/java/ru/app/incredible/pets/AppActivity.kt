package ru.app.incredible.pets

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler
import ru.app.incredible.pets.ui.common.BackButtonHandler

class AppActivity : AppCompatActivity(), NavigationMessageHandler {

    private lateinit var navigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {

        val instanceState = null

        super.onCreate(instanceState)

        setTheme(R.style.AppTheme)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        navigator = FragmentNavigator(R.id.screenContainer, supportFragmentManager, this)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun handleNavigationMessage(msg: NavigationMessage): Boolean {
        when (msg) {
            is BackMessage -> navigator.back()

            else -> return false
        }

        return true
    }

    override fun onBackPressed() {
        val visibleScreen = supportFragmentManager.findFragmentById(R.id.screenContainer)
        if ((visibleScreen as? BackButtonHandler)?.onBackPressed() != true) {
            handleNavigationMessage(BackMessage())
        }
    }
}