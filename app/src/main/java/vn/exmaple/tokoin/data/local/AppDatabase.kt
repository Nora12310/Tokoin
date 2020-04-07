package org.akd.muxic.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.akd.support.util.SingletonHolder
import vn.exmaple.tokoin.data.local.AccountDao
import vn.exmaple.tokoin.data.local.ArticleDao
import vn.exmaple.tokoin.data.local.converter.RoomConverter
import vn.exmaple.tokoin.model.Account
import vn.exmaple.tokoin.model.Article

@Database(entities = [Article::class, Account::class], version = 3, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val article: ArticleDao
    abstract val account: AccountDao

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            AppDatabase::class.java, "database.db"
        ).fallbackToDestructiveMigration().build()
    })
}