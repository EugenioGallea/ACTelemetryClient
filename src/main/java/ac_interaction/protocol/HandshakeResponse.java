package it.polito.s241876.client.ac_interaction.protocol;

import it.polito.s241876.client.ac_interaction.structutils.StructReader;

public class HandshakeResponse {
    private static final int CAR_NAME_SIZE = 50;
    private static final int DRIVER_NAME_SIZE = 50;
    private static final int TRACK_NAME_SIZE = 50;
    private static final int TRACK_CONFIG_SIZE = 50;
    public static final int SIZE =
                    CAR_NAME_SIZE * 2 +
                    DRIVER_NAME_SIZE * 2 +
                    TRACK_NAME_SIZE * 2 +
                    TRACK_CONFIG_SIZE * 2 +
                    4 + 4;
    private String driverName;
    private String carName;
    private String trackName;
    private String trackConfig;
    private int identifier;
    private int version;

    public HandshakeResponse(byte[] received) {
        StructReader structReader = new StructReader(received);

        carName = structReader.readChars(CAR_NAME_SIZE);
        structReader.readChars(30);
        identifier = structReader.readInt();
        version = structReader.readInt();
        driverName =structReader.readChars(DRIVER_NAME_SIZE * 2);
        structReader.readChars(38);
        trackName = structReader.readChars(TRACK_NAME_SIZE * 2);
        structReader.readChars(44);
        trackConfig = structReader.readChars(TRACK_CONFIG_SIZE * 2);
    }

    @Override
    public String toString() {
        return "handshake.HandshakeResponse{" +
                "carName='" + carName + '\'' +
                ", driverName='" + driverName + '\'' +
                ", identifier=" + identifier +
                ", version=" + version +
                ", trackName='" + trackName + '\'' +
                ", trackConfig='" + trackConfig + '\'' +
                '}';
    }
}
