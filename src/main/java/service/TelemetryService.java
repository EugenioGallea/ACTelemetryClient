package service;

import data.OperationId;
import data.RTCarInfo;
import data.RTLap;
import handshake.HandshakeRequest;
import handshake.HandshakeResponse;
import data.Constants;

import java.io.IOException;
import java.net.*;

public class TelemetryService {
    private HandshakeRequest handshake;
    private DatagramSocket socket;
    private InetAddress address;
    private String separator = "###########################################";

    public TelemetryService() {
         this.handshake = new HandshakeRequest(1, 1, OperationId.HANDSHAKE);
         this.setupSocketInfo();
    }

    private void setupSocketInfo(){
        try {
            this.socket = new DatagramSocket();
            address = InetAddress.getByName(Constants.HOST_NAME);
        } catch (SocketException e) {
           System.err.println("Error in initialization of the socket...");
           e.printStackTrace();
        } catch (UnknownHostException e) {
            System.err.println("Error in resolution of the address...");
            e.printStackTrace();
        }
    }

    private void changeHandshakeDatagramFlags(int identifier, int version, int operationId){
        handshake.setIdentifier(identifier);
        handshake.setVersion(version);
        handshake.setOperationId(operationId);
    }

    public void run() {
        System.out.println(separator);
        connectToACServer();
        try {
            switch(handshake.getOperationId()){
                case OperationId.SUBSCRIBE_UPDATE:
                    this.receiveCarUpdates();
                    break;

                case OperationId.SUBSCRIBE_SPOT:
                    this.receiveCarSpotUpdates();
                    break;

                default:
                    System.err.println("***** Invalid operation id *****");
            }
            receiveCarUpdates();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveCarSpotUpdates() throws IOException {
        byte[] buffer;
        int updateCounter = 0;
        while (!socket.isClosed()) {
            buffer = new byte[RTLap.RT_LAP_SIZE];
            DatagramPacket packetResponse = new DatagramPacket(buffer, buffer.length);
            System.out.println("###\t\t WAITING UPDATE #" + ++updateCounter + " \t\t\t\t###");
            socket.receive(packetResponse);
            RTLap data = new RTLap(buffer);
            System.out.println("###\t\t RECEIVED UPDATE #" + updateCounter + " \t\t\t###");
            System.out.println("#" + updateCounter + " - Response update: " + data.toString());
            System.out.println(separator);
        }
    }

    private void receiveCarUpdates() throws IOException {
        byte[] buffer;
        int updateCounter = 0;
        while (!socket.isClosed()) {
            buffer = new byte[RTCarInfo.RT_CAR_INFO_SIZE];
            DatagramPacket packetResponse = new DatagramPacket(buffer, buffer.length);
            System.out.println("###\t\t WAITING UPDATE #" + ++updateCounter + " \t\t\t\t###");
            socket.receive(packetResponse);
            RTCarInfo data = new RTCarInfo(buffer);
            System.out.println("###\t\t RECEIVED UPDATE #" + updateCounter + " \t\t\t###");
            System.out.println("#" + updateCounter + " - Response update: " + data.toString());
            System.out.println(separator);
        }
    }

    private void connectToACServer() {
        byte[] buffer;
        try {
            // Step 1 -- Una sorta di ping al server per chiedergli di aprire una connesione con noi
            buffer = handshake.getDataAsByte();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.address, Constants.PORT);
            System.out.println("###\t\t STEP 1 HANDSHAKE PHASE \t\t###");
            socket.send(packet);
            buffer = new byte[HandshakeResponse.SIZE];
            DatagramPacket packetResponse = new DatagramPacket(buffer, buffer.length);
            socket.receive(packetResponse);
            HandshakeResponse response = new HandshakeResponse(packetResponse.getData());
            System.out.println("#1 - Response. Received: " + response.toString());
            System.out.println(separator);

            // Step 2 -- Avviso il server su che cosa mi interessa essere aggiornato
            System.out.println("###\t\t STEP 2 SUBSCRIPTION PHASE \t\t###");
            changeHandshakeDatagramFlags(0, 0, OperationId.SUBSCRIBE_UPDATE);
            buffer = handshake.getDataAsByte();
            packet = new DatagramPacket(buffer, buffer.length, this.address, Constants.PORT);
            socket.send(packet);

            // A questo punto sono registrato come un listener
            System.out.println(separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket(){
        this.socket.close();
    }
}
