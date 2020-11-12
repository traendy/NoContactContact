package de.traendy.nocontact.ui.add.mail


import android.graphics.Bitmap
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

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = Transformations.map(_title) {
        it
    }

    fun updateTitle(title: CharSequence?) {
        launch {
            flow {
                emit(title?.toString() ?: "")
            }.distinctUntilChanged().collect {
                _title.postValue(it)
            }
        }
    }

    private val _preview = MutableLiveData<Bitmap>()
    val preview: LiveData<Bitmap> = Transformations.map(_preview) {
        it
    }

    private val _eMail = MutableLiveData<String>()
    val eMail: LiveData<String> = Transformations.map(_eMail) {
        it
    }

    fun upDateEMail(eMail: CharSequence?) {
        _eMail.postValue(eMail?.toString() ?: "")
    }


    private val _subject = MutableLiveData<String>()
    val subject: LiveData<String> = Transformations.map(_subject) {
        it
    }

    fun upDateSubject(subject: CharSequence?) {
        _subject.postValue(subject?.toString() ?: "")
    }

    private val _body = MutableLiveData<String>()
    val body: LiveData<String> = Transformations.map(_body) {
        it
    }

    fun upDateBody(body: CharSequence?) {
        _body.postValue(body?.toString() ?: "")
    }

    private fun saveQrCode(name: String, content: String) {
        launch {
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
    }

    fun onClick(title: String) {
        saveQrCode(title, qrCodeContentFactory(eMail.value ?: "", subject.value
                ?: "", body.value ?: ""))
    }

    private fun replaceSpaces(text: String): String {
        return text.replace(" ", "%20", true)
    }

    @FlowPreview
    fun updateQrCodePreview(size: Int) {
        launch {
            flow {
                val code = QrCodeGenerator().createQrCodeBitMap(
                        qrCodeContentFactory(eMail.value ?: "", subject.value
                                ?: "", body.value ?: ""),
                        size
                )
                emit(code)
            }.debounce(300).distinctUntilChanged().flowOn(Dispatchers.IO).collect {
                _preview.postValue(it)
            }
        }
    }

    private fun qrCodeContentFactory(eMail: String, subject: String, body: String): String {
        val builder = StringBuilder()
        builder.append("mailto:").append(eMail)
        if (!subject.isBlank()) {
            builder.append("?").append("subject=").append(replaceSpaces(subject))
            if (!body.isBlank()) {
                builder.append("&").append("body=").append(replaceSpaces(body))
            }
        }
        return builder.toString()
    }

}