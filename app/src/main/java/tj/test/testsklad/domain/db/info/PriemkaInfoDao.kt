package tj.test.testsklad.domain.db.info

import androidx.room.*

@Dao
interface PriemkaInfoDao {

    @Query("SELECT * FROM priemkainfoentity")
    suspend fun getAll(): List<PriemkaInfoEntity>

    @Query("SELECT * FROM priemkainfoentity WHERE seria LIKE :seria")
    suspend fun findByTitle(seria: String): PriemkaInfoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(info: PriemkaInfoEntity)

    @Query("DELETE FROM priemkainfoentity WHERE seria = :seria")
    fun deleteByPriemka(seria: String)

    @Update
    suspend fun updateInfo(info: PriemkaInfoEntity)
}

