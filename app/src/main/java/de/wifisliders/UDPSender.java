package de.wifisliders;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class UDPSender implements Runnable {

    private final SocketAddress address;
    private final Sending sending;
    private final UDPBackend backend;

    public UDPSender(SocketAddress address, Sending sending, UDPBackend udpBackend) {
        this.address = address;
        this.sending = sending;
        backend = udpBackend;
    }

    @Override
    public void run() {
        send();
    }

    public void send() {
        try(DatagramSocket socket = new DatagramSocket(null)) {
            byte[] message = {sending.toSlider, sending.value };

            DatagramPacket packet = new DatagramPacket(message, message.length, address);

            socket.send(packet);

            backend.feedback(sending, true);
            /*
            byte[] receivedMsg = new byte[50];
            DatagramPacket response = new DatagramPacket(receivedMsg, receivedMsg.length);
            socket.setSoTimeout(5000);
            socket.receive(response);

            System.out.println("Received ack:" + receivedMsg[0]);

            switch(receivedMsg[0]) {
                case 1:
                    System.out.println("Generic Error from ESP32");
                case 0:
                    backend.feedback(sending, true);
                    break;
                case 2:
                    backend.feedback(sending, false);
                    break;
            }*/

        } catch(IOException e) {
            System.err.println("Error in Sending or Receiving: ");
            //e.printStackTrace();
            backend.feedback(sending, false);
        }
    }
}
