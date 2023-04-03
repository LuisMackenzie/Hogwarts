package com.mackenzie.hogwarts.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mackenzie.domain.ImagesItem
import com.mackenzie.hogwarts.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <T> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.baseline_downloading)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_error_grey)
        .into(this)
}

fun createImageUrl(houseName: String): String {
    val resources = ImagesItem()
    when (houseName) {
        "Gryffindor" -> return resources.gryf
        "Slytherin" -> return resources.slyt
        "Ravenclaw" -> return resources.rave
        "Hufflepuff" -> return resources.huff
        "Hogwarts" -> return resources.hogwarts
        else -> return resources.heads
    }
}

fun createImageHeadUrl(headName: String): String {
    val resources = ImagesItem()
    when (headName) {
        "Minerva" -> return resources.minerva
        "Godric" -> return resources.godric
        "Filius" -> return resources.filius
        "Rowena" -> return resources.rowena
        "Helga" -> return resources.helga
        "Pomona" -> return resources.pomona
        "Horace" -> return resources.horace
        "Salazar" -> return resources.salazar
        "Severus" -> return resources.severus
        else -> return resources.heads
    }
}

fun ImageView.loadUrlWithCircleCrop(url: String) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.baseline_downloading)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_error_grey)
        .into(this)
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun <T> LifecycleOwner. launchAndCollect(
    flow: Flow<T>, state:
    Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}