package com.example.finaldiplom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.finaldiplom.database.converters.Converters
import com.example.finaldiplom.database.entities.Budget
import com.example.finaldiplom.database.entities.Spending

@Database(entities = [Spending::class, Budget::class], version = 3)
@TypeConverters(Converters::class)
abstract class SpendingDatabase: RoomDatabase() {
    companion object{
        const val NAME = "Spendings_DB"
        var MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Spending ADD COLUMN month TEXT DEFAULT '' NOT NULL")
                db.execSQL("ALTER TABLE Spending ADD COLUMN year TEXT DEFAULT '' NOT NULL")
            }
        }

    }

    abstract fun getSpendingDao() : SpendingDataAccessObject
}