package org.ybk.fooddiaryapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.ybk.fooddiaryapp.data.model.etc.DataConverter
import org.ybk.fooddiaryapp.data.model.diary.Diary

@Database(entities = [Diary::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class DiaryDatabase: RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {
        private var INSTANCE: DiaryDatabase? = null

        fun getInstance(context: Context): DiaryDatabase? {
            if (INSTANCE == null) {
                synchronized(DiaryDatabase::class) {
                    INSTANCE = databaseBuilder(context.applicationContext,
                        DiaryDatabase::class.java, "diary.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}