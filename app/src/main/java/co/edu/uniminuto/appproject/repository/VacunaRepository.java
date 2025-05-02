package co.edu.uniminuto.appproject.repository;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.edu.uniminuto.appproject.entities.Vacuna;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniminuto.appproject.dataaccess.ManagerDataBase;
import co.edu.uniminuto.appproject.entities.Vacuna;



public class VacunaRepository {
    private ManagerDataBase dbHelper;


    public VacunaRepository(Context context) {
        dbHelper = new ManagerDataBase(context);
    }

    public boolean insertarVacuna(Vacuna vacuna) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("vac_nombre", vacuna.getNombre());
        values.put("vac_fecha", vacuna.getFecha());
        values.put("vac_tipo", vacuna.getTipo());
        values.put("vac_status", vacuna.getStatus() ? 1 : 0);
        values.put("vac_idMascota", vacuna.getIdMascota());

        long result = db.insert("vacunas", null, values);
        return result != -1;
    }


    public boolean actualizarVacuna(int idVacuna, String nombre, String fecha, String tipo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("vac_nombre", nombre);
        valores.put("vac_fecha", fecha);
        valores.put("vac_tipo", tipo);

        int filasAfectadas = db.update(
                "vacunas",
                valores,
                "vac_id = ?",
                new String[]{String.valueOf(idVacuna)}
        );


        return filasAfectadas > 0;
    }

    public boolean eliminarVacuna(int idVacuna) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filasAfectadas = db.delete("vacunas", "vac_id = ?", new String[]{String.valueOf(idVacuna)});
        return filasAfectadas > 0;
    }

    public List<Vacuna> searchVName(String buscandoName) {
        List<Vacuna> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT vac_id, vac_nombre, vac_fecha, vac_tipo, vac_status FROM vacunas WHERE vac_nombre LIKE ?",
                new String[]{"%" + buscandoName + "%"}
        );
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("vac_id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("vac_nombre"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("vac_fecha"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("vac_tipo"));
                boolean status = cursor.getInt(cursor.getColumnIndexOrThrow("vac_status")) == 1;
                Vacuna vacuna = new Vacuna(id, nombre, fecha, tipo, status);
                lista.add(vacuna);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }///search name finnn

    public List<Vacuna> obtenerVacunas() {
        List<Vacuna> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT vac_id, vac_nombre, vac_fecha, vac_tipo, vac_status FROM vacunas", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("vac_id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("vac_nombre"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("vac_fecha"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("vac_tipo"));
                boolean status = cursor.getInt(cursor.getColumnIndexOrThrow("vac_status")) == 1;
                Vacuna vacuna = new Vacuna(id, nombre, fecha, tipo, status);
                lista.add(vacuna);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

}

