package com.example.calcimetro;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Set;

public class ListaDispositivos extends ListActivity {

    private BluetoothAdapter mBluetoothAdapter2 = null;

    static String ENDERECO_MAC = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        mBluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> DispositivosPareados= mBluetoothAdapter2.getBondedDevices();

        if(DispositivosPareados.size()>0){
            for (BluetoothDevice Dispositivo: DispositivosPareados){
                String nombreBT = Dispositivo.getName();
                String macBT = Dispositivo.getAddress();
                ArrayBluetooth.add(nombreBT + "\n" + macBT);
            }
        }
        setListAdapter(ArrayBluetooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacion = ((TextView) v).getText().toString();
        Toast.makeText(getApplicationContext(),"Información" + informacion,Toast.LENGTH_LONG).show();

        String endereoMac = informacion.substring(informacion.length()- 17);
        Toast.makeText(getApplicationContext(),"Información" + endereoMac,Toast.LENGTH_LONG).show();

        Intent retornaMac = new Intent ();
        retornaMac.putExtra(ENDERECO_MAC, endereoMac);
        setResult(RESULT_OK, retornaMac);
        finish();
    }
}
