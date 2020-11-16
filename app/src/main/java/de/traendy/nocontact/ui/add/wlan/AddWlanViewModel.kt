package de.traendy.nocontact.ui.add.wlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.qrcode.WifiAuthType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddWlanViewModel
constructor(
        private val qrCodeRepository: QrCodeRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    private fun saveQrCode(name: String, content: String) {
        launch {
            // TODO set content depending of authtype
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
    }

    private val _authType = MutableLiveData<WifiAuthType>()
    val authType: LiveData<WifiAuthType> = Transformations.map(_authType) {
        it
    }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = Transformations.map(_title) {
        it
    }

    private val _ssid = MutableLiveData<String>()
    val ssid: LiveData<String> = Transformations.map(_ssid) {
        it
    }

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = Transformations.map(_password) {
        it
    }

    private val _hidden = MutableLiveData<Boolean>()
    val hidden: LiveData<Boolean> = Transformations.map(_hidden) {
        it
    }

    private val _eapMethod = MutableLiveData<String>()
    val eapMethod: LiveData<String> = Transformations.map(_eapMethod) {
        it
    }

    private val _anonymousIdentity = MutableLiveData<String>()
    val anonymousIdentity: LiveData<String> = Transformations.map(_anonymousIdentity) {
        it
    }

    private val _identity = MutableLiveData<String>()
    val iIdentity: LiveData<String> = Transformations.map(_identity) {
        it
    }

    private val _phase2Method = MutableLiveData<String>()
    val phase2Method: LiveData<String> = Transformations.map(_phase2Method) {
        it
    }

    fun setPhase2Method(phase2Method: String) {
        _phase2Method.postValue(phase2Method)
    }

    fun setIdentity(identity: String) {
        _identity.postValue(identity)
    }

    fun setAnonymousIdentity(anonymousIdentity: String) {
        _anonymousIdentity.postValue(anonymousIdentity)
    }

    fun setEapMethod(eapMethod: String) {
        _eapMethod.postValue(eapMethod)
    }

    fun setSsidHidden(hidden: Boolean) {
        _hidden.postValue(hidden)
    }

    fun setPassword(password: String) {
        _password.postValue(password)
    }

    fun setSsid(ssid: String) {
        _ssid.postValue(ssid)
    }


    fun setTitle(title: String) {
        _title.postValue(title)
    }

    fun setAuthType(authType: WifiAuthType) {
        _authType.postValue(authType)
    }
}