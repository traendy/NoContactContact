package de.traendy.nocontact.ui.add.mail


import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import kotlinx.coroutines.*
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

    private fun saveQrCode(name: String, content: String) {
        launch {
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
    }

    fun onClick(title: Editable?, eMail: Editable?, subject: Editable?, body: Editable?) {
        val builder = StringBuilder()
        builder.append("mailto:").append(eMail)
        if (!subject.isNullOrBlank()) {
            builder.append("?").append("subject=").append(replaceSpaces(subject))
            if (!body.isNullOrBlank()) {
                builder.append("&").append("body=").append(replaceSpaces(body))
            }
        }
        saveQrCode(title.toString(), builder.toString())
    }

    private fun replaceSpaces(editable: Editable): String {
        return editable.toString().replace(" ", "%20", true)
    }
}