package ru.app.incredible.pets.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.screen_main_bottom_bar.*
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.CatsOpenScreen
import ru.app.incredible.pets.DogsOpenScreen
import ru.app.incredible.pets.R
import ru.app.incredible.pets.ui.cats.CatsScreen
import ru.app.incredible.pets.ui.common.BaseScreen
import ru.app.incredible.pets.ui.dogs.DogsScreen

class MainBottomBarScreen : BaseScreen<MainBottomBarPm>(), NavigationMessageHandler {

    override val screenLayout = R.layout.screen_main_bottom_bar

    override fun providePresentationModel(): MainBottomBarPm {
        return getKoin().get()
    }

    override fun onInitView() {
        super.onInitView()

        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = Page.values().size - 1
        viewPager.adapter = PageAdapter(this)

        bottomNavigation.setOnNavigationItemSelectedListener { tab ->
            when (tab.itemId) {
                R.id.dogs -> Page.DOGS.ordinal
                R.id.cats -> Page.CATS.ordinal
                else -> Page.DOGS.ordinal
            }.let {
                viewPager.setCurrentItem(it, true)
            }
            true
        }

        view?.setOnApplyWindowInsetsListener { _, insets ->
            viewPager.dispatchApplyWindowInsets(insets)
                .consumeSystemWindowInsets()
        }
    }

    private enum class Page { DOGS, CATS }

    private class PageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

        override fun getItemCount() = Page.values().size

        override fun createFragment(position: Int): Fragment {
            return when(Page.values()[position]) {
                Page.DOGS -> DogsScreen()
                Page.CATS -> CatsScreen()
            }
        }
    }

    override fun handleNavigationMessage(message: NavigationMessage): Boolean {
        return when (message) {
            is DogsOpenScreen -> {
                bottomNavigation.selectedItemId = R.id.dogs
                true
            }

            is CatsOpenScreen -> {
                bottomNavigation.selectedItemId = R.id.cats
                true
            }

            else -> false
        }
    }
}
