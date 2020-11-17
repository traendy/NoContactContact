package de.traendy.nocontact.ui.add.wlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.qrcode.WifiAuthType
import de.traendy.nocontact.qrcode.WifiQrCodeContentBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddWifiViewModel
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
    val identity: LiveData<String> = Transformations.map(_identity) {
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
        _authTypeError.postValue(false)
    }

    fun addQrCode() {
        when (_authType.value) {
            WifiAuthType.NoPass -> addNoPassQrCode()
            WifiAuthType.WEP -> addWEPQrCode()
            WifiAuthType.WPA2_EAP -> addWPA2EAPQrCode()
            WifiAuthType.WPA -> addWPAQrCode()
            null -> {
                _authTypeError.postValue(true)
            }
        }
    }

    private fun addWPAQrCode() {
        addWEPQrCode()
    }

    private fun addWPA2EAPQrCode() {
        val contentBuilder = WifiQrCodeContentBuilder()
        val tempTitle: String
        if (title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = title.value ?: ""
            _titleError.postValue(false)
        }
        if (ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(ssid.value ?: "")
            _ssidError.postValue(false)
        }
        if (password.value.isNullOrBlank()) {
            _passwordError.postValue(true)
            return
        } else {
            contentBuilder.addPassword(password.value ?: "")
            _passwordError.postValue(false)
        }
        hidden.value?.let { contentBuilder.addHiddenSsid(true) }

        if (eapMethod.value.isNullOrBlank()) {
            _eapMethodError.postValue(true)
            return
        } else {
            contentBuilder.addEapMethod(eapMethod.value ?: "")
            _eapMethodError.postValue(false)
        }

        if (_identity.value.isNullOrBlank() && _anonymousIdentity.value.isNullOrBlank()) {
            _identityError.postValue(true)
            _anonymousIdentityError.postValue(true)
            return
        } else {
            if (_identity.value.isNullOrBlank()) {
                contentBuilder.addAnonymousIdentity(_anonymousIdentity.value ?: "")
            } else {
                contentBuilder.addIdentity(_identity.value ?: "")
            }
            _identityError.postValue(false)
            _anonymousIdentityError.postValue(false)
            _identityError.postValue(false)
        }
        if (phase2Method.value.isNullOrBlank()) {
            _phase2MethodError.postValue(true)
            return
        } else {
            contentBuilder.addPhase2Method(phase2Method.value ?: "")
            _phase2MethodError.postValue(false)
        }

        saveQrCode(tempTitle, contentBuilder.create())
    }

    private val _titleError = MutableLiveData<Boolean>()
    val titleError: LiveData<Boolean> = _titleError

    private val _ssidError = MutableLiveData<Boolean>()
    val ssidError: LiveData<Boolean> = Transformations.map(_ssidError) {
        it
    }

    private val _passwordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean> = Transformations.map(_passwordError) {
        it
    }

    private val _phase2MethodError = MutableLiveData<Boolean>()
    val phase2MethodError: LiveData<Boolean> = Transformations.map(_phase2MethodError) {
        it
    }

    private val _identityError = MutableLiveData<Boolean>()
    val identityError: LiveData<Boolean> = Transformations.map(_identityError) {
        it
    }

    private val _anonymousIdentityError = MutableLiveData<Boolean>()
    val anonymousIdentityError: LiveData<Boolean> = Transformations.map(_anonymousIdentityError) {
        it
    }

    private val _eapMethodError = MutableLiveData<Boolean>()
    val eapMethodError: LiveData<Boolean> = Transformations.map(_eapMethodError) {
        it
    }

    private val _authTypeError = MutableLiveData<Boolean>()
    val authTypeError: LiveData<Boolean> = Transformations.map(_authTypeError) {
        it
    }

    private fun addWEPQrCode() {
        val contentBuilder = WifiQrCodeContentBuilder()
        val tempTitle: String
        if (title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = title.value ?: ""
            _titleError.postValue(false)
        }
        if (ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(ssid.value ?: "")
            _ssidError.postValue(false)
        }
        if (password.value.isNullOrBlank()) {
            _passwordError.postValue(true)
            return
        } else {
            contentBuilder.addPassword(password.value ?: "")
            _passwordError.postValue(false)
        }
        hidden.value?.let { contentBuilder.addHiddenSsid(true) }
        saveQrCode(tempTitle, contentBuilder.create())
    }

    private fun addNoPassQrCode() {
        val contentBuilder = WifiQrCodeContentBuilder()
        val tempTitle: String
        if (title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = title.value ?: ""
            _titleError.postValue(false)
        }
        if (ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(ssid.value ?: "")
            _ssidError.postValue(false)
        }
        hidden.value?.let { contentBuilder.addHiddenSsid(true) }
        saveQrCode(tempTitle, contentBuilder.create())
    }
}