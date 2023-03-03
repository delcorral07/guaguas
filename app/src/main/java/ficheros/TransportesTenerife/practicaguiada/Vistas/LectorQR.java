package ficheros.TransportesTenerife.practicaguiada.Vistas;

import static ficheros.TransportesTenerife.practicaguiada.Animaciones.AnimacionesMain.animarLayoutFragments;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.abrirFragmentParada;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.layoutInferior;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.layoutOpciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.Parada;
import com.example.practicaguiada.R;
import com.google.zxing.Result;



public class LectorQR extends AppCompatActivity{


    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qr);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 123);
        } else {
             scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(this, scannerView);

            startScanning(this);
        }

    }

    private void startScanning(Context contexto) {
        try{
            scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(this, scannerView);
        }catch (IllegalStateException ex){}

        mCodeScanner.startPreview();
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String resultado = result.getText();
                        if(resultado.contains("http://movil.titsa.com/infoparada.php?IdParada=")) {
                            resultado = resultado.substring(resultado.lastIndexOf("=")+1);
                            System.out.println(resultado);
                            MapsActivity.layoutFragmentsActivo = animarLayoutFragments(MapsActivity.layouFragments, layoutOpciones,
                                    layoutInferior,MapsActivity.layoutFragmentsActivo, findViewById(R.id.layoutFragments));
                            for (Parada parada : MapsActivity.paradas) {
                                if (parada.getStop_id().equals(resultado)) {
                                    abrirFragmentParada(parada.getStop_id(), parada.getStop_name());
                                }
                            }
                            finish();
                        }else{
                            android.app.AlertDialog.Builder mensaje = new AlertDialog.Builder(contexto);
                            mensaje.setTitle("Código QR incorrecto");
                            mensaje.setMessage("Ups, parece que el código QR leído no corresponde a ninguna parada");
                            mensaje.setPositiveButton("Escanear de nuevo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mCodeScanner.startPreview();
                                    startScanning(contexto);
                                }
                            });
                            mensaje.setNegativeButton("Salir del escáner", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            mensaje.create().show();
                        }

                    }
                });


            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning(this);
            } else {
                Toast.makeText(this, "No se han concedido permisos de cámara", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }
}