package com.example.sopie.fruits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sopie on 28/10/15.
 */
public class DBAdapter extends SQLiteOpenHelper {

    private static final String DB_NAME = "fruit";
    private static final String TABEL_NAME = "buah";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "nama";
    private static final String COL_WARNA = "warna";
    private static final String COL_JENIS = "jenis";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS"+TABEL_NAME+";";

    private SQLiteDatabase sqLiteDatabase = null;

    public DBAdapter(Context context){

        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTable(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
    }

    public void createTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABEL_NAME+"("+ COL_ID
                +"INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+ COL_NAME
                +"TEXT,"+COL_Warna+" TEXT");
    }
    public void openDB(){
        if (sqLiteDatabase == null){
            sqLiteDatabase = getWritableDatabase();
        }
    }
    public void closeDB(){
        if (sqLiteDatabase!= null) {
            if (sqLiteDatabase.isOpen()){
                sqLiteDatabase.close();
            }
        }
    }



    public void save(Buah Buah){
        sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, Buah.getNama());
        contentValues.put(COL_WARNA, Buah.getWarna());
        contentValues.put(COL_JENIS, Buah.getJenis());

        sqLiteDatabase.insertWithOnConflict(TABEL_NAME, null,
                contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        sqLiteDatabase.close();
    }

    public void updateBuah(Buah Buah){
        sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, Buah.getNama());
        cv.put(COL_WARNA, Buah.getWarna());
        cv.put(COL_JENIS, Buah.getJenis());
        String whereClause = COL_ID +"==?";
        String whereArgs[] = new String[] { Buah.getId() };
        sqLiteDatabase.update(TABEL_NAME, cv, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void delete(Buah Buah){
        sqLiteDatabase = getWritableDatabase();
        String whereClause = COL_ID +"==?";
        String whereArgs[] = new String[] {String.valueOf(Buah.getId()) };
        sqLiteDatabase.delete(TABEL_NAME, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void deleteAll() {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABEL_NAME, null, null);
        sqLiteDatabase.close();
    }

    public List<Buah> getAllBuah(){
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = this.sqLiteDatabase.query(TABEL_NAME, new String[] {
                COL_ID, COL_NAME, COL_Warna}, null, null, null, null, null);
        List<Buah> Buahs = new ArrayList<Buah>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Buah Buah = new Buah();
                Buah.setId(cursor.getString(cursor.getColumnIndex(COL_ID)));
                Buah.setNama(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                Buah.setWarna(cursor.getString(cursor.getColumnIndex(COL_WARNA)));
                Buah.setJenis(cursor.getString(cursor.getColumnIndex(COL_JENIS)));
                Buahs.add(Buah);
            }
            sqLiteDatabase.close();
            return Buahs;
        }else {
            sqLiteDatabase.close();
            return new ArrayList<Buah>();
        }
    }

}

