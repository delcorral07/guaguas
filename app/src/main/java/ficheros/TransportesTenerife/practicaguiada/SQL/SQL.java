package ficheros.TransportesTenerife.practicaguiada.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQL extends SQLiteOpenHelper {

    public static final String NOMBRE_BBDD = "db.guaguas";
    public static final int VERSION_BBDD = 5;
    public static final String TABLA_AJUSTES = "t_ajustes";
    public static final String TABLA_GUAGUAS = "t_guaguas";
    public static final String TABLA_PARADAS = "t_paradas";

    public SQL(@Nullable Context context){
        super(context,NOMBRE_BBDD,null,VERSION_BBDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE "+TABLA_GUAGUAS+"(" +
                    "numero text not null, " +
                    "recorrido text not null)");
            db.execSQL("CREATE TABLE "+TABLA_PARADAS+"(" +
                    "codigo text not null," +
                    "nombre text not null)");
            db.execSQL("CREATE TABLE "+TABLA_AJUSTES+"(" +
                    "tema int not null)");
        }catch (SQLiteException e){}


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table "+TABLA_AJUSTES);
        db.execSQL("drop table "+TABLA_GUAGUAS);
        db.execSQL("drop table "+TABLA_PARADAS);
        onCreate(db);
    }
}
