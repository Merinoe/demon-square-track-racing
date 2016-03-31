package com.computer.team8.DSTR.multiplayer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public final class DSTRBluetooth {

    private static int REQUEST_ENABLE_BT = 1;
    private static BluetoothSocket mmSocket = null;
    public static InputStream mmInStream = null;
    public static OutputStream mmOutStream = null;
    private static boolean Connected = false;
    private static BluetoothAdapter mBluetoothAdapter;
    private static ArrayList< String > myDiscoveredDevicesStringArray = new ArrayList < String > ( ) ;
    private static BluetoothDevice mDevice = null;
    private static Context context;
    public static Result bluetoothResult = null;


    public enum Result{
        UNSUPPORTED, BLUETOOTH_DISABLED, FAIL, NOT_PAIRED, SUCCESS
    }


    public DSTRBluetooth(Context c)
    {
        this.context = c;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            bluetoothResult = Result.UNSUPPORTED;
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            bluetoothResult = Result.BLUETOOTH_DISABLED;
        }
    }

    public static void attachContext(Context c)
    {
        context = c;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            bluetoothResult = Result.UNSUPPORTED;
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            bluetoothResult = Result.BLUETOOTH_DISABLED;
        }
    }

    public static void connect(String deviceName)
    {
        if(Connected) {
            close();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().equals(deviceName)) {
                    mDevice = device;
                    break;
                }
            }
        }

        if(mDevice == null) {
            bluetoothResult = Result.NOT_PAIRED;
            return;

        }

        generateBluetoothSocket(mDevice);
        connectSockets();
    }

    public static void close() {
        try {
            mmInStream.close();
            mmInStream = null;
        } catch (IOException e) {
            System.out.println("Bluetooth InStream failed to close");
        }
        try {
            mmOutStream.close();
            mmOutStream = null;
        } catch (IOException e) {
            System.out.println("Bluetooth OutStream failed to close");
        }
        try {
            mmSocket.close();
            mmSocket = null;
        } catch (IOException e) {
            System.out.println("Bluetooth Socket failed to close");
        }

        Connected = false;
    }

    public static boolean isConnected()
    {
        return Connected;
    }

    public static void generateBluetoothSocket(BluetoothDevice device)
    {
        mmSocket = null;

        // universal UUID for a serial profile RFCOMM blue tooth device
        // this is just one of those “things” that you have to do and just works
        UUID MY_UUID = UUID.fromString ("00001101-0000-1000-8000-00805F9B34FB");
        // Get a Bluetooth Socket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            mmSocket = device.createRfcommSocketToServiceRecord (MY_UUID);
        }
        catch (IOException e) {
            Toast.makeText(context, "Socket Creation Failed", Toast.LENGTH_LONG).show();
        }
    }

    public static void connectSockets() {
        try {
            // Attempt connection to the device through the socket.
            mmSocket.connect();
            bluetoothResult = Result.SUCCESS;
            Connected = true;
        } catch (IOException connectException) {
            bluetoothResult = Result.FAIL;
            return;
        }

        //create the input/output stream and record fact we have made a connection
        generateIOSockets(); // see page 26
    }

    // gets the input/output stream associated with the current socket
    private static void generateIOSockets() {
        try {
            mmInStream = mmSocket.getInputStream();
            mmOutStream = mmSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Bluetooth Sockets failed to instantiate");
        }
    }

    // This function write a line of text (in the form of an array of bytes)
    // to the Bluetooth device and then sends the string “\r\n”
    // (required by the bluetooth dongle)
    public static void write(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            mmOutStream.write(msgBuffer, 0, msgBuffer.length) ;
            mmOutStream.flush();
        } catch (IOException e) {
            System.out.println("Could not write to Bluetooth");
        }
    }

    public static String read() {
        byte c;
        String s = new String("");


        try { // Read from the InputStream using polling and timeout
            for (int i = 0; i < 200; i++) { // try to read for 2 seconds max
                if (mmInStream.available() > 0) {
                    if ((c = (byte) mmInStream.read()) != '\r') // '\r' terminator
                        s += (char) c; // build up string 1 byte by byte
                    else
                        return s;
                }
            }
        } catch (IOException e) {
            return "Unable to Communicate with DE2";
        }

        return "";
    }
}
