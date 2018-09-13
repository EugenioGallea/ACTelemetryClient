package ac_interaction.structutils;

import java.io.ByteArrayInputStream;

public class StructReader {
    private ByteArrayInputStream is;

    public StructReader(byte[] received) {
        is = new ByteArrayInputStream(received);
    }

    public String readChars (int size) {
        char[] arr = new char[size];
        int index = -1;

        for (int i = 0; i < size && index == -1; i++) {
            char c = this.readChar();
            if (c == '%') {
                index = i - 1;
            } else {
                arr[i] = c;
            }
        }

        if (index == -1) {
            index = arr.length;
        }

        return new String(arr, 0, index);
    }

    public float[] wreadFloats( int size) {
        float[] arr = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = this.readFloat();
        }
        return arr;
    }

    public int readInt() { // 32 bit long integers for Assetto Corsa
        byte[] ret = new byte[4];
        int howMany = is.read(ret, 0,4);

        assert howMany == 4;

        return ret[0] & 0xFF |
                (ret[1] &0xFF) << 8 |
                (ret[2] &0xFF) << 16 |
                (ret[3] &0xFF) << 24;
    }

    public char readChar() {
        return (char) is.read();
    }

    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }

    public boolean readBool() {
        byte[] ret = new byte[1];
        int howMany = is.read(ret, 0,1);

        assert howMany == 1;

        return ret[0]!=0;
    }
}
