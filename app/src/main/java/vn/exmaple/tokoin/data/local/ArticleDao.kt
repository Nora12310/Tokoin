package vn.exmaple.tokoin.data.local

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.room.*
import vn.exmaple.tokoin.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg recipes: Article): List<Long>

    @Delete()
    suspend fun delete(recipe: Article)

    @Query("SELECT * FROM article WHERE title=:title LIMIT 1")
    suspend fun getArticle(title: String): Article?

    @Query("SELECT * FROM article WHERE keyword=:keyword")
    fun getAll(keyword: String): DataSource.Factory<Int, Article>

    @Query("SELECT COUNT(*) FROM article WHERE keyword=:keyword")
    suspend fun count(keyword: String): Int
}