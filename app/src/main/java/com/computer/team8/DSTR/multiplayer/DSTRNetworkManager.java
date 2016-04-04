package com.computer.team8.DSTR.multiplayer;

import android.util.Log;

public class DSTRNetworkManager {
    private int dataCounter, delayCounter, writeDelay;
    private final int SLOW_DELAY = 70;
    private final int FAST_DELAY = 14;
    private final int BYTE_INCREMENT = 2;

    public DSTRNetworkManager() {
        dataCounter = 0;
        delayCounter = 0;
        writeDelay = FAST_DELAY;
    }

    public boolean sendMsg(String msg)
    {
        for(int i = 0; i < msg.length(); i++)
        {
            sendChar(msg.charAt(i));

            for(int j = 0; j < 100; j++)
            {
                //rest
            }
        }

        do{
            DSTRBluetooth.connect("DSTR1");
        }while(DSTRBluetooth.bluetoothResult != DSTRBluetooth.Result.SUCCESS);


        return true;
    }

    public boolean sendChar(char c)
    {
        for(int i = 0; i < 1000; i++)
        {
//                Log.i("Data", "Send");

                if(DSTRBluetooth.isConnected())
                {
                    DSTRBluetooth.write("" + c);
                }

        }

        for(int i = 0; i < 1000; i++) {
              //   Log.i("Data", "End");

            if (DSTRBluetooth.isConnected()) {
                DSTRBluetooth.write("$");

            }
        }

        return true;
    }

    public boolean sendMessage(String mesg, String speed) {
        // set message sending speed
        switch (speed) {
            case "fast":
                writeDelay = FAST_DELAY;
                break;
            case "slow":
                writeDelay = SLOW_DELAY;
                break;
            default:
                writeDelay = FAST_DELAY;
                break;
        }

        if (!DSTRBluetooth.isConnected()) {
            delayCounter = 0;
            dataCounter = 0;
            return true;
        }

        ++delayCounter;

        if (delayCounter > writeDelay && DSTRBluetooth.isConnected()) {
            if (mesg.length() - dataCounter > BYTE_INCREMENT) {
                DSTRBluetooth.write("" +
                        mesg.charAt(dataCounter) +
                        mesg.charAt(dataCounter + 1));
            } else if (dataCounter + 1 < mesg.length()) {
                DSTRBluetooth.write("" + mesg.charAt(dataCounter));
            }

            delayCounter = 0;
            dataCounter += BYTE_INCREMENT;

            if (dataCounter >= mesg.length()) {
                delayCounter = 0;
                dataCounter = 0;
                return true;
            }
        }

        return false;
    }

    public void sendComplete() {
        if (!DSTRBluetooth.isConnected()) {
            return;
        }

        DSTRBluetooth.write("$$$$");
    }

    public String getMessage() {
        String message = DSTRBluetooth.read();
        if (message != null) {
            return message;
        } else {
            return "";
        }
    }
}
