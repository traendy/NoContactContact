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
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
        _qrCodeCreated.postValue(true)
    }

    private val _authType = MutableLiveData<WifiAuthType>()
    val authType: LiveData<WifiAuthType> = Transformations.map(_authType) {
        it
    }

    private val _qrCodeCreated = MutableLiveData<Boolean>()
    val qrCodeCreated: LiveData<Boolean> = Transformations.map(_qrCodeCreated) {
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
        _phase2Method.value = phase2Method
    }

    fun setIdentity(identity: String) {
        _identity.value = identity
    }

    fun setAnonymousIdentity(anonymousIdentity: String) {
        _anonymousIdentity.value = anonymousIdentity
    }

    fun setEapMethod(eapMethod: String) {
        _eapMethod.value = eapMethod
    }

    fun setSsidHidden(hidden: Boolean) {
        _hidden.value = hidden
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setSsid(ssid: String) {
        _ssid.value = ssid
    }


    fun setTitle(title: String) {
        _title.value = title
    }

    fun setAuthType(authType: WifiAuthType) {
        _authType.value = authType
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
        val contentBuilder = WifiQrCodeContentBuilder()
        val tempTitle: String
        if (_title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = _title.value ?: ""
            _titleError.postValue(false)
        }
        if (_ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(_ssid.value ?: "")
            _ssidError.postValue(false)
        }
        if (_password.value.isNullOrBlank()) {
            _passwordError.postValue(true)
            return
        } else {
            contentBuilder.addPassword(_password.value ?: "")
            _passwordError.postValue(false)
        }
        _hidden.value?.let { contentBuilder.addHiddenSsid(true) }
        _authType.value?.let {
            contentBuilder.addAuthType(it)
        }
        saveQrCode(tempTitle, contentBuilder.create())
    }

    private fun addWPA2EAPQrCode() {
        val contentBuilder = WifiQrCodeContentBuilder()
        val tempTitle: String
        if (_title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = _title.value ?: ""
            _titleError.postValue(false)
        }
        if (_ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(_ssid.value ?: "")
            _ssidError.postValue(false)
        }
        if (_password.value.isNullOrBlank()) {
            _passwordError.postValue(true)
            return
        } else {
            contentBuilder.addPassword(_password.value ?: "")
            _passwordError.postValue(false)
        }
        _hidden.value?.let { contentBuilder.addHiddenSsid(true) }

        if (_eapMethod.value.isNullOrBlank()) {
            _eapMethodError.postValue(true)
            return
        } else {
            contentBuilder.addEapMethod(_eapMethod.value ?: "")
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
        if (_phase2Method.value.isNullOrBlank()) {
            _phase2MethodError.postValue(true)
            return
        } else {
            contentBuilder.addPhase2Method(_phase2Method.value ?: "")
            _phase2MethodError.postValue(false)
        }
        _authType.value?.let {
            contentBuilder.addAuthType(it)
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
        if (_title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = _title.value ?: ""
            _titleError.postValue(false)
        }
        if (_ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(_ssid.value ?: "")
            _ssidError.postValue(false)
        }
        if (_password.value.isNullOrBlank()) {
            _passwordError.postValue(true)
            return
        } else {
            contentBuilder.addPassword(_password.value ?: "")
            _passwordError.postValue(false)
        }
        _hidden.value?.let { contentBuilder.addHiddenSsid(true) }
        _authType.value?.let {
            contentBuilder.addAuthType(it)
        }
        saveQrCode(tempTitle, contentBuilder.create())

    }

    private fun addNoPassQrCode() {
        val contentBuilder = WifiQrCodeContentBuilder()
        val tempTitle: String
        if (_title.value.isNullOrBlank()) {
            _titleError.postValue(true)
            return
        } else {
            tempTitle = _title.value ?: ""
            _titleError.postValue(false)
        }
        if (_ssid.value.isNullOrBlank()) {
            _ssidError.postValue(true)
            return
        } else {
            contentBuilder.addSsid(_ssid.value ?: "")
            _ssidError.postValue(false)
        }
        _hidden.value?.let { contentBuilder.addHiddenSsid(true) }
        _authType.value?.let {
            contentBuilder.addAuthType(it)
        }
        saveQrCode(tempTitle, contentBuilder.create())
    }

    fun resetQrCodeCreated() {
        _qrCodeCreated.postValue(false)
    }
}