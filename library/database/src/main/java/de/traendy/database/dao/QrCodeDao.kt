package de.traendy.database.dao

import androidx.room.*
import de.traendy.database.model.QrCode

@Dao
interface QrCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(element: QrCode)

    @Query("DELETE FROM qr_code_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM qr_code_table ORDER BY title ASC")
    suspend fun getAll(): List<QrCode>

    @Delete
    suspend fun deleteDesignPattern(element: QrCode)

    @Query("SELECT * FROM qr_code_table WHERE id = :elementId LIMIT 1")
    fun getDesignPatternById(elementId: Int): QrCode

}