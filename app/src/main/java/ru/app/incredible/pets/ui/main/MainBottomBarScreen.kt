package ru.app.incredible.pets.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.screen_main_bottom_bar.*
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler
import me.dmdev.rxpm.passTo
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.GalleryOpenScreen
import ru.app.incredible.pets.PetsOpenScreen
import ru.app.incredible.pets.R
import ru.app.incredible.pets.ui.gallery.GalleryScreen
import ru.app.incredible.pets.ui.common.BaseScreen
import ru.app.incredible.pets.ui.pets.PetsScreen

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
                R.id.pets -> Page.PETS.ordinal
                R.id.gallery -> Page.GALLERY.ordinal
                else -> Page.PETS.ordinal
            }.let { viewPager.setCurrentItem(it, true) }
            true
        }

        view?.setOnApplyWindowInsetsListener { _, insets ->
            viewPager.dispatchApplyWindowInsets(insets)
                .consumeSystemWindowInsets()
        }
    }

    private enum class Page { PETS, GALLERY }

    private class PageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

        override fun getItemCount() = Page.values().size

        override fun createFragment(position: Int): Fragment {
            return when(Page.values()[position]) {
                Page.PETS -> PetsScreen()
                Page.GALLERY -> GalleryScreen()
            }
        }
    }

    override fun handleNavigationMessage(message: NavigationMessage): Boolean {
        return when (message) {
            is PetsOpenScreen -> {
                bottomNavigation.selectedItemId = R.id.pets
                true
            }

            is GalleryOpenScreen -> {
                bottomNavigation.selectedItemId = R.id.gallery
                true
            }

            else -> false
        }
    }
}
