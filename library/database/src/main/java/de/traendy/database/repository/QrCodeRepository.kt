package de.traendy.database.repository

import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.model.QrCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QrCodeRepository constructor(
    private val qrCodeDataSource: QrCodeDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getById(id: Int): QrCode {
        return withContext(ioDispatcher) {
            return@withContext qrCodeDataSource.getQrCodeById(id)
        }
    }

    fun getAll(): Flow<Collection<QrCode>> = qrCodeDataSource.getAllQrCodes()


    suspend fun saveQrCode(qrCode: QrCode) {
        withContext(ioDispatcher) {
            qrCodeDataSource.saveQrCode(qrCode)
        }
    }

    suspend fun getQrCode(searchString: String): Collection<QrCode> {
        return withContext(ioDispatcher) {
            return@withContext qrCodeDataSource.getQrCodesBySearchString(
                    searchString
            )
        }
    }

    suspend fun delete(qrCode: QrCode) {
        withContext(ioDispatcher) {
            qrCodeDataSource.delete(qrCode)
        }
    }
}
