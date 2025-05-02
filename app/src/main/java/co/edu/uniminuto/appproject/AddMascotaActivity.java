package co.edu.uniminuto.appproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.edu.uniminuto.appproject.entities.Mascotas;
import co.edu.uniminuto.appproject.repository.MascotasRepository;
import co.edu.uniminuto.appproject.repository.UserRepository;

public class AddMascotaActivity extends AppCompatActivity {
    private Context context;
    private EditText etNombreMascota;
    private EditText etTipoMascota;
    private EditText etEdad;
    private EditText etRaza;
    private EditText etPeso;
    private Button btnSaveMascota;
    private int id;
    private String nombreMascota;
    private String tipoMascota;
    private String edad;
    private String raza;
    private double peso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_mascota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inicio();
        btnSaveMascota.setOnClickListener(this::saveMascota);
    }
    private void saveMascota(View view){
        capData();
        int idDueno = getIntent().getIntExtra("idDueno", -1);
        if(idDueno != -1){
            Mascotas mascotas = new Mascotas(nombreMascota, tipoMascota, edad, raza, peso, 1,idDueno);
            MascotasRepository mascotasRepository = new MascotasRepository(view, context);
            mascotasRepository.insertMascotas(mascotas);

            Intent intent = new Intent(this, ActivityHome.class);
            intent.putExtra("idDueno", idDueno);
            startActivity(intent);
            finish();


        }else{
            Toast.makeText(this, "No se pudo obtener el ID del dueño", Toast.LENGTH_SHORT).show();
        }


    }

    private void capData(){
        this.nombreMascota = etNombreMascota.getText().toString();
        this.tipoMascota = etTipoMascota.getText().toString();
        this.edad = etEdad.getText().toString();
        this.raza = etRaza.getText().toString();
        this.peso = Double.parseDouble(etPeso.getText().toString());
    }

    private void inicio() {
        this.context = this;
        this.etNombreMascota = findViewById(R.id.etNombreMascota);
        this.etTipoMascota = findViewById(R.id.etTipoMascota);
        this.etEdad = findViewById(R.id.etEdad);
        this.etRaza = findViewById(R.id.etRazaUp);
        this.etPeso = findViewById(R.id.etPeso);
        this.btnSaveMascota = findViewById(R.id.btnSaveMascota);

    }

}