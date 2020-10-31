package de.traendy.nocontact.ui.main

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val _title = MutableLiveData<String>()
    val text: LiveData<String> = Transformations.map(_title) {
        it
    }

    private val _drawable = MutableLiveData<Drawable>()
    val drawable: LiveData<Drawable> = Transformations.map(_drawable) {
        it
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDrawable(drawable:Drawable?){
        _drawable.value = drawable
    }
}