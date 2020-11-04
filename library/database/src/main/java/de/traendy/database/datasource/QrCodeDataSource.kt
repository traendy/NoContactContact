package de.traendy.database.datasource

import de.traendy.database.dao.QrCodeDao
import de.traendy.database.model.QrCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QrCodeDataSource(
        private val qrCodeDao: QrCodeDao,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getQrCodeById(id: Int): QrCode = withContext(ioDispatcher) {
        return@withContext qrCodeDao.getById(id)
    }

    fun getAllQrCodes(): Flow<Collection<QrCode>> = qrCodeDao.getAll()


    // TODO query db not list
    suspend fun getQrCodesBySearchString(searchString: String): Collection<QrCode> = emptyList()

    suspend fun saveQrCode(qrCode: QrCode) {
        qrCodeDao.insert(qrCode)
    }
}