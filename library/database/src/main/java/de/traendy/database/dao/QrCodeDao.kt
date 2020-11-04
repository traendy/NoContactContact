package de.traendy.database.dao

import androidx.room.*
import de.traendy.database.model.QrCode
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(element: QrCode)

    @Query("DELETE FROM qr_code_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM qr_code_table ORDER BY title ASC")
    fun getAll(): Flow<List<QrCode>>

    @Delete
    suspend fun delete(element: QrCode)

    @Query("SELECT * FROM qr_code_table WHERE id = :elementId LIMIT 1")
    fun getById(elementId: Int): QrCode

}