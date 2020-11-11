package de.traendy.nocontact.ui.add.mail


import android.graphics.Bitmap
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import de.traendy.qrcode.QrCodeGenerator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class MailViewModel
constructor(
    private val qrCodeRepository: QrCodeRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()
    private lateinit var job: Job

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = Transformations.map(_name) {
        it
    }

    private val _preview = MutableLiveData<Bitmap>()
    val preview: LiveData<Bitmap> = Transformations.map(_preview) {
        it
    }


    private fun saveQrCode(name: String, content: String) {
        launch {
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
    }

    fun onClick(title: Editable?, eMail: Editable?, subject: Editable?, body: Editable?) {
        val builder = StringBuilder()
        builder.append("mailto:").append(eMail)
        if (!subject.isNullOrBlank()) {
            builder.append("?").append("subject=").append(replaceSpaces(subject.toString()))
            if (!body.isNullOrBlank()) {
                builder.append("&").append("body=").append(replaceSpaces(body.toString()))
            }
        }
        saveQrCode(title.toString(), builder.toString())
    }

    private fun replaceSpaces(text: String): String {
        return text.replace(" ", "%20", true)
    }

    // Protoyp pattern
    private val _qrCodeData = MutableLiveData<QrCode>()
    fun updateTitle(title: String, qrCode: QrCode) {
        if (!title.isBlank()) {
            _qrCodeData.value = QrCode(title = title, description = qrCode.description, content = qrCode.content
                    ?: "")
        }
    }

    fun updateDescription(description: String, qrCode: QrCode) {
        if (!description.isBlank()) {
            _qrCodeData.value = QrCode(title = qrCode.title, description = description, content = qrCode.content
                    ?: "")
        }
    }

    fun updateContent(content: String, qrCode: QrCode) {
        if (!content.isBlank()) {
            _qrCodeData.value = QrCode(title = qrCode.title, description = qrCode.description, content = content)
        }
    }

    private val _eMail = MutableLiveData<String>()
    private val _subject = MutableLiveData<String>()
    private val _body = MutableLiveData<String>()


    @FlowPreview
    fun updateQrCodePreview(size: Int, email: String = "", subject: String = "", body: String = "") {
        if (!email.isBlank()) {
            _eMail.value = email
        }
        if (!subject.isBlank()) {
            _subject.value = subject
        }
        if (!body.isBlank()) {
            _body.value = body
        }
        launch {
            flow<Bitmap> {

                val builder = StringBuilder()
                builder.append("mailto:").append(_eMail.value ?: "")
                if (!_subject.value.isNullOrBlank()) {
                    builder.append("?").append("subject=").append(replaceSpaces(_subject.value
                            ?: ""))
                    if (!_body.value.isNullOrBlank()) {
                        builder.append("&").append("body=").append(replaceSpaces(_body.value ?: ""))
                    }
                }
                val code = QrCodeGenerator().createQrCodeBitMap(
                        builder.toString(),
                        size
                )
                emit(code)
            }.debounce(300).distinctUntilChanged().flowOn(Dispatchers.IO).collect {
                _preview.postValue(it)
            }

        }
    }
}