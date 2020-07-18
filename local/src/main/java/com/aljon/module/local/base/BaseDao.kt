package com.aljon.module.local.base

import androidx.room.*

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: MutableList<T>): List<Long>

    @Update
    fun update(obj: T)

    @Transaction
    fun insertOrUpdate(obj: T) {
        val id = insert(obj)
        if (id == -1L) update(obj)
    }

    @Delete
    fun delete(obj: T)
}
