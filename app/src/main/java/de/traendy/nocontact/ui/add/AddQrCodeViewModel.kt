package de.traendy.nocontact.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class AddQrCodeViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = Transformations.map(_name) {
        it
    }

    fun setName(name: String) {
        _name.value = name
    }

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> = Transformations.map(_content) {
        it
    }

    fun setContent(content: String) {
        _content.value = content
    }
}