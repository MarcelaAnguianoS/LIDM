package com.example.calcimetro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    private Button btnEnviar;
    private TextView nombre;
    private EditText txtMensaje;
    private RecyclerView rvMensajes;
    private AdapterMensajes adapter;
    private FirebaseDatabase database;
    private DatabaseReference dataBaseReference;
    //private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.barchat);
        setSupportActionBar(toolbar);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);
        txtMensaje = (EditText) findViewById(R.id.txtMensajes);
        rvMensajes = (RecyclerView)findViewById(R.id.rvMensajes);
        nombre =(TextView)findViewById(R.id.nombre);

        //final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        //nombre.setText(user.getDisplayName());

        database = FirebaseDatabase.getInstance();
        dataBaseReference = database.getReference("Chat");//Sala de chat (nombre)


        adapter = new AdapterMensajes(this);
       LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseReference.push().setValue(new itemActivity(txtMensaje.getText().toString(),nombre.getText().toString(),"1","00:00"));
               //adapter.addMensaje(new itemActivity(txtMensaje.getText().toString(),nombre.getText().toString(),"1","00:00"));
                txtMensaje.setText(""); //limpiar el cuadro de mensaje
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

      dataBaseReference.addChildEventListener(new ChildEventListener(){


          @Override
          public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              itemActivity m = snapshot.getValue(itemActivity.class);
              adapter.addMensaje(m);
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot snapshot) {

          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });





    }

    private  void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }



}