package it.polito.s241876.client.ac_interaction.data;

import it.polito.s241876.client.ac_interaction.structutils.StructReader;

import java.io.IOException;
import java.util.Arrays;

public class RTCarInfo {
    public static final int RT_CAR_INFO_SIZE = 328;

    private char identifier;
    private char dummy1;
    private char dummy2;
    private char dummy3;
    private int size;

    private float speed_Kmh;
    private float speed_Mph;
    private float speed_Ms;

    private boolean isAbsEnabled;
    private boolean isAbsInAction;
    private boolean isTcInAction;
    private boolean isTcEnabled;
    private boolean isInPit;
    private boolean isEngineLimiterOn;

    private float accG_vertical;
    private float accG_horizontal;
    private float accG_frontal;

    private int lapTime;
    private int lastLap;
    private int bestLap;
    private int lapCount;

    private float gas;
    private float brake;
    private float clutch;
    private float engineRPM;
    private float steer;
    private int gear;
    private float cgHeight;

    private float wheelAngularSpeed[];
    private float slipAngle[];
    private float slipAngle_ContactPatch[];
    private float slipRatio[];
    private float tyreSlip[];
    private float ndSlip[];
    private float load[];
    private float Dy[];
    private float Mz[];
    private float tyreDirtyLevel[];

    private float camberRAD[];
    private float tyreRadius[];
    private float tyreLoadedRadius[];
    private float suspensionHeight[];
    private float carPositionNormalized;
    private float carSlope;
    private float carCoordinates[];

    public RTCarInfo(byte[] received) throws IOException {
        StructReader structReader = new StructReader(received);
        identifier = structReader.readChar();   // NEW API
        this.dummy1 = structReader.readChar();  // NEW API
        this.dummy2 = structReader.readChar();  // NEW API
        this.dummy3 = structReader.readChar();  // NEW API
        size = structReader.readInt();

        speed_Kmh = structReader.readFloat();
        speed_Mph = structReader.readFloat();
        speed_Ms = structReader.readFloat();
        isAbsEnabled = structReader.readBool();
        isAbsInAction = structReader.readBool();
        isTcInAction = structReader.readBool();
        isTcEnabled = structReader.readBool();
        isInPit = structReader.readBool();
        isEngineLimiterOn = structReader.readBool();

        accG_vertical = structReader.readFloat();
        accG_horizontal = structReader.readFloat();
        accG_frontal = structReader.readFloat();

        lapTime = structReader.readInt();
        lastLap = structReader.readInt();
        bestLap = structReader.readInt();
        lapCount = structReader.readInt();

        gas = structReader.readFloat();
        brake = structReader.readFloat();
        clutch = structReader.readFloat();
        engineRPM = structReader.readFloat();
        steer = structReader.readFloat();
        gear = structReader.readInt();
        cgHeight = structReader.readFloat();
        wheelAngularSpeed = structReader.wreadFloats(4);
        slipAngle = structReader.wreadFloats(4);
        slipAngle_ContactPatch = structReader.wreadFloats(4);
        slipRatio = structReader.wreadFloats(4);
        tyreSlip = structReader.wreadFloats(4);
        ndSlip = structReader.wreadFloats(4);
        load = structReader.wreadFloats(4);
        Dy = structReader.wreadFloats(4);
        Mz = structReader.wreadFloats(4);
        tyreDirtyLevel = structReader.wreadFloats(4);

        camberRAD = structReader.wreadFloats(4);
        tyreRadius = structReader.wreadFloats(4);
        tyreLoadedRadius = structReader.wreadFloats(4);
        suspensionHeight = structReader.wreadFloats(4);
        carPositionNormalized = structReader.readFloat();
        carSlope = structReader.readFloat();

        carCoordinates = structReader.wreadFloats(5); // NEW API
    }

    public char getIdentifier() {
        return identifier;
    }

    public int getSize() {
        return size;
    }

    public float getSpeed_Kmh() {
        return speed_Kmh;
    }

    public float getSpeed_Mph() {
        return speed_Mph;
    }

    public float getSpeed_Ms() {
        return speed_Ms;
    }

    public boolean isAbsEnabled() {
        return isAbsEnabled;
    }

    public boolean isAbsInAction() {
        return isAbsInAction;
    }

    public boolean isTcInAction() {
        return isTcInAction;
    }

    public boolean isTcEnabled() {
        return isTcEnabled;
    }

    public boolean isInPit() {
        return isInPit;
    }

    public boolean isEngineLimiterOn() {
        return isEngineLimiterOn;
    }

    public float getAccG_vertical() {
        return accG_vertical;
    }

    public float getAccG_horizontal() {
        return accG_horizontal;
    }

    public float getAccG_frontal() {
        return accG_frontal;
    }

    public int getLapTime() {
        return lapTime;
    }

    public int getLastLap() {
        return lastLap;
    }

    public int getBestLap() {
        return bestLap;
    }

    public int getLapCount() {
        return lapCount;
    }

    public float getGas() {
        return gas;
    }

    public float getBrake() {
        return brake;
    }

    public float getClutch() {
        return clutch;
    }

    public float getEngineRPM() {
        return engineRPM;
    }

    @Override
    public String toString() {
        return "data.RTCarInfo{" +
                "identifier=" + identifier +
                ", dummy1=" + dummy1 +
                ", dummy2=" + dummy2 +
                ", dummy3=" + dummy3 +
                ", size=" + size +
                ", speed_Kmh=" + speed_Kmh +
                ", speed_Mph=" + speed_Mph +
                ", speed_Ms=" + speed_Ms +
                ", isAbsEnabled=" + isAbsEnabled +
                ", isAbsInAction=" + isAbsInAction +
                ", isTcInAction=" + isTcInAction +
                ", isTcEnabled=" + isTcEnabled +
                ", isInPit=" + isInPit +
                ", isEngineLimiterOn=" + isEngineLimiterOn +
                ", accG_vertical=" + accG_vertical +
                ", accG_horizontal=" + accG_horizontal +
                ", accG_frontal=" + accG_frontal +
                ", lapTime=" + lapTime +
                ", lastLap=" + lastLap +
                ", bestLap=" + bestLap +
                ", lapCount=" + lapCount +
                ", gas=" + gas +
                ", brake=" + brake +
                ", clutch=" + clutch +
                ", engineRPM=" + engineRPM +
                ", steer=" + steer +
                ", gear=" + gear +
                ", cgHeight=" + cgHeight +
                ", wheelAngularSpeed=" + Arrays.toString(wheelAngularSpeed) +
                ", slipAngle=" + Arrays.toString(slipAngle) +
                ", slipAngle_ContactPatch=" + Arrays.toString(slipAngle_ContactPatch) +
                ", slipRatio=" + Arrays.toString(slipRatio) +
                ", tyreSlip=" + Arrays.toString(tyreSlip) +
                ", ndSlip=" + Arrays.toString(ndSlip) +
                ", load=" + Arrays.toString(load) +
                ", Dy=" + Arrays.toString(Dy) +
                ", Mz=" + Arrays.toString(Mz) +
                ", tyreDirtyLevel=" + Arrays.toString(tyreDirtyLevel) +
                ", camberRAD=" + Arrays.toString(camberRAD) +
                ", tyreRadius=" + Arrays.toString(tyreRadius) +
                ", tyreLoadedRadius=" + Arrays.toString(tyreLoadedRadius) +
                ", suspensionHeight=" + Arrays.toString(suspensionHeight) +
                ", carPositionNormalized=" + carPositionNormalized +
                ", carSlope=" + carSlope +
                ", carCoordinates=" + Arrays.toString(carCoordinates) +
                '}';
    }
}
