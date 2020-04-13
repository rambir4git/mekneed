package com.myapp.mekvahan.Rooms;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.myapp.mekvahan.Cart.CartTable;

import java.util.List;

@Dao
public interface CartDao {

    @Query("SELECT * FROM CartTable ORDER BY CartTable.id DESC")
    List<CartTable> getAllMyCartItems();

    @Query("SELECT * FROM CartTable WHERE CartTable.id = :id")
    CartTable getCartItemById(int id);

    @Query("SELECT * FROM CartTable WHERE CartTable.serviceId = :id")
    CartTable getCartItemByServiceId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartTable item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CartTable> list);

    @Query("DELETE FROM CartTable WHERE CartTable.serviceId = :id")
    void deleteByServiceId(int id);


    @Query("DELETE FROM CartTable")
    void deleteAll();

    @Query("SELECT COUNT(CartTable.id) FROM CartTable")
    int getRowCount();
}
