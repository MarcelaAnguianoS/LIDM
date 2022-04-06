package com.example.calcimetro;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.calcimetro.fragments.PerfilFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class UserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.barchat);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        Configuration config = new Configuration (getResources().getConfiguration());
        if (id == R.id.idioma1) {


            String languageToLoad = "fr";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }
        if (id == R.id.idioma2){
            config.locale = Locale.UK;
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

            /*String languageToLoad = "en";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());*/

        }
        if(id == R.id.perfil){
            Intent intent = new Intent(this, PerfilFragment.class);
            startActivity(intent);

        }
        if(id == R.id.chat){
            Intent intent = new Intent(this, Chat.class);
            startActivity(intent);

        }

        if (id == R.id.cerrar){
            FirebaseAuth.getInstance().signOut();
            this.finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public void onChat (View nota){
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);

    }


}