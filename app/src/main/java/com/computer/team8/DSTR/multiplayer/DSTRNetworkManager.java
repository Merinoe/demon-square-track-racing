package com.computer.team8.DSTR.multiplayer;

public class DSTRNetworkManager {
    private int dataCounter, delayCounter, writeDelay;
    private final int INITIAL_WRITE_DELAY = 8;
    private final int BYTE_INCREMENT = 2;

    public DSTRNetworkManager() {
        dataCounter = 0;
        delayCounter = 0;
        writeDelay = INITIAL_WRITE_DELAY;
    }

    public void sendMessage(String mesg) {
        ++delayCounter;

        if (delayCounter >= writeDelay && DSTRBluetooh.isConnected()) {
            if (mesg.length() - dataCounter > BYTE_INCREMENT) {
                DSTRBluetooh.write("" +
                        mesg.charAt(dataCounter) +
                        mesg.charAt(dataCounter + 1));
            } else if (dataCounter + 1 < mesg.length()) {
                DSTRBluetooh.write("" + mesg.charAt(dataCounter));
            }

            delayCounter = 0;

            dataCounter += BYTE_INCREMENT;
            if (dataCounter >= mesg.length()) {
                dataCounter = 0;
            }
        }
    }

    public String getMessage() {
        String message = DSTRBluetooh.read();
        if (message != null) {
            return message;
        } else {
            return "";
        }
    }
}
