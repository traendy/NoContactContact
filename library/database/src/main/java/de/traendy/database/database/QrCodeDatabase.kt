package de.traendy.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import de.traendy.database.dao.QrCodeDao
import de.traendy.database.model.QrCode

@Database(
    entities = [
        QrCode::class
    ],
    version = 1,
    exportSchema = true
)
abstract class QrCodeDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QrCodeDao

    companion object {
        private lateinit var instance: QrCodeDatabase
        fun provideDatabase(context: Context): QrCodeDatabase {
            if (!this::instance.isInitialized) {
                instance = databaseBuilder(
                    context.applicationContext,
                    QrCodeDatabase::class.java,
                    "design_patterns.db"
                ).build()
            }
            return instance
        }
    }
}

