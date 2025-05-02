package co.edu.uniminuto.appproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import co.edu.uniminuto.appproject.entities.Mascotas;
import co.edu.uniminuto.appproject.repository.MascotasRepository;

public class dataPetsActivity extends AppCompatActivity {
    private Context context;
    private int idDueno;
    private EditText etNameMascotaUp;
    private EditText etTipoUp;
    private EditText etEdad;
    private EditText etRaza;
    private EditText etPeso;
    private EditText etStatus;
    private EditText etIdMascota;
    private ListView lvDataPets;
    private Button btnAddMascota;
    private int idMascota;
    private String nombreMascota;
    private String tipoMascota;
    private String edad;
    private String raza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_pets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        begin();

        this.lvDataPets.setOnItemClickListener(this::setOnItemClick);
        btnAddMascota.setOnClickListener(this::addMascota);



    }
    private void addMascota(View view){
        Intent intent = new Intent(this, AddMascotaActivity.class);
        intent.putExtra("idDueno", idDueno);
        startActivity(intent);

    }
    private void setOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        Mascotas selected = (Mascotas) parent.getItemAtPosition(position);
        idMascota = selected.getIdMascota();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones");
        builder.setItems(new String[]{"Actualizar", "Eliminar"}, (dialog, which) -> {
            if (which == 0) {
                PopUpActualizar(view);
            } else if (which == 1) {
                eliminarMascota(view);


            }

        });
        builder.show();



    }

    private void PopUpActualizar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_actualizar, null);
        EditText etNameMascotaUp = dialogView.findViewById(R.id.etNameMascotaUp);
        EditText etTipoUp = dialogView.findViewById(R.id.etTipoUp);
        EditText etRazaUp = dialogView.findViewById(R.id.etRazaUp);
        EditText etEdadUp = dialogView.findViewById(R.id.etEdadUp);
        EditText etPesoUp = dialogView.findViewById(R.id.etPesoUp);
        builder.setView(dialogView);
        // Obtener los datos de la mascota seleccionada
        MascotasRepository mascotasRepository = new MascotasRepository(view, context);
        Mascotas selected = mascotasRepository.getMascotaByID(idMascota);
        etNameMascotaUp.setText(selected.getNombreMascota());
        etTipoUp.setText(selected.getTipoMascota());
        etRazaUp.setText(selected.getRaza());
        etEdadUp.setText(selected.getEdad());
        etPesoUp.setText(String.valueOf(selected.getPeso()));
        builder.setView(dialogView);
        builder.setCancelable(false);

        builder.setPositiveButton("Actualizar", (dialog, which) -> {


            Mascotas mascotas = new Mascotas();
            mascotas.setIdMascota(idMascota);
            mascotas.setNombreMascota(etNameMascotaUp.getText().toString());
            mascotas.setTipoMascota(etTipoUp.getText().toString());
            mascotas.setRaza(etRazaUp.getText().toString());
            mascotas.setEdad(etEdadUp.getText().toString());
            mascotas.setPeso(Double.parseDouble(etPesoUp.getText().toString()));
            mascotasRepository.updateMascotas(mascotas);
            listData(view);
            Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_LONG).show();

        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();






    }

    private void listData(View view){
        idDueno= getIntent().getIntExtra("idDueno", -1);

        if(idDueno != -1){
            MascotasRepository mascotasRepository = new MascotasRepository(view,context);

            ArrayList<Mascotas> list = mascotasRepository.getAllMascotas(idDueno);

            if(list.size()>0){
                ArrayAdapter<Mascotas> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                lvDataPets.setAdapter(arrayAdapter);

            }else{
                Toast.makeText(this, "No se encontraron mascotas", Toast.LENGTH_LONG).show();
            }


        }else{
            Toast.makeText(this, "id no valido", Toast.LENGTH_LONG).show();
        }


    }
    private  void eliminarMascota(View view) {
        MascotasRepository mascotasRepository = new MascotasRepository(view, context);
        boolean correcto = mascotasRepository.deleteMascotas(idMascota);
        if (correcto) {
            listData(view);
            Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_LONG).show();

        }



    }


    private void begin() {
        this.context = this;
        this.etNameMascotaUp = findViewById(R.id.etNameMascotaUp);
        this.etTipoUp = findViewById(R.id.etTipoUp);
        this.etEdad = findViewById(R.id.etEdad);
        this.etRaza = findViewById(R.id.etRazaUp);
        this.etPeso = findViewById(R.id.etPesoUp);
        this.idMascota = getIntent().getIntExtra("idMascota", 0);
        this.lvDataPets = findViewById(R.id.lvDataPets);
        this.btnAddMascota = findViewById(R.id.btnAddMascota);
        listData(findViewById(R.id.main));





    }
}