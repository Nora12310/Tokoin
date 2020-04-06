package vn.exmaple.tokoin.data.local

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.room.*
import vn.exmaple.tokoin.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg recipes: Article)

    @Delete()
    suspend fun delete(recipe: Article)

    @Query("SELECT * FROM article WHERE title=:title LIMIT 1")
    suspend fun getArticle(title: Int): Article?

    @Query("SELECT * FROM article")
    fun getAll(): DataSource.Factory<Int, Article>
}