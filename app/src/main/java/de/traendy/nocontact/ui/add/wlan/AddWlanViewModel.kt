package de.traendy.nocontact.ui.add.wlan

import androidx.lifecycle.ViewModel
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
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
            qrCodeRepository.saveQrCode(QrCode(title = name, description = "", content = content))
        }
    }
}