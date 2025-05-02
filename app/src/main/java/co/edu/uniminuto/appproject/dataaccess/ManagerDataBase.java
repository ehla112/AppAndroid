package co.edu.uniminuto.appproject.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ManagerDataBase extends SQLiteOpenHelper {
    private static final String DATA_BASE = "appProject.db";
    private static final int VERSION= 5;
    private static final String TABLE_USERS ="users";
    private static final String TABLE_MASCOTAS = "mascotas";
    private static final String TABLE_CITAS = "citas";
    private static final String TABLE_VACUNAS = "vacunas";

    public ManagerDataBase(@Nullable Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(use_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "use_user varchar(35), " +
                "use_name varchar(200), " +
                "use_password varchar(200)," +
                " use_email varchar(35), " +
                "use_phone varchar(35), " +
                "use_address varchar(35)," +
                " use_status INTEGER (1))";
        db.execSQL(CREATE_TABLE_USERS);
        final String CREATE_TABLE_MASCOTAS = "CREATE TABLE " + TABLE_MASCOTAS +  "(mas_idMascota INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mas_nombre varchar(35), " +
                "mas_tipo varchar(200), " +
                "mas_edad INTEGER," +
                " mas_raza varchar(35), " +
                "mas_peso INTEGER, " +
                "mas_status INTEGER (1),"+
                "mas_idDueno INTEGER,"
                +"FOREIGN KEY (mas_idDueno) REFERENCES users(use_id))";

        db.execSQL(CREATE_TABLE_MASCOTAS);
        final String CREATE_TABLE_CITAS = "CREATE TABLE " + TABLE_CITAS +  "(cit_idCita INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cit_fecha varchar(35), " +
                "cit_lugar varchar(200), " +
                "cit_hora varchar(35)," +
                "cit_descripcion varchar(255), "+
                " cit_status INTEGER (1),"+
                "cit_idMascota INTEGER,"
                +"FOREIGN KEY (cit_idMascota) REFERENCES mascotas(mas_idMascota))";
        db.execSQL(CREATE_TABLE_CITAS);
        final String CREATE_TABLE_VACUNAS = "CREATE TABLE " + TABLE_VACUNAS +  "(vac_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "vac_nombre TEXT, " +
                "vac_fecha TEXT, " +
                "vac_tipo TEXT, " +
                "vac_status INTEGER, " +
                "vac_idMascota INTEGER, " +
                "FOREIGN KEY (vac_idMascota) REFERENCES mascotas(mas_idMascota))";

        db.execSQL(CREATE_TABLE_VACUNAS);



}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASCOTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACUNAS);
        onCreate(db);

    }
}
