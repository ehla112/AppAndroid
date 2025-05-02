package co.edu.uniminuto.appproject.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import co.edu.uniminuto.appproject.dataaccess.ManagerDataBase;
import co.edu.uniminuto.appproject.entities.Citas;

public class CitaRepository {
    private ManagerDataBase dataBase;
    private View view;

    private Context context;
    private Citas citas;

    public CitaRepository(View view, Context context) {
        this.view = view;
        this.context = context;
        this.dataBase = new ManagerDataBase(context);
    }
    public void insertCita(Citas citas){
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        if(sqLiteDatabase != null){
            ContentValues values = new ContentValues();
            values.put("cit_fecha", citas.getFecha());
            values.put("cit_lugar", citas.getLugar());
            values.put("cit_hora", citas.getHora());
            values.put("cit_descripcion", citas.getDescripcion());
            values.put("cit_status", 1);
            values.put("cit_idMascota", citas.getIdMascota());
            long response = sqLiteDatabase.insert("citas", null, values);
            String message = (response >=1) ? "se registro correctamente" :
                    "no se registro correctamente";
            Snackbar.make(this.view, message, Snackbar.LENGTH_LONG).show();
            sqLiteDatabase.close();


        }

    }
    public ArrayList<Citas> getAllCitas(int idMascota) {
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        ArrayList<Citas> appointments = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE cit_idMascota = ? AND cit_status = 1";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(idMascota)});
        if (cursor.moveToFirst()) {
            do{
                Citas citas = new Citas();
                citas.setIdCita(cursor.getInt(0));
                citas.setFecha(cursor.getString(1));
                citas.setLugar(cursor.getString(2));
                citas.setHora(cursor.getString(3));
                citas.setDescripcion(cursor.getString(4));
                citas.setStatus(cursor.getInt(5));
                citas.setIdMascota(cursor.getInt(6));
                appointments.add(citas);
            }while (cursor.moveToNext());

        } cursor.close();
        return appointments;


    }
    public Citas getCitaByID(int idCita){
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        Citas citas = null;
        String sql = "SELECT * FROM citas WHERE cit_idCita = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(idCita)});
        if(cursor.moveToFirst()){
            citas = new Citas();
            citas.setIdCita(cursor.getInt(0));
            citas.setFecha(cursor.getString(1));
            citas.setLugar(cursor.getString(2));
            citas.setHora(cursor.getString(3));
            citas.setDescripcion(cursor.getString(4));
            citas.setStatus(cursor.getInt(5));
            citas.setIdMascota(cursor.getInt(6));
            cursor.close();
            sqLiteDatabase.close();
        }
        cursor.close();
        sqLiteDatabase.close();
        return citas;

    }
  public  Citas getProximaCita(int idMascota){

        Citas citas = null;
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        String sql = "SELECT * FROM citas WHERE cit_idMascota = ?"+
                " AND cit_status = 1 "+
                "AND date(cit_fecha) >= date('now')"+
                "ORDER BY date(cit_fecha), time(cit_hora)  LIMIT 1";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(idMascota)});

        if(cursor.moveToFirst()){
                 citas = new Citas();
                citas.setIdCita(cursor.getInt(0));
                citas.setFecha(cursor.getString(1));
                citas.setLugar(cursor.getString(2));
                citas.setHora(cursor.getString(3));
                citas.setDescripcion(cursor.getString(4));
                citas.setStatus(cursor.getInt(5));
                citas.setIdMascota(cursor.getInt(6));

            }
        cursor.close();
        sqLiteDatabase.close();
        return citas;



        }





    public void updateCita(Citas citas){
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("cit_fecha", citas.getFecha());
            values.put("cit_lugar", citas.getLugar());
            values.put("cit_hora", citas.getHora());
            values.put("cit_descripcion", citas.getDescripcion());
            int result = sqLiteDatabase.update("citas", values, "cit_idCita = ?", new String[]{String.valueOf(citas.getIdCita())});
            if(result>0){
                Snackbar.make(this.view, "se actualizo correctamente", Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(this.view, "no se actualizo correctamente", Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.i("Error en bases de datos", "insertUser: " +e.getMessage());
        }


    }
    public boolean deleteCita(int idCita){
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        boolean correcto = false;
        try{
            String sql = "UPDATE citas SET cit_status =0  WHERE cit_idCita = ?";
            sqLiteDatabase.execSQL(sql, new Object[]{idCita});
            correcto = true;
            Snackbar.make(this.view, "se elimino correctamente", Snackbar.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.i("Error en bases de datos", "insertUser: " +e.getMessage());
        }
        sqLiteDatabase.close();
        return correcto;
    }

   public ArrayList<Citas> getCitasPasadas(int idMascota){
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        ArrayList<Citas> citas = new ArrayList<>();
        String sql = "SELECT c.*, m.mas_nombre FROM citas c " +
                "JOIN mascotas m ON c.cit_idMascota = m.mas_idMascota " +
                "WHERE c.cit_idMascota = ? " +
                "AND c.cit_status = 1 " +
                "AND date(c.cit_fecha) < date('now') " +
                "ORDER BY date(c.cit_fecha) DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(idMascota)});
        if (cursor.moveToFirst()) {
            do{
                Citas cita = new Citas();
                cita.setIdCita(cursor.getInt(0));
                cita.setFecha(cursor.getString(1));
                cita.setLugar(cursor.getString(2));
                cita.setHora(cursor.getString(3));
                cita.setDescripcion(cursor.getString(4));
                cita.setStatus(cursor.getInt(5));
                cita.setIdMascota(cursor.getInt(6));
                cita.setNombreMascota(cursor.getString(7));
                citas.add(cita);
            }while (cursor.moveToNext());

        }
        cursor.close();
        sqLiteDatabase.close();
        return citas;

   }



}
