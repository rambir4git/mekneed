package com.myapp.mekvahan.Rooms;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myapp.mekvahan.Cart.CartTable;

@Database(entities = {CartTable.class}, version = 1, exportSchema = false)

public abstract class MekVahanDatabase extends RoomDatabase {

    private static final String DB_NAME = "MekVahanDatabase.db";
    private static volatile MekVahanDatabase instance;

    public static synchronized MekVahanDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static MekVahanDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                MekVahanDatabase.class,
                DB_NAME).build();
    }

    public abstract CartDao getCartDao();


}


