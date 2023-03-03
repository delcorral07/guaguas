package ficheros.TransportesTenerife.practicaguiada.Data;


import android.content.Context;

import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.Guagua;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.Parada;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.PuntosVenta;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;

import com.example.practicaguiada.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class lecturaFicheros {

    public static final String ficheroSerializado = "ficheroSerializado.dat";

    public static List<Parada> leerParadas(Context context){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(context.getResources().openRawResource(R.raw.stops)));
            return gson.fromJson(br, new TypeToken<List<Parada>>(){}.getType());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Guagua> leerGuaguas(Context contexto){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(contexto.getResources().openRawResource(R.raw.routes)));
            return gson.fromJson(br, new TypeToken<List<Guagua>>(){}.getType());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static ArrayList<PuntosVenta> leerFicheroVentas(Context context){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(context.getResources().openRawResource(R.raw.puntos_venta)));
            return gson.fromJson(br, new TypeToken<List<PuntosVenta>>(){}.getType());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean existeSerializado(Context c){
        boolean creados = true;
        try
        {
            File fichero = new File(c.getFilesDir().getParentFile()+File.separator+ficheroSerializado);
            if(!fichero.exists()){
                fichero.createNewFile();
                return false;
            }else{
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creados;
    }

    public static void serializarArray(List[] array,Context c){
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(c.getFilesDir()+File.separator+ficheroSerializado));
            oos.writeObject(array);
            oos.close();
            System.out.println("\n\n\n\tFICHEROS GUARDADOS\n\n");
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println("\n\n\n\tHA OCURRIDO UN ERROR\n\n");
        }
    }


    public static void deserializarFichero(Context c){
        List[] lista = new List[3];
        try
        {
            ObjectInputStream oos = new ObjectInputStream(
                    new FileInputStream(c.getFilesDir()+File.separator+ficheroSerializado));
            lista = (List[]) oos.readObject();
            oos.close();
            MapsActivity.paradas = lista[0];
            MapsActivity.puntosVentas = lista[1];
            MapsActivity.listaEstacionarios = lista[2];
            System.out.println("\n\n\n\tFICHEROS CARGADOS\n\n");
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println("\n\n\n\tHA OCURRIDO UN ERROR DESERIALIZANDO\n\n");
        }
    }

    public static void tareasDeActualizacion(Context c){
        try{
            File ficheroSer = new File(c.getFilesDir()+File.separator+ficheroSerializado);
            if(ficheroSer.delete()){
                System.out.println("\n\n\nFICHERO SERIALIZADO ELIMINADO CON EXITO\n\n\n");
            }else{
                System.out.println("\n\n\nNO SE HA PODIDO ELIMINAR EL FICHERO EN CUESTION\n\n\n\n");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }




}


