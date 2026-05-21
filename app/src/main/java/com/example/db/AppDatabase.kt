package com.example.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "test_attempts")
data class TestAttempt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateMillis: Long,
    val score: Int,
    val totalQuestions: Int,
    val domainScoresJson: String // Serialized format of Map<Int, Int> representing correct answers per domain
)

@Dao
interface TestAttemptDao {
    @Query("SELECT * FROM test_attempts ORDER BY dateMillis DESC")
    fun getAllAttempts(): Flow<List<TestAttempt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: TestAttempt)
}

@Database(entities = [TestAttempt::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testAttemptDao(): TestAttemptDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "chsos_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
