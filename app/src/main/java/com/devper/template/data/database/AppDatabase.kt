package com.devper.template.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devper.template.data.remote.user.UserData
import com.devper.template.domain.model.member.Member

@Database(
    entities = [Member::class, UserData::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun member(): MemberDao

    abstract fun user(): UserDao

    companion object {
        fun create(context: Context, useInMemory: Boolean): AppDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            }
            return databaseBuilder.run{
                fallbackToDestructiveMigration()
                allowMainThreadQueries()
                build()
            }
        }
    }
}