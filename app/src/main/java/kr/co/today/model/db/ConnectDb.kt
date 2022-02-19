package kr.co.today.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Database(
        entities = [MetaDataEntities::class],
        version = 1
)
abstract class OhmyAppDatabase: RoomDatabase(){
    abstract fun metaDao(): RoomMetaData
    companion object{
        private var INSTANCE: OhmyAppDatabase? = null

        fun getInstance(context: Context, useInMemory: Boolean): OhmyAppDatabase {
            if (INSTANCE == null) {
                synchronized(OhmyAppDatabase::class) {
                    INSTANCE = if (useInMemory) {
                        Room.inMemoryDatabaseBuilder(context.applicationContext, OhmyAppDatabase::class.java)
                                .build()
                    } else {
                        Room.databaseBuilder(
                                context.applicationContext,
                                OhmyAppDatabase::class.java,
                                "ohmyapp.db"
                        )
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}

fun <T> RoomDatabase.runFromIOObserveMain(callable: () -> T): Single<T> {
    return Single.fromCallable(callable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> RoomDatabase.runFromIO(callable: () -> T): Single<T> {
    return Single.fromCallable(callable)
            .subscribeOn(Schedulers.io())
}