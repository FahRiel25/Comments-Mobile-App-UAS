package id.ac.pnm.comments.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete


import id.ac.pnm.comments.ui.database.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<FavoriteEntity>

    @Insert
    fun insert(vararg favorite: FavoriteEntity)

    @Update
    fun update(favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)
}