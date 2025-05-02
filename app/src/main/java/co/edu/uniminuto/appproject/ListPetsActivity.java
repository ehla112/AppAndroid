package co.edu.uniminuto.appproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import co.edu.uniminuto.appproject.entities.Mascotas;
import co.edu.uniminuto.appproject.repository.MascotasRepository;

public class ListPetsActivity extends AppCompatActivity {
    private Context context;
    private int idDueno;
    private ListView lvListaMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_pets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponentes();
        recibirIntent();
        validarYListarMascotas();
        configurarClickLista();
    }
    private void iniciarComponentes() {
        context = this;
        lvListaMascotas = findViewById(R.id.lvListaMascotas);
    }

    private void recibirIntent() {
        idDueno = getIntent().getIntExtra("idDueno", -1);
    }

    private void validarYListarMascotas() {
        if (idDueno != -1) {
            cargarMascotas();
        } else {
            mostrarMensaje("ID de dueño inválido");
        }
    }

    private void cargarMascotas() {
        MascotasRepository mascotasRepository = new MascotasRepository(lvListaMascotas, context);
        ArrayList<Mascotas> listaMascotas = mascotasRepository.getAllMascotas(idDueno);

        if (listaMascotas != null && !listaMascotas.isEmpty()) {
            ArrayAdapter<Mascotas> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, listaMascotas
            );
            lvListaMascotas.setAdapter(adapter);
        } else {
            mostrarMensaje("No se encontraron mascotas para este dueño");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
    private void configurarClickLista() {
        lvListaMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mascotas mascotaSeleccionada = (Mascotas) parent.getItemAtPosition(position);

                // Mostrar Toast
                Toast.makeText(ListPetsActivity.this,
                        "Seleccionaste: " + mascotaSeleccionada.getNombreMascota(),
                        Toast.LENGTH_SHORT).show();

                // Navegar a ActivityVacunas
                Intent intent = new Intent(ListPetsActivity.this, ActivityVacunas.class);
                intent.putExtra("idMascota", mascotaSeleccionada.getIdMascota());
                startActivity(intent);
            }
        });
    }

}



