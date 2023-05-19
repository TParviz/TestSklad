package tj.test.testsklad.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tj.test.testsklad.domain.db.info.PriemkaInfoDao
import tj.test.testsklad.domain.db.info.PriemkaInfoEntity

@Database(
    entities = [PriemkaInfoEntity::class],
    version = 2,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priemkaInfoDao(): PriemkaInfoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: tj.test.testsklad.domain.db.Database.build(context)
                .also { instance = it }
        }
    }
}
