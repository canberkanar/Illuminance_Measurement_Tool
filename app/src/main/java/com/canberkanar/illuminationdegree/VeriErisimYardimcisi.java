package com.canberkanar.illuminationdegree;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class VeriErisimYardimcisi extends SQLiteOpenHelper {
    public static String VERITABANI_ADI = "IlluminationDegreeMeasurement.db";
    public static int VERITABANI_VERSIYONU = 2;

    public VeriErisimYardimcisi(Context context) {
        super(context, VERITABANI_ADI, null, VERITABANI_VERSIYONU);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ROOM( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OdaAdi TEXT NOT NULL," + "MinAydinlikLumen DOUBLE NOT NULL," +
                "MaxAydinlikLumen DOUBLE NOT NULL);");

        fillDatabaseWithDefaultRecords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ROOM");
        onCreate(db);
    }

    public void fillDatabaseWithDefaultRecords(SQLiteDatabase db){

        ContentValues myDefaultValues = new ContentValues();

        myDefaultValues.put("OdaAdi", "General Purpose");
        myDefaultValues.put("MinAydinlikLumen", 300);
        myDefaultValues.put("MaxAydinlikLumen", 500);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Kitchen");
        myDefaultValues.put("MinAydinlikLumen", 300);
        myDefaultValues.put("MaxAydinlikLumen", 750);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Bedroom");
        myDefaultValues.put("MinAydinlikLumen", 200);
        myDefaultValues.put("MaxAydinlikLumen", 300);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Reading");
        myDefaultValues.put("MinAydinlikLumen", 500);
        myDefaultValues.put("MaxAydinlikLumen", 1000);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Corridor");
        myDefaultValues.put("MinAydinlikLumen", 50);
        myDefaultValues.put("MaxAydinlikLumen", 100);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Gym");
        myDefaultValues.put("MinAydinlikLumen", 200);
        myDefaultValues.put("MaxAydinlikLumen", 300);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Classroom");
        myDefaultValues.put("MinAydinlikLumen", 500);
        myDefaultValues.put("MaxAydinlikLumen", 750);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Laboratory");
        myDefaultValues.put("MinAydinlikLumen", 750);
        myDefaultValues.put("MaxAydinlikLumen", 1200);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Office");
        myDefaultValues.put("MinAydinlikLumen", 300);
        myDefaultValues.put("MaxAydinlikLumen", 500);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Parking Lot");
        myDefaultValues.put("MinAydinlikLumen", 50);
        myDefaultValues.put("MaxAydinlikLumen", 100);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Restroom");
        myDefaultValues.put("MinAydinlikLumen", 100);
        myDefaultValues.put("MaxAydinlikLumen", 300);

        db.insert("ROOM", null, myDefaultValues);

        myDefaultValues.put("OdaAdi", "Stairway");
        myDefaultValues.put("MinAydinlikLumen", 50);
        myDefaultValues.put("MaxAydinlikLumen", 100);

        db.insert("ROOM", null, myDefaultValues);


    }
}
