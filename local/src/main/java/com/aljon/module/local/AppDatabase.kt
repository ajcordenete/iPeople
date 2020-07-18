package com.aljon.module.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aljon.baseplate.local.BuildConfig
import com.aljon.module.local.features.auth.dao.SessionDao
import com.aljon.module.local.features.auth.dao.UserDao
import com.aljon.module.local.features.auth.models.DBSession
import com.aljon.module.local.features.auth.models.DBUser
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
        DBSession::class,
        DBUser::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters
abstract class AppDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            val dbName = "ipeople.db"

            val builder = Room.databaseBuilder(context, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()

            if (!BuildConfig.DEBUG) {
                val passphrase = SQLiteDatabase.getBytes(dbName.toCharArray())
                val factory = SupportFactory(passphrase)
                builder.openHelperFactory(factory)
            }

            return builder.build()
        }
    }
}
