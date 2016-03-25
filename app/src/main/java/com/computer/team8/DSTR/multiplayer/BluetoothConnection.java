package com.computer.team8.DSTR.multiplayer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/*
 * Singleton static Bluetooth Communicator
 */
public final class BluetoothConnection {

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
        UNSUPPORTED, BLUETOOTH_DISABLED, NOT_PAIRED, SUCCESS
    }


    public BluetoothConnection(Context c)
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
        if(Connected)
        {
            closeConnection();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(deviceName))
                {
                    mDevice = device;
                    break;
                }

            }
        }

        if(mDevice == null)
        {
            bluetoothResult = Result.NOT_PAIRED;

        }
        else
        {
            bluetoothResult = Result.SUCCESS;
        }

        CreateSerialBluetoothDeviceSocket(mDevice);
        ConnectToSerialBlueToothDevice();
    }

    public static void closeConnection() {
        try {
            mmInStream.close();
            mmInStream = null;
        } catch (IOException e) {
        }
        try {
            mmOutStream.close();
            mmOutStream = null;
        } catch (IOException e) {
        }
        try {
            mmSocket.close();
            mmSocket = null;
        } catch (IOException e) {
        }

        Connected = false;
    }

    public static boolean isConnected()
    {
        return Connected;
    }

    public static void CreateSerialBluetoothDeviceSocket(BluetoothDevice device)
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

    public static void ConnectToSerialBlueToothDevice() {
        try {
            // Attempt connection to the device through the socket.
            mmSocket.connect();
            Toast.makeText(context, "Connection Made", Toast.LENGTH_LONG).show();
            Connected = true;
            bluetoothResult = Result.SUCCESS;
        }
        catch (IOException connectException) {
            Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            return;
        }

        //create the input/output stream and record fact we have made a connection
        GetInputOutputStreamsForSocket(); // see page 26
    }

    // gets the input/output stream associated with the current socket
    private static void GetInputOutputStreamsForSocket() {
        try {
            mmInStream = mmSocket.getInputStream();
            mmOutStream = mmSocket.getOutputStream();
        } catch (IOException e) { }
    }

    //
// This function write a line of text (in the form of an array of bytes)
// to the Bluetooth device and then sends the string “\r\n”
// (required by the bluetooth dongle)
//
    public static void WriteToBTDevice (String message) {
        String s = new String("\r\n") ;
        byte[] msgBuffer = message.getBytes();
        byte[] newline = s.getBytes();

        try {
            mmOutStream.write(msgBuffer) ;
            mmOutStream.write(newline) ;
            Log.i("myApp", "Wrote a message");
        } catch (IOException e) { }
    }

    public static String ReadFromBTDevice() {
        byte c;
        String s = new String("");


        try { // Read from the InputStream using polling and timeout
            for (int i = 0; i < 200; i++) { // try to read for 2 seconds max
                SystemClock.sleep(10);
                if (mmInStream.available() > 0) {
                    if ((c = (byte) mmInStream.read()) != '\r') // '\r' terminator
                        s += (char) c; // build up string 1 byte by byte
                    else
                        return s;
                }
            }
        } catch (IOException e) {
            return new String("-- No Response --");
        }

        return new String("-- No Response --");
    }
}
