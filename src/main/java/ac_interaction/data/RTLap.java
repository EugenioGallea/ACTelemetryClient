package ac_interaction.data;

import ac_interaction.structutils.StructReader;

import java.io.IOException;

public class RTLap {
    public static final int RT_LAP_SIZE = 896;
    int carIdentifierNumber;
    int lap;
    String driverName;
    String carName;
    int time;

    public RTLap(byte[] received) throws IOException {
        StructReader structReader = new StructReader(received);
        carIdentifierNumber = structReader.readInt();
        lap = structReader.readInt();
        carName = structReader.readChars(50);
        driverName = structReader.readChars(50);
        time = structReader.readInt();
    }

    public int getCarIdentifierNumber() {
        return carIdentifierNumber;
    }

    public int getLap() {
        return lap;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCarName() {
        return carName;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "data.RTLap{" +
                "carIdentifierNumber=" + carIdentifierNumber +
                ", lap=" + lap +
                ", driverName='" + driverName + '\'' +
                ", carName='" + carName + '\'' +
                ", time=" + time +
                '}';
    }
}
