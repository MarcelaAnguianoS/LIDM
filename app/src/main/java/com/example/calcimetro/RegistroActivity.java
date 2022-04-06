package com.example.calcimetro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    private EditText pass2EditText;
    CheckBox userBox,admBox;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.passEditText);
        pass2EditText = findViewById(R.id.pass2EditText);
        userBox = findViewById(R.id.UsuarioReg);
        admBox = findViewById(R.id.AdmRegistro);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    admBox.setChecked(false);
                }
            }
        });

        admBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    userBox.setChecked(false);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    //Caracteristicas del usuario actual
    private void updateUI(FirebaseUser currentUser) {
        Log.i("User", ""+currentUser);
    }

    public void createUserWhitEmailAndPassword(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            
                            Log.d("ÉXITO", "signInWithCustomToken:success");
                            Toast.makeText(RegistroActivity.this, "USUARIO CREADO.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();

                            // MANDAR A LOGIN
                            Intent intent= new Intent (RegistroActivity.this, MainActivity.class);
                            startActivity(intent);

                            //CERRAR VENTANA
                            finish();
                        } else {
                            
                            Log.w("ERROR", "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(RegistroActivity.this, "ERROR AL CREAR USUARIO.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }
                    }
                });
    }

    public void buttonPress (View view){

        String email = emailEditText.getText().toString();
        String password = passEditText.getText().toString();
        String Repassword = pass2EditText.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()&&!Repassword.isEmpty()){
            if(password.equals(Repassword)){
                if(password.length()>5){
                    if(userBox.isChecked() || admBox.isChecked()) {
                        createUserWhitEmailAndPassword(email,password);
                    }else{
                        Toast.makeText(this, "Seleccionar tipo de usuario", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(this, "La contraseña debe de tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();

    }


}