package de.traendy.nocontact.ui.qrcodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class QrCodeFragmentViewModel constructor(private val qrCodeRepository: QrCodeRepository) :
    ViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    private val _qrCodes = MutableLiveData<Collection<QrCode>>()
    val qrCodes: LiveData<Collection<QrCode>> = _qrCodes

    fun loadQrCodes() {
        launch {
            qrCodeRepository.getAll().collect {
                _qrCodes.postValue(it)
            }
        }
    }

    fun deleteCode(qrCode: QrCode) {
        launch {
            qrCodeRepository.delete(qrCode)
        }
    }
}

class QrCodeFragmentViewModelFactory(private val qrCodeRepository: QrCodeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(QrCodeRepository::class.java).newInstance(qrCodeRepository)
    }
}