package com.ogzkesk.filmapp.util

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ogzkesk.filmapp.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun ImageView.showImage(imgUrl : String?){
    Glide.with(context)
        .load(imgUrl)
        .apply (
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.error)
        )
        .into(this)
}

fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    lifecycle: Lifecycle = viewLifecycleOwner.lifecycle,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    action: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch(dispatcher) {
        flow.flowWithLifecycle(lifecycle, state).collect {
            action(it)
        }
    }
}

fun Fragment.showToast(message: String){
    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
}

fun String.capitalize(): String {
    return if (isNotEmpty() && this[0].isLowerCase()) substring(0, 1).uppercase() + substring(1) else this
}