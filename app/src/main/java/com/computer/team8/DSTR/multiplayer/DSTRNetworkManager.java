package com.computer.team8.DSTR.multiplayer;

public class DSTRNetworkManager {
    private int dataCounter, delayCounter, writeDelay;
    private final int WRITE_DELAY = 14;
    private final int BYTE_INCREMENT = 2;

    public DSTRNetworkManager() {
        dataCounter = 0;
        delayCounter = 0;
        writeDelay = WRITE_DELAY;
    }

    public boolean sendMessage(String mesg) {
        if (!DSTRBluetooth.isConnected()) {
            return false;
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
                dataCounter = 0;
            }
            return true;
        } else {
            return false;
        }
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
