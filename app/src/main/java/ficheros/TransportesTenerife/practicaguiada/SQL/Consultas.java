package ficheros.TransportesTenerife.practicaguiada.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.EstacionarioBusqueda;

import java.io.Serializable;
import java.util.ArrayList;

public class Consultas implements Serializable {



    public static boolean insertarParada(String codParada, String nombreParda, SQL db) {
        SQLiteDatabase ddbb = db.getWritableDatabase();
        try {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codParada);
            registro.put("nombre", nombreParda);
            ddbb.insert(SQL.TABLA_PARADAS, null, registro);
            System.out.println("inserta");
            ddbb.close();
            return true;
        } catch (SQLiteException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean comprobarParadaRegistrada(String codParada, String nombreParda,SQL db) {
        SQLiteDatabase ddbb = db.getWritableDatabase();
        try {
            Cursor cur = ddbb.rawQuery("select codigo from "
                    + SQL.TABLA_PARADAS + "  where codigo='"+codParada+"'", null);
            boolean result = cur.moveToFirst();
            cur.close();
            db.close();
            return result;
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean insertarGuagua(String codGuagua, String nombreGuagua, SQL db) {
        SQLiteDatabase ddbb = db.getWritableDatabase();
        try {
            ContentValues registro = new ContentValues();
            registro.put("numero", codGuagua);
            registro.put("recorrido", nombreGuagua);
            ddbb.insert(SQL.TABLA_GUAGUAS, null, registro);
            ddbb.close();
            System.out.println("inserta guagua");
            return true;
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /*
    Devuelve true si existe
     */
    public static boolean comprobarGuagua(String codGuagua, String nombreGuagua, SQL db) {
        SQLiteDatabase ddbb = db.getWritableDatabase();
        try {
            Cursor cursor = ddbb.rawQuery("select numero from "
                    + SQL.TABLA_GUAGUAS + "  where  numero='"+codGuagua+"'", null);
            if (cursor.moveToFirst()) {
                if (cursor.getString(0).equals(codGuagua)) {
                    cursor.close();
                    ddbb.close();
                    return true;
                }
                while (cursor.moveToNext()) {
                    if (cursor.getString(0).equals(codGuagua)) {
                        cursor.close();
                        ddbb.close();
                        return true;
                    }
                }
            }
            cursor.close();
            ddbb.close();
            return false;
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static ArrayList<EstacionarioBusqueda> recogerEstacionariosUser(SQL database) {
        ArrayList<EstacionarioBusqueda> listaEstacionarios = new ArrayList<>();
        SQLiteDatabase ddbb = database.getWritableDatabase();
        try {
            Cursor cursor = ddbb.rawQuery("select numero,recorrido from "
                    + SQL.TABLA_GUAGUAS , null);
            if (cursor.moveToFirst()) {
                EstacionarioBusqueda estacionario = new EstacionarioBusqueda( cursor.getString(0),
                    cursor.getString(1),"Linea",true);
                listaEstacionarios.add(estacionario);
                while (cursor.moveToNext()) {
                    estacionario = new EstacionarioBusqueda(cursor.getString(0),
                        cursor.getString(1),"Linea",true);
                    listaEstacionarios.add(estacionario);
                }
            }
            cursor = ddbb.rawQuery("select codigo,nombre from "
                    + SQL.TABLA_PARADAS, null);
            if (cursor.moveToFirst()) {
                EstacionarioBusqueda estacionario = new EstacionarioBusqueda(cursor.getString(0),
                        cursor.getString(1),"Parada",false);
                listaEstacionarios.add(estacionario);
                while (cursor.moveToNext()) {
                    estacionario = new EstacionarioBusqueda(cursor.getString(0),cursor.getString(1),
                            "Parada",false);
                    listaEstacionarios.add(estacionario);
                }
            }
            cursor.close();
            ddbb.close();
            return listaEstacionarios;
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean eliminarGuagua(String codGuagua, SQL database){
        SQLiteDatabase ddbb = database.getWritableDatabase();
        try {
            ddbb.delete(SQL.TABLA_GUAGUAS,"numero='"+codGuagua+"'",null);
            ddbb.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean eliminarParada(String codParada, SQL database){
        SQLiteDatabase ddbb = database.getWritableDatabase();
        try {
            ddbb.delete(SQL.TABLA_PARADAS,"codigo='"+codParada+"'",null);
            ddbb.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }
}
