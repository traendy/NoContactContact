package de.traendy.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_code_table")
data class QrCode(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int = 0,
    @ColumnInfo(name = "title")
    @NonNull
    val title: String = "",
    @ColumnInfo(name = "description")
    @NonNull
    val description: String = "",
    @ColumnInfo(name = "content")
    val content: String? = null,)