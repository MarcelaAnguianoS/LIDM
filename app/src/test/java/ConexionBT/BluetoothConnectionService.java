package ConexionBT;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class BluetoothConnectionService extends AppCompatActivity {
    String glucosa="";
    private static final String TAG = "BluetoothConnectionServ";
    private boolean closeconnection=false;
    private static final String appName = "MYAPP";
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private UUID deviceUUID;
    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    Context cContext; //context para hacer startActivity una vez recibido un dato.
    private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private BluetoothDevice mmDevice;
    ProgressDialog mProgressDialog;

    public BluetoothConnectionService(Context context, Context ccontext) {
        mContext = context;
        cContext=ccontext;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
        glucosa="";
    }
    public void setCloseconnection(boolean closeconnection) {
        this.closeconnection = closeconnection;
        Log.e(TAG, "close connection set to true");
    }
    /*Accept Thread actua como servidor
 crea un ServerSocket y en caso que se conecto otro dispositivo
como cliente, recibe un socket diferente al server socket */
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket; //Se crea serverSocket
        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp =
                        mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName,
                                MY_UUID_INSECURE);
                Log.d(TAG, "AcceptThread: Setting up Server using: " +
                        MY_UUID_INSECURE);
            }catch (IOException e){
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
            }
            mmServerSocket = tmp; //El server socket se ha creado correctamente a partir del UUID
        }
        public void run(){
            Log.d(TAG, "run: AcceptThread Running.");
            BluetoothSocket socket = null;
            try{
                Log.d(TAG, "run: AcceptThread RFCOM server socket  start.....");
                socket = mmServerSocket.accept(); //Intenta aceptar comunicacion de parte del cliente. Si se acepta, se recibe un socket a partir del server socket
                Log.d(TAG, "run: AcceptThread RFCOM server socket accepted connection.");
            }catch (IOException e){
                Log.e(TAG, "run AcceptThread: IOException: " +
                        e.getMessage() );
            }
            if(socket != null){
                connected(socket,mmDevice); //Se dirige a connecedThread para la transferencia de datos
            }
            Log.i(TAG, "run mAcceptThread ENDS");
        }
        public void cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
            }
        }
    }
    /*Este thread actua como conexion como Cliente
     * Se crea un socket y si la conexion es aceptada*/
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = uuid;
        }
        public void run(){
            BluetoothSocket tmp = null;
            Log.i(TAG, "RUN mConnectThread ");
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                Log.d(TAG, "run: ConnectThread: Trying to create  InsecureRfcommSocket using UUID: " +MY_UUID_INSECURE );
                tmp =
                        mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.e(TAG, "run: ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }
            mmSocket = tmp;
            // Always cancel discovery because it will slow down a connection
            mBluetoothAdapter.cancelDiscovery();
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
                Log.d(TAG, "run: ConnectThread connected.");
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                    Log.d(TAG, "run: ConnectThread Closed Socket.");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE );
            }
            //will talk about this in the 3rd video
            connected(mmSocket,mmDevice);
        }
        public void cancel() {
            try {
                Log.d(TAG, "cancel: ConnectThread Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: ConnectThread close() of mmSocket failed. " + e.getMessage());
            }
        }
    }
    //Se eliminan los posibles threads existentes para empezar de 0 la comunicacion
    public synchronized void start() {
        Log.d(TAG, "start method begins");
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            Log.d(TAG, "start method removes ConnectThread");
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            Log.d(TAG, "start method removes ConnectedThread");
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }
    public void startClient(BluetoothDevice device,UUID uuid){
        Log.d(TAG, "startClient: Started.");
        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }
    //ConnectThread es el thread final que permite el traspaso de datos.
    private class ConnectedThread extends Thread {
        private BluetoothSocket mmSocket;
        private InputStream mmInStream;
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");
            glucosa="";
            mmSocket = socket; //Se recibe el socket creado previamente por el thread ConnectThread o AcceptThread
            InputStream tmpIn = null;
            try{
                mProgressDialog.dismiss();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            //Se recibe un inputstream a partir del socket recibido que conecta los dos dispositivos
            try {

                tmpIn = mmSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmInStream = tmpIn;
        }
        //Este metodo es el responsable de leer el dato desde el glucometro
        public void run(){
            byte[] buffer = new byte[1024]; // buffer para el stream
            int bytes;
            //Se espera a que exista una interrupción para recuperar los datos
            while (true) {
                if(closeconnection){ //booleano que sirve para parar la comunicacion bluetooth
                    cancel();
                }
                //Lectura de input stream
                try {
                    bytes = mmInStream.read(buffer);
                    String incomingMessage = new String(buffer, 0,
                            bytes);
                    Log.d(TAG, "InputStream: " + incomingMessage);
                    glucosa=glucosa+incomingMessage;
                    Log.d(TAG, "Glucosa: " + glucosa);
                    //La llegada de datos es asincrona. Si el dato recibido es mayor a 50 se considera que el dato ha llegado completo.
                    if(Integer.valueOf(glucosa)>50){
                        try {
                            Intent intent = new Intent(cContext, RegistrarAnalisis.class);
                            intent.putExtra("MedidaSangre", glucosa);
                            cContext.startActivity(intent);
                            glucosa="";
                        } catch (Exception e) {
                            Log.e(TAG, "ConnectedThread_ Error en startActivity Registrar activity. Error: " + e);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "ConnectedThread: Error reading Input Stream. " + e.getMessage());
                    break;
                }
            }
        }
        //Se cancela la comunicación
        public void cancel() {
            Log.d(TAG, "ConnectedThread cancel method: shutdown connection");
            if(mmInStream!=null){
                try {
                    mmInStream.close();
                    Log.d(TAG, "ConnectedThread cancel method: input stream closed");
                } catch (Exception e) {
                    Log.e(TAG, "ConnectedThread cancel method: Error al cerrar input stream. Error: " +e.getMessage());
                }
                mmInStream=null;
                Log.d(TAG, "ConnectedThread cancel method: input stream is now null");
            }
            if(mmSocket!=null){
                try {
                    mmSocket.close();
                    Log.d(TAG, "ConnectedThread cancel method: socket closed");
                } catch (IOException e) {
                    Log.e(TAG, "ConnectedThread cancel method: Error closing socket. Error: " +e.getMessage());
                }
                mmSocket=null;
            }
        }
    }
    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG, "Connected method: Starting.");
        // Start the thread to manage the connection and perform transmissions
                mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }
}