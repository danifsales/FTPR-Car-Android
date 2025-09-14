package com.example.myapitest.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    val MIGRATION_1_TO_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Criar uma nova tabela com a nova estrutura
            database.execSQL("""
            CREATE TABLE new_location_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                latitude REAL NOT NULL,
                longitude REAL NOT NULL,
                createdAt INTEGER NOT NULL DEFAULT (strftime('%s','now')) 
            )
        """.trimIndent())

            database.execSQL("""
            INSERT INTO new_location_table (id, latitude, longitude)
            SELECT id, latitude, longitude FROM user_location_table
        """.trimIndent())

            database.execSQL("DROP TABLE user_location_table")

            database.execSQL("ALTER TABLE new_location_table RENAME TO user_location_table")
        }
    }
}