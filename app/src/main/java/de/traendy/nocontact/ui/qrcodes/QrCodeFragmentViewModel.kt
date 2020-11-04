package de.traendy.nocontact.ui.qrcodes

import androidx.lifecycle.*
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class QrCodeFragmentViewModel constructor(private val qrCodeRepository: QrCodeRepository) :
    ViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()
    private lateinit var job: Job

    private val _qrCodes = MutableLiveData<Collection<QrCode>>()
    val qrCodes: LiveData<Collection<QrCode>> = _qrCodes

    fun loadQrCodes() {
        launch {
            qrCodeRepository.getAll().collect {
                _qrCodes.postValue(it)
            }
        }
    }
}

class QrCodeFragmentViewModelFactory(private val qrCodeRepository: QrCodeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(QrCodeRepository::class.java).newInstance(qrCodeRepository)
    }
}