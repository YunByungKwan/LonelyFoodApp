package org.ybk.fooddiaryapp.data.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.diary.Diary

@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary WHERE email=:email ORDER BY register_time DESC")
    fun getDiaryAll(email: String): Single<List<Diary>>

    @Query("SELECT * FROM diary WHERE open=:open")
    fun getOpenDiaryAll(open: String): Single<List<Diary>>

    @Query("SELECT * FROM diary WHERE id=:id")
    fun getDiary(id: String): Single<Diary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiaries(diaryList: List<Diary>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(Diary: Diary): Completable

    @Update
    fun updateDiary(diary: Diary): Completable

    @Delete
    fun deleteDiary(diary: Diary): Completable
}