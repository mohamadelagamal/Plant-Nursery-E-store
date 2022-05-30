package com.database.room.dao

import androidx.room.*
import com.database.room.entity.Category
import java.util.*

@Dao
interface CategoryDao {
    //..make insertion
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByReplacement(image: List<Category>)
    @Update
    fun updateCategory(category: Category)
    @Delete
    fun deleteCategory(category: Category)
    @Query("select * from Category")
    fun getCategories():List<Category>
}