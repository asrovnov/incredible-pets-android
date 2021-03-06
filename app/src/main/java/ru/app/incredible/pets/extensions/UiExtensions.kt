@file:Suppress("NOTHING_TO_INLINE")

package ru.app.incredible.pets.extensions

import android.view.*
import androidx.annotation.*

fun View.visible(visible: Boolean, useGone: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int, @StyleRes themeResId: Int? = null, attachToRoot: Boolean = false): View {
    val inflater = LayoutInflater.from(context).let {
        if (themeResId != null) {
            it.cloneInContext(ContextThemeWrapper(context, themeResId))
        } else {
            it
        }
    }

    return inflater.inflate(layoutId, this, attachToRoot)
}
