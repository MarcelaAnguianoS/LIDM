package com.example.calcimetro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecContraActivity extends AppCompatActivity {

    private EditText email;
    private Button recupe;

    private String email2 ="";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_contra);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.RecEditText);
        recupe = (Button) findViewById(R.id.button4);

        recupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email2 = email.getText().toString();

                if(!email2.isEmpty()){
                    resetContraseña();
                }else{
                    Toast.makeText(RecContraActivity.this, "Ingresar el correo registrado", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void resetContraseña(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RecContraActivity.this, "Correo enviado", Toast.LENGTH_SHORT).show();

                    // MANDAR A LOGIN
                    Intent intent= new Intent (RecContraActivity.this, MainActivity.class);
                    startActivity(intent);

                    //CERRAR VENTANA
                    finish();
                }else{
                    Toast.makeText(RecContraActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}