package id.ac.pnm.comments.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import id.ac.pnm.comments.database.FavoriteDao

@Database(entities = [FavoriteEntity::class], version=1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
        fun getInstance(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "favorite_database"
                ).fallbackToDestructiveMigration().build().also {INSTANCE = it}
            }
        }
    }
}