package de.traendy.nocontact.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddQrCodeViewModel
constructor(private val qrCodeRepository: QrCodeRepository
) : ViewModel(), CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()
    private lateinit var job: Job

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

    fun saveQrCode(name: String, content: String){
        launch {
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
    }
}