package com.example.alta_empleados;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.alta_empleados.Registro;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    EditText nombre1, apellido1, num_empleado, num_segurosocial, rfc1, curp1, fecha_nac;
    Button btn_registrar;
    ImageView img_calendario;

    //FIREBASE;
    FirebaseAuth mAuth;
    DatabaseReference users;
    Dialog myDialog;

    Registro registro;
    Calendar calendar;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FIREBASE CONEXION
        users = FirebaseDatabase.getInstance().getReference().child("Registro");
        mAuth = FirebaseAuth.getInstance();
        myDialog = new Dialog(this);
        calendar = Calendar.getInstance();

        nombre1 = findViewById(R.id.nombre);
        apellido1 = findViewById(R.id.apellido);
        num_empleado = findViewById(R.id.numero_empleado);
        num_segurosocial = findViewById(R.id.numero_segurosocial);
        rfc1 = findViewById(R.id.rfc_usuario);
        curp1 = findViewById(R.id.curp_usuario);
        fecha_nac = findViewById(R.id.fecha_nac);
        img_calendario = findViewById(R.id.img_calendar);

        btn_registrar = findViewById(R.id.btn_registrar);

        registro = new Registro();

        img_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year= calendar.get(Calendar.YEAR);


                dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mDay, int mMonth,int mYear) {
                        mMonth = mMonth + 1;
                        fecha_nac.setText(mDay + "/" + mMonth +"/"+ mYear);
                    }
                }, day, month + 1,year);
                dpd.show();
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }

            private void registerUser() {
                final String nombre = nombre1.getText().toString().trim();
                final String apellido = apellido1.getText().toString().trim();
                final String numero_empleado = num_empleado.getText().toString().trim();
                final String numero_segurosocial = num_segurosocial.getText().toString().trim();
                final String rfc = rfc1.getText().toString().trim();
                final String curp = curp1.getText().toString().trim();
                final String fecha_nacimiento = fecha_nac.getText().toString().trim();

                if (TextUtils.isEmpty(nombre)) {
                    Toast.makeText(MainActivity.this, "Se debe ingresa un nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(apellido)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar Un Apellido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(numero_empleado )) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar Un Numero de Empleado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (numero_empleado.length() !=6) {
                    Toast.makeText(MainActivity.this, "El numero de empleado debe ser de 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(numero_segurosocial)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar Seguro Social", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (numero_segurosocial.length() != 11) {
                    Toast.makeText(MainActivity.this, "El numero de seguro social debe ser de 11 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rfc)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar RFC ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rfc.length() != 13) {
                    Toast.makeText(MainActivity.this, "El RFC debe ser de 13 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(curp)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar CURP", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (curp.length() != 18) {
                    Toast.makeText(MainActivity.this, "El CURP debe ser de 18 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fecha_nacimiento)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                }

                registro.setNombre(nombre);
                registro.setApellido(apellido);
                registro.setNumero_empleado(numero_empleado);
                registro.setNumero_segurosocial(numero_segurosocial);
                registro.setRfc(rfc);
                registro.setCurp(curp);
                registro.setFecha_nacimiento(fecha_nacimiento);

                users.push().setValue(registro);
                Toast.makeText(MainActivity.this, "Alta Empleado", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
