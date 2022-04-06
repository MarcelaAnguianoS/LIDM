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

public class MainActivity extends AppCompatActivity {

    private EditText emailLogin;
    private EditText passLogin;
    CheckBox userlogin,admlogin;
    private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailLogin = findViewById(R.id.emailLogin);
        passLogin = findViewById(R.id.passLogin);
        userlogin = findViewById(R.id.UserLogin);
        admlogin = findViewById(R.id.AdmLogin);
        mAuth = FirebaseAuth.getInstance();
        userlogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    admlogin.setChecked(false);
                }
            }
        });
        admlogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    userlogin.setChecked(false);
                }
            }
        });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "EL USUARIO INICIO SESION", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "EL USUARIO CERRO SESION", Toast.LENGTH_SHORT).show();
                }
            }
        };
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                if (user.isEmailVerified()) {
                    Toast.makeText(this, "Correo no verificado", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {
    }
    public void signInWithEmailAndPassword(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG","SignInWhitEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            if (user != null) {
                                if (user.isEmailVerified()) {
                                    Toast.makeText(MainActivity.this, "Usuario verificado", Toast.LENGTH_SHORT).show();
                                    //ENVIAR A LA VISTA
                                    if (userlogin.isChecked() == true) {
                                        Intent intent= new Intent (MainActivity.this, UserActivity.class);
                                        startActivity(intent);


                                    }
                                    if (admlogin.isChecked() == true) {
                                        Intent intent= new Intent (MainActivity.this, AdmActivity.class);
                                        startActivity(intent);



                                    }
                                    finish();//cerrar ventana actual
                                } else
                                    Toast.makeText(MainActivity.this, "Usuario no verificado", Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            Log.w("ERROR","signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Error al intentar ingresar", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    public void buttonPressL(View view){
        String email = emailLogin.getText().toString();
        String password = passLogin.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            if(password.length()>5){
                if(userlogin.isChecked() || admlogin.isChecked()) {
                    signInWithEmailAndPassword(email, password);
                }else
                    Toast.makeText(this, "Selecciona tipo de usuario", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
    }
    public void recuperarcontraseña (View view1) {
        Intent intent = new Intent(MainActivity.this, RecContraActivity.class);
        startActivity(intent);
    }
    public void registro (View view2){
        Intent intent= new Intent (MainActivity.this, RegistroActivity.class);
        startActivity(intent);
    }
}