package structutils;

import com.google.common.io.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StructWriter {
    private final ByteArrayOutputStream outputStream;
    private LittleEndianDataOutputStream littleEndianOutputStream;

    public StructWriter(int size) {
        outputStream = new ByteArrayOutputStream(size);
        littleEndianOutputStream = new LittleEndianDataOutputStream(outputStream);
    }

    public void writeInt(int val) throws IOException {
        littleEndianOutputStream.writeInt(val);
    }

    public byte[] toByteArray(){
        return outputStream.toByteArray();
    }
}
