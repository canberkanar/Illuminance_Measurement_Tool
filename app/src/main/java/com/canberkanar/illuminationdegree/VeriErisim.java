package com.canberkanar.illuminationdegree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class VeriErisim {
    private SQLiteDatabase db;
    private final Context context;
    private final com.canberkanar.illuminationdegree.VeriErisimYardimcisi dbYardimcisi;
    public static String ROOMID_KOLONU = "_id";
    public static String ROOMNAME_KOLONU = "OdaAdi";
    public static String ROOMMININTENSITY_KOLONU = "MinAydinlikLumen";
    public static String ROOMMAXINTENSITY_KOLONU = "MaxAydinlikLumen";
    public static String TABLO_ADI = "ROOM";


    public VeriErisim(Context c) {
        context = c;
        dbYardimcisi = new VeriErisimYardimcisi(context);

    }

    public void veritabaniniKapat (){
        db.close();
    }

    public void veritabaninaBaglan () throws SQLiteException {
        try {
            db = dbYardimcisi.getWritableDatabase();
        }catch (SQLiteException ex){
            db = dbYardimcisi.getReadableDatabase();
        }
    }

    public long AddRoom (Room newRoom){
        try {
            ContentValues yeniContentValue = new ContentValues();
            yeniContentValue.put(ROOMNAME_KOLONU, newRoom.name);
            yeniContentValue.put(ROOMMININTENSITY_KOLONU, newRoom.minIntensity);
            yeniContentValue.put(ROOMMAXINTENSITY_KOLONU, newRoom.maxIntensity);


            return db.insert(TABLO_ADI, null, yeniContentValue);
        }catch (SQLiteException ex){
            return -1;
        }
    }

    public Room GetRoomByName (String roomName){
        Cursor mCursor = db.query(TABLO_ADI, new String[] {ROOMNAME_KOLONU, ROOMMININTENSITY_KOLONU, ROOMMAXINTENSITY_KOLONU}, ROOMNAME_KOLONU
                + "= " + "'"+ roomName + "'",null,null,null,null);

        String name = null;
        int minIntensity = -1;
        int maxIntensity = -1;

        if (mCursor.moveToFirst()){
                name = mCursor.getString(mCursor.getColumnIndex(ROOMNAME_KOLONU));
                minIntensity = mCursor.getInt(mCursor.getColumnIndex(ROOMMININTENSITY_KOLONU));
                maxIntensity = mCursor.getInt(mCursor.getColumnIndex(ROOMMAXINTENSITY_KOLONU));
        }

        return new Room(name, minIntensity, maxIntensity);
    }

    public Cursor getRoomsAllColumns (){
        return db.query(TABLO_ADI, new String[] {ROOMID_KOLONU, ROOMNAME_KOLONU, ROOMMININTENSITY_KOLONU, ROOMMAXINTENSITY_KOLONU}, null ,null, null, null , null);
    }

    public Cursor getRoomsIdsNames (){
        return db.query(TABLO_ADI, new String[] {ROOMID_KOLONU, ROOMNAME_KOLONU}, null ,null, null, null , null);
    }

    public long UpdateRoom(String originalRoomName, Room room){

        ContentValues cv = new ContentValues();
        cv.put(ROOMNAME_KOLONU, room.name);
        cv.put(ROOMMININTENSITY_KOLONU, room.minIntensity);//Variables to be Updated
        cv.put(ROOMMAXINTENSITY_KOLONU, room.maxIntensity);

        return db.update(TABLO_ADI, cv , ROOMNAME_KOLONU + "=?",  new String[] {originalRoomName});
    }


    public long DeleteRoom (String roomName){
        return db.delete(TABLO_ADI, ROOMNAME_KOLONU + "=?", new String[] {String.valueOf(roomName)});
    }
}
