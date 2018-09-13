package it.polito.s241876.client.ac_interaction.service;

import it.polito.s241876.client.ac_interaction.data.OperationId;
import it.polito.s241876.client.ac_interaction.data.RTCarInfo;
import it.polito.s241876.client.ac_interaction.data.RTLap;
import it.polito.s241876.client.ac_interaction.protocol.HandshakeResponse;
import it.polito.s241876.client.ac_interaction.protocol.Request;
import it.polito.s241876.client.ac_interaction.utils.ConnectionInfo;
import it.polito.s241876.server.service.CarInfoManager;

import java.io.IOException;
import java.net.*;

/**
 * This is the class responsible for the connection with Assetto Corsa's server opened socket.
 */
public class TelemetryService {
    private DatagramSocket socket;
    private InetAddress address;
    private CarInfoManager infoManager;
    private static final String TAG = "[TelemetryService] ";

    public TelemetryService() {
        this.setupSocketInfo();
        this.infoManager = CarInfoManager.getInstance();
    }

    /**
     * This function has to setup the information of the socket: hostname, port.
     */
    private void setupSocketInfo() {
        try {
            this.socket = new DatagramSocket();
            address = InetAddress.getByName(ConnectionInfo.HOST_NAME);
        } catch (SocketException e) {
            System.err.println(TAG + "Error in initialization of the socket...");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.err.println(TAG + "Error in resolution of the address...");
            e.printStackTrace();
        }
    }

    /**
     * Main function. It will connect to the AC server, and then call a function to receive the
     * type of updates we have subscribed to.
     *
     * @param subscriptionType: what kind of update we want to subscribe for
     */
    public void run(int subscriptionType) {
        try {
            connectToACServer(subscriptionType); // Connect to the server
            switch (subscriptionType) {
                case OperationId.SUBSCRIBE_SPOT: // Update after each lap
                    this.receiveCarSpotUpdates();
                    break;

                case OperationId.SUBSCRIBE_UPDATE: // Constant update on the car situation
                    this.receiveCarUpdates();
                    break;

                default:
                    System.err.println(TAG + "Unrecognized subscription type");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to connect to AC server.
     *
     * @param subscriptionType: what kind of update we're willing to receive
     */
    private void connectToACServer(int subscriptionType) {
        try {
            // Step 1 -- HANDSHAKE PHASE
            this.subscribe(OperationId.HANDSHAKE);

            // Step 2 -- SUBSCRIPTION PHASE
            this.subscribe(subscriptionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to receive car spot updates, i.e. at each lap
     *
     * @throws IOException : thrown when something went wrong with the socket
     */
    private void receiveCarSpotUpdates() throws IOException {
        byte[] buffer;
        //int updateCounter = 0;
        while (!socket.isClosed()) {
            buffer = new byte[RTLap.RT_LAP_SIZE];
            DatagramPacket packetResponse = new DatagramPacket(buffer, buffer.length);
            socket.receive(packetResponse);
            //RTLap data = new RTLap(buffer);
            //System.out.println(TAG + updateCounter + " - Response update: " + data.toString());
        }
    }

    /**
     * Function called when we want to receive constant updates on the vehicle. Here I just have to receive the updates
     * coming from the server, nothing else.
     *
     * @throws IOException : thrown when something went wrong with the socket
     */
    private void receiveCarUpdates() throws IOException {
        byte[] buffer;
        //int updateCounter = 0;
        while (!socket.isClosed()) {
            buffer = new byte[RTCarInfo.RT_CAR_INFO_SIZE];
            DatagramPacket packetResponse = new DatagramPacket(buffer, buffer.length);
            socket.receive(packetResponse);
            RTCarInfo data = new RTCarInfo(packetResponse.getData());
            if (data.getIdentifier() != 'a') { // 'a' as identifier means correct response received
                this.unsubscribe(); // Remove this application from the list of listeners
                this.closeSocket(); // Close the socket
            } else { // Everything ok
                //System.out.println(TAG + ++updateCounter + " - Response update: " + data.toString());
                this.infoManager.evaluateCarInfo(data);
            }
        }
    }

    /**
     * Function that simply close the opened socket
     */
    private void closeSocket() {
        this.socket.close();
    }

    /**
     * Function called to subcribe as listener to assetto corsa server for a certain type of subscription
     *
     * @param subscriptionType : the type of subscription UPDATE or SPOT
     * @throws IOException : thrown when something went wrong with the socket
     */
    private void subscribe(int subscriptionType) throws IOException {
        byte[] buffer;
        Request request;

        //System.out.println(TAG + "Subscription type: " + subscriptionType);
        request = new Request(1, 1, subscriptionType);
        buffer = request.getDataAsByte();

        // 1 - Request
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, this.address, ConnectionInfo.PORT);
        socket.send(requestPacket);

        // 2 - Response
        if (subscriptionType == 0) { // Need to manage response only if it's handshake case
            buffer = new byte[HandshakeResponse.SIZE];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);
        }
    }

    /**
     * Remove this application from the list of listeners in assetto corsa server
     *
     * @throws IOException : thrown when something went wrong with the socket
     */
    private void unsubscribe() throws IOException {
        Request request = new Request(1, 1, OperationId.DISMISS);
        byte[] buffer = request.getDataAsByte();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.address, ConnectionInfo.PORT);
        socket.send(packet);
        //System.out.println("TAG + Unsubscribed from server");
    }

}
