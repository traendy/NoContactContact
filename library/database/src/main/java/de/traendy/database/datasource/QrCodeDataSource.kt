package de.traendy.database.datasource

import de.traendy.database.dao.QrCodeDao
import de.traendy.database.model.QrCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class QrCodeDataSource(
    private val qrCodeDao: QrCodeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getQrCodeById(id: Int): QrCode = withContext(ioDispatcher) {
        return@withContext qrCodeDao.getDesignPatternById(id)
    }

    suspend fun getAllQrCodes(): Collection<QrCode> =
        withContext(ioDispatcher) {
            return@withContext qrCodeDao.getAll()
        }

    suspend fun getQrCodesBySearchString(searchString: String): Collection<QrCode> =
        withContext(ioDispatcher) {
            return@withContext qrCodeDao.getAll().filter {
                it.title.contains(searchString)
            }
        }

    suspend fun saveQrCode(qrCode: QrCode) {
        qrCodeDao.insert(qrCode)
    }
}