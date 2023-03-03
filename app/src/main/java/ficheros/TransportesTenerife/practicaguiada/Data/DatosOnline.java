package ficheros.TransportesTenerife.practicaguiada.Data;

import android.os.StrictMode;

import ficheros.TransportesTenerife.practicaguiada.POJO.Avisos.Avisos;
import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Itinerario;
import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Puntos;
import ficheros.TransportesTenerife.practicaguiada.POJO.TiempoRestante.Llegada;
import ficheros.TransportesTenerife.practicaguiada.POJO.TiempoRestante.Llegadas;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.FragmentNoticias;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DatosOnline {

    //Devuelve el itinerario, siendo Puntos las paradas que tiene la guagua pedida
    public static Itinerario itinerarioIda(String linea, int n) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        boolean centinela = false;
        Itinerario iti = new Itinerario();
        if(n == 0) {
            try {
                URL url = new URL("https://titsa.com/ajax/xItinerario.php?c=1234&id_linea=" + linea + "&id_trayecto=11");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                Gson gson = new Gson();
                urlConnection.disconnect();

                iti = gson.fromJson(bf, Itinerario.class);
                centinela = true;
                System.out.println(iti);
            } catch (IOException | JsonSyntaxException e){
                return null;
            }
            if(iti == null){
                iti = itinerarioVenida(linea,1);
            }
            return iti;

        }else{
            return null;
        }

    }


    //Devuelve el itinerario, siendo Puntos las paradas que tiene la guagua pedida
    public static Itinerario itinerarioVenida(String linea, int n) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Itinerario iti = new Itinerario();
        if(n == 0){
            try{
                URL url = new URL("https://titsa.com/ajax/xItinerario.php?c=1234&id_linea="+linea+"&id_trayecto=12");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        new BufferedInputStream(urlConnection.getInputStream())));
                urlConnection.disconnect();
                iti = new Gson().fromJson(bf, Itinerario.class);
            } catch (IOException e) {
                return null;
            } catch (NullPointerException ex){
                iti = itinerarioIda(linea,1);
            }
            return iti;
        }else{
            return null;
        }

    }

    //Devuelve el tiempo restante para que las guaguas lleguen a un punto
    public static Llegadas tiempo(String numeroParada) {
        Llegadas llegadas = new Llegadas();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://apps.titsa.com/apps/apps_sae_llegadas_parada.asp?IdApp=6275b428fb9f6735bdd94a5775832699&idParada=" + numeroParada);
            InputStream is = url.openStream();
            DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbc.newDocumentBuilder();
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("llegada");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Llegada l = new Llegada();
                    Element eElement = (Element) node;
                    l.setCodigoParada(eElement.getElementsByTagName("codigoParada").item(0).getTextContent());
                    l.setDenominacion(eElement.getElementsByTagName("denominacion").item(0).getTextContent());
                    l.setDestinoLinea(eElement.getElementsByTagName("destinoLinea").item(0).getTextContent());
                    l.setHora(eElement.getElementsByTagName("hora").item(0).getTextContent());
                    l.setIdTrayecto(eElement.getElementsByTagName("idTrayecto").item(0).getTextContent());
                    l.setLinea(eElement.getElementsByTagName("linea").item(0).getTextContent());
                    l.setMinutosParaLlegar(eElement.getElementsByTagName("minutosParaLlegar").item(0).getTextContent());
                    llegadas.getEntradas().add(l);
                }
            }
            return llegadas;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Devuelve los enlaces a las diferentes páginas de avisos
    public static ArrayList<Avisos> avisos(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<Avisos> ultimoAvisos = new ArrayList<>();
                org.jsoup.nodes.Document doc = null;
                try {
                    doc = Jsoup.connect("https://titsa.com/").get();
                    Elements avisos = doc.select(".wrapper .item");
                    for(org.jsoup.nodes.Element elemento : avisos){
                        Avisos nuevoAviso = new Avisos();
                        nuevoAviso.setFechas(elemento.getElementsByTag("h5").text());
                        nuevoAviso.setTitulo(elemento.getElementsByTag("h4").text());
                        nuevoAviso.setUrl(elemento.getElementsByTag("a").attr("href"));
                        ultimoAvisos.add(nuevoAviso);
                    }
                    FragmentNoticias.noticiasCargadas = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
       return ultimoAvisos;
    }
}


