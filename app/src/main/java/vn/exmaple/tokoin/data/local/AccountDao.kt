package vn.exmaple.tokoin.data.local

import androidx.paging.DataSource
import androidx.room.*
import vn.exmaple.tokoin.model.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg account: Account): List<Long>

    @Query("SELECT * FROM account")
    fun getAll(): DataSource.Factory<Int, Account>

    @Delete
    suspend fun delete(id: Account)
}