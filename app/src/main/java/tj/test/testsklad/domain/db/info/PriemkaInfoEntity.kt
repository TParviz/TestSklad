package tj.test.testsklad.domain.db.info

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PriemkaInfoEntity(
    @PrimaryKey val priemka: String,
    val code: String,
    val seria: String,
    val barCodes: List<String>
)