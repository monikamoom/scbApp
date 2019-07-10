package com.codemobiles.scbauthen.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


// #step2 ประกาศtableใน entities = [...]
@Database(entities = [UserEntity::class,CompanyEntity::class], version = 4, exportSchema = true)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO // #step3
    abstract fun companyDAO(): CompanyDAO
    companion object {

        private val TAG: String by lazy { AppDatabase::class.java.simpleName }

        // For Singleton instantiation, visible to other threads.
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (instance != null){
                return instance!!
            }

            instance?.let {
                return it
            }
            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE `company` (`id` INTEGER, `name` TEXT NOT NULL,`company_id` TEXT NOT NULL, PRIMARY KEY(`id`))")
                }
            }
            val MIGRATION_2_3 = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE `users` ADD `role` TEXT NOT NULL DEFAULT '0'")
                }
            }
            val MIGRATION_3_4 = object : Migration(3, 4) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // SQLite supports a limited operations for ALTER.
                    // Changing the type of a column is not directly supported, so this is what we need
                    database.execSQL(
                        "CREATE TABLE users_new (id INTEGER AUTO_INCREMENT,"
                                + "user_id TEXT NOT NULL,"
                                + "username TEXT NOT NULL,"
                                + "password TEXT NOT NULL,"
                                + "role INTEGER NOT NULL, "
                                + "PRIMARY KEY(id))"
                    )
                    database.execSQL(
                        "INSERT INTO users_new (id, user_id, username, password, role) " +
                                "SELECT id, user_id, username, password, role FROM users"
                    )
                    database.execSQL("DROP TABLE users")
                    database.execSQL("ALTER TABLE users_new RENAME TO users")
                }
            }
//            val MIGRATION_2_3 = object : Migration(2, 3) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("ALTER TABLE `company` ADD `address` TEXT")
//                }
//            }

            // query -> insert -> drop table

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "SCB" // #step1 ตั้งชื่อdatabase ใน "..."
                ).addCallback(object : RoomDatabase.Callback() {
                    // onCreate will be called when the database is created for the first time
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "onCreate")
                    }
                }).addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4)

                    .build().also {
                    instance = it
                    return instance!!
                }
            }
        }
        //clear database !!อย่าลืม set null
        fun destroyInstance() {
            instance = null
        }
    }
}

