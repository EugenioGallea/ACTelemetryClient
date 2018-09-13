package it.polito.s241876.client.ac_interaction.protocol;

import java.io.*;

public class Request {
    private Data data;

    public Request() { }

    public Request(int identifier, int version, int operationId){
        this.data = new Data();
        this.data.identifier = identifier;
        this.data.version = version;
        this.data.operationId = operationId;
    }

    public byte[] getDataAsByte() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = {};
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.write(this.data.identifier);
            out.write(this.data.version);
            out.write(this.data.operationId);
            out.flush();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return bytes;
    }

    @Override
    public String toString() {
        return "Request{" +
                "identifier = " + data.identifier +
                "version = " + data.version +
                "operationId = " + data.operationId +
                '}';
    }

    class Data implements Serializable {
        int identifier;
        int version;
        int operationId;

        Data() { }
    }
}
