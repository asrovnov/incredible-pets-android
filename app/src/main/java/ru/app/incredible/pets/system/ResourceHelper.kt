package ru.app.incredible.pets.system

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Helps to get resources without importing the context.
 */
class ResourceHelper(private val context: Context) {

    companion object {
        const val DRAWABLE_DEF_TYPE = "drawable"
    }

    fun getDrawable(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(context, drawableRes)

    fun getDrawableResourceId(name: String) =
        context.resources.getIdentifier(name, DRAWABLE_DEF_TYPE, context.packageName)

    fun getString(@StringRes stringRes: Int): String =
        context.getString(stringRes)

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String =
        context.getString(stringRes, *formatArgs)

    fun getStringsList(@ArrayRes arrayRes: Int) =
        context.resources.getStringArray(arrayRes).toList()
}