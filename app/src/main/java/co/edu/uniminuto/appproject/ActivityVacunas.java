package co.edu.uniminuto.appproject;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import co.edu.uniminuto.appproject.entities.Vacuna;
import co.edu.uniminuto.appproject.repository.VacunaRepository;


public class ActivityVacunas extends AppCompatActivity {
    private EditText etFechaVacuna;
    private EditText etNombreVacuna;
    private EditText etTipoVacuna;
    private Button btnAddvacuna;
    private Button btnActualizarVacuna;
    private Button btnEliminarVacuna;
    private ListView lvVacunas;
    private int vacunaSeleccionadaId = -1;

    private EditText etDiasRecientes;
    private Button btnBuscarRecientes;
    private Button btnBuscarVacuna;
    private int idMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacunas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponentes();
        configurarEventos();
        configurarBotonActualizar();
        configurarBotonAgregar(1);
        configurarBotonEliminar();
        lvVacunas = findViewById(R.id.lvVacunas);
        cargarVacunas();
        buscarVpornombre();
        buscarVporDias();
        idMascota = getIntent().getIntExtra("idMascota", 1);
    }

    private void iniciarComponentes() {
        etNombreVacuna = findViewById(R.id.etNombreVacuna);
        etFechaVacuna = findViewById(R.id.etFechaVacuna);
        etTipoVacuna = findViewById(R.id.etTipoVacuna);
        btnAddvacuna = findViewById(R.id.btnAddvacuna);
        btnActualizarVacuna = findViewById(R.id.btnActualizarVacuna);
        btnEliminarVacuna = findViewById(R.id.btnEliminarVacuna);
        btnBuscarVacuna = findViewById(R.id.btnBuscarVacuna);
        etDiasRecientes = findViewById(R.id.etDiasRecientes);
        btnBuscarRecientes = findViewById(R.id.btnBuscarRecientes);
        lvVacunas = findViewById(R.id.lvVacunas);
    }


    private void configurarEventos() {
        etFechaVacuna.setOnClickListener(v -> mostrarCalendario());
    }

   /* private int obtenerIdVacunaSeleccionada() {
        return 1;
    }*/

    private void limpiarCampos() {
        etNombreVacuna.setText("");
        etFechaVacuna.setText("");
        etTipoVacuna.setText("");
        vacunaSeleccionadaId = -1;
    }


    private void mostrarCalendario() {
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    etFechaVacuna.setText(fechaSeleccionada);
                },
                ano, mes, dia
        );
        datePickerDialog.show();
    }

    private void configurarBotonAgregar(int idMascota) {
        btnAddvacuna.setOnClickListener(v -> {
            String nombre = etNombreVacuna.getText().toString();
            String fecha = etFechaVacuna.getText().toString();
            String tipo = etTipoVacuna.getText().toString();

            if (nombre.isEmpty() || fecha.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }
            Vacuna vacuna = new Vacuna(0, nombre, fecha, tipo, idMascota, true);
            VacunaRepository repo = new VacunaRepository(this);
            boolean insertado = repo.insertarVacuna(vacuna);
            if (insertado) {
                Toast.makeText(this, "Vacuna registrada correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                cargarVacunas();
            } else {
                Toast.makeText(this, "Error al registrar la vacuna", Toast.LENGTH_SHORT).show();
            }
        });
    }///fin codigo agregar vacuna
    ///
    private void cargarVacunas() {
        VacunaRepository repo = new VacunaRepository(this);
        List<Vacuna> lista = repo.obtenerVacunas();
        mostrarVacunasEnListView(lista);
    }

    private void configurarBotonActualizar() {
        btnActualizarVacuna.setOnClickListener(v -> {
            String nombre = etNombreVacuna.getText().toString();
            String fecha = etFechaVacuna.getText().toString();
            String tipo = etTipoVacuna.getText().toString();

            if (nombre.isEmpty() || fecha.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (vacunaSeleccionadaId != -1) {
                VacunaRepository repo = new VacunaRepository(this);
                boolean actualizado = repo.actualizarVacuna(vacunaSeleccionadaId, nombre, fecha, tipo);
                if (actualizado) {
                    Toast.makeText(this, "Vacuna actualizada correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    cargarVacunas();
                } else {
                    Toast.makeText(this, "Error al actualizar la vacuna", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Selecciona una vacuna primero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarVpornombre() {
        VacunaRepository repo = new VacunaRepository(this);
        btnBuscarVacuna.setOnClickListener(v -> {
            String nombre = etNombreVacuna.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Ingrese un nombre de vacuna", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Vacuna> lista = repo.searchVName(nombre);
            mostrarVacunasEnListView(lista);
        });
    }

    private void buscarVporDias() {
        VacunaRepository repo = new VacunaRepository(this);
        btnBuscarRecientes.setOnClickListener(v -> {
            String diasStr = etDiasRecientes.getText().toString().trim();
            if (diasStr.isEmpty()) {
                Toast.makeText(this, "Ingrese cantidad de días", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int dias = Integer.parseInt(diasStr);
                long ahora = System.currentTimeMillis();
                long limite = ahora - dias * 24L * 60 * 60 * 1000;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                List<Vacuna> filtradas = new ArrayList<>();
                for (Vacuna vacuna : repo.obtenerVacunas()) {
                    Date fecha = sdf.parse(vacuna.getFecha());
                    if (fecha.getTime() >= limite) {
                        filtradas.add(vacuna);
                    }
                }
                mostrarVacunasEnListView(filtradas);
            } catch (Exception e) {
                Toast.makeText(this, "Error al procesar la fecha", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    private void configurarBotonEliminar() {
        btnEliminarVacuna.setOnClickListener(v -> {
            if (vacunaSeleccionadaId != -1) {
                VacunaRepository repo = new VacunaRepository(this);
                boolean eliminado = repo.eliminarVacuna(vacunaSeleccionadaId);
                if (eliminado) {
                    Toast.makeText(this, "Vacuna eliminada", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    vacunaSeleccionadaId = -1;
                    cargarVacunas();
                } else {
                    Toast.makeText(this, "Error al eliminar la vacuna", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Selecciona una vacuna primero", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mostrarVacunasEnListView(List<Vacuna> listaVacunas) {
        List<String> datos = new ArrayList<>();
        for (Vacuna v : listaVacunas) {
            datos.add("Nombre: " + v.getNombre() + "\nFecha: " + v.getFecha() + "\nTipo: " + v.getTipo());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        lvVacunas.setAdapter(adapter);

        lvVacunas.setOnItemClickListener((parent, view, position, id) -> {

            Vacuna vacuna = listaVacunas.get(position);
            etNombreVacuna.setText(vacuna.getNombre());
            etFechaVacuna.setText(vacuna.getFecha());
            etTipoVacuna.setText(vacuna.getTipo());
            vacunaSeleccionadaId = vacuna.getId();
        });
    }


}//fin codigo