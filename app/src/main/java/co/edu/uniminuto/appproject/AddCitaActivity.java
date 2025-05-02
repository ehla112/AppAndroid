package co.edu.uniminuto.appproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import co.edu.uniminuto.appproject.entities.Citas;
import co.edu.uniminuto.appproject.repository.CitaRepository;

public class AddCitaActivity extends AppCompatActivity {
    private Context context;

    private EditText etFecha;
    private EditText etLugar;
    private EditText etHora;
    private EditText etDescripcion;
    private Button btnAddCita;
    private int year;
    private int month;
    private int day;
    private String fecha;
    private String lugar;
    private String hora;
    private String descripcion;
    private int hour;
    private int minute;
    private int status;
    private int idMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_cita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciar();
        pickDate();
        pickTime();
        btnAddCita.setOnClickListener(this::addCita);

    }
    private void pickDate(){
        etFecha.setOnClickListener(view -> {
            Calendar calendar =Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day =calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,(view1, year1, month1, dayOfMonth) -> {
                String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, month1 + 1, dayOfMonth);
                etFecha.setText(date);
            },year,month,day);
            datePickerDialog.show();
        });

        }
        private void pickTime(){
        etHora.setOnClickListener(view-> {
            Calendar calendar = Calendar.getInstance();
            hour =  calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            boolean isPm = calendar.get(Calendar.AM_PM) == Calendar.AM;
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + minute1;
                etHora.setText(time);
            },hour,minute,false);
            timePickerDialog.show();

        });

    }
        private void addCita(View view){
        capData();
        idMascota = getIntent().getIntExtra("idMascota", -1);
        if(idMascota != -1){
            Citas citas = new Citas(fecha, lugar, hora, descripcion,1  , idMascota);
            CitaRepository citasRepository = new CitaRepository(view,context);
            citasRepository.insertCita(citas);
            Toast.makeText(this, "Cita agregada", Toast.LENGTH_SHORT).show();
            finish();

        }else {
            Toast.makeText(this, "No se pudo obtener el ID de la mascota", Toast.LENGTH_SHORT).show();
        }





        }
        private void capData(){
        this.fecha = etFecha.getText().toString();
        this.lugar = etLugar.getText().toString();
        this.hora = etHora.getText().toString();
        this.descripcion = etDescripcion.getText().toString();


        }


    private void iniciar() {
        this.context = this;
        this.etFecha = findViewById(R.id.etFecha);
        this.etLugar = findViewById(R.id.etLugar);
        this.etHora = findViewById(R.id.etHora);
        this.etDescripcion = findViewById(R.id.etDescripcion);
        this.btnAddCita = findViewById(R.id.btnAddCita);

    }
}