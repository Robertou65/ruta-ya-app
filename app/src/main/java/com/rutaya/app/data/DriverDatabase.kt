package com.rutaya.app.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest

class DriverDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE drivers (
                document_number TEXT PRIMARY KEY,
                full_name TEXT NOT NULL,
                document_type TEXT NOT NULL,
                vehicle_type TEXT NOT NULL,
                license_plate TEXT NOT NULL,
                password_hash TEXT NOT NULL
            )""".trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS drivers")
        onCreate(db)
    }

    fun register(driver: Driver, password: String): Result<Driver> = runCatching {
        val values = ContentValues().apply {
            put("document_number", driver.documentNumber.trim())
            put("full_name", driver.fullName.trim())
            put("document_type", driver.documentType)
            put("vehicle_type", driver.vehicleType)
            put("license_plate", driver.licensePlate.trim().uppercase())
            put("password_hash", hash(password))
        }
        val result = writableDatabase.insert("drivers", null, values)
        check(result != -1L) { "Ya existe una cuenta con este número de documento." }
        driver.copy(
            documentNumber = driver.documentNumber.trim(),
            fullName = driver.fullName.trim(),
            licensePlate = driver.licensePlate.trim().uppercase()
        )
    }

    fun login(documentNumber: String, password: String): Driver? {
        readableDatabase.query(
            "drivers",
            arrayOf("full_name", "document_type", "document_number", "vehicle_type", "license_plate"),
            "document_number = ? AND password_hash = ?",
            arrayOf(documentNumber.trim(), hash(password)),
            null, null, null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return Driver(
                fullName = cursor.getString(0),
                documentType = cursor.getString(1),
                documentNumber = cursor.getString(2),
                vehicleType = cursor.getString(3),
                licensePlate = cursor.getString(4)
            )
        }
    }

    private fun hash(value: String): String = MessageDigest.getInstance("SHA-256")
        .digest(value.toByteArray())
        .joinToString("") { "%02x".format(it) }

    companion object {
        private const val DATABASE_NAME = "rutaya.db"
        private const val DATABASE_VERSION = 1
    }
}
