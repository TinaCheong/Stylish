package student.tina.stylish.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CartItems::class], version = 2, exportSchema = false)
abstract class CartItemsDatabase : RoomDatabase(){

    abstract val cartItemsDatabaseDao : CartItemsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CartItemsDatabase? = null

        fun getInstance(context: Context): CartItemsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CartItemsDatabase::class.java,
                        "cart_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}