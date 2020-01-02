package ru.app.incredible.pets

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigator(
    @IdRes val screenContainerId: Int,
    val fm: FragmentManager,
    val activity: Activity
) {

    private val Fragment.screenTag get() = this::class.java.name

    fun setRoot(fragment: Fragment) {
        fm.beginTransaction()
            .add(screenContainerId, fragment, fragment.screenTag)
            .commitNow()
    }

    fun goTo(fragment: Fragment) {
        fm.beginTransaction()
            .addToBackStack(fragment.screenTag)
            .replace(screenContainerId, fragment, fragment.screenTag)
            .commit()
    }

    fun replace(fragment: Fragment) {
        back()
        goTo(fragment)
    }

    fun back() {
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            activity.finish()
        }
    }

    fun backToRoot() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun showDialog(fragment: DialogFragment) {
        fragment.show(
            fm.beginTransaction()
                .addToBackStack(fragment.screenTag),
            fragment.screenTag
        )
    }

    inline fun <reified T : Fragment> backTo() {
        fm.popBackStackImmediate(T::class.java.name, 0)
    }
}