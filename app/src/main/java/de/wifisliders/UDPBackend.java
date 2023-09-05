package de.wifisliders;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class UDPBackend {
    private final SocketAddress address;

    private final List<Sending> sendingList = new ArrayList<>(100);

    public final int MAX_TRIES = 5;

    public UDPBackend(String host, int port) {
        address = new InetSocketAddress(host, port);
    }

    public void sendPacket(Sending sending) {
        //System.out.println("Hello " + sending);
        sendingList.add(sending);
        //Start Worker to send packet
        UDPSender udpSender = new UDPSender(address, sending, this);

        Thread thread = new Thread(udpSender);
        thread.start();
    }

    public void sendPacket(int toSlider, int value) {
        sendPacket(new Sending((byte) toSlider, (byte) value));
    }

    public void feedback(Sending sending, boolean successful) {
        sendingList.remove(sending);
        if(successful)
            return;

        if(sending.tries > MAX_TRIES) {
            //MainActivity.MakeStaticToast("Aborted package (" + sending.toString() + ") after " + MAX_TRIES);
            System.err.println("Aborted package (" + sending + ") after " + MAX_TRIES);
            return;
        }

        boolean again = true;
        for (Sending s : sendingList) {
            if(s.toSlider != sending.toSlider)
                continue;
            if(s.time.after(sending.time)) {
                again = false;
                break;
            }
        }

        if(again) {
            sending.tries++;
            sendPacket(sending);
        }
    }
}
