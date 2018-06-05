package handshake;

import java.io.*;

public class HandshakeRequest {
    private Data data;

    public HandshakeRequest(int identifier, int version, int operationId){
        this.data = new Data();
        this.data.identifier = identifier;
        this.data.version = version;
        this.data.operationId = operationId;
    }

    public void setIdentifier(int identifier) {
        this.data.identifier = identifier;
    }

    public void setVersion(int version) {
        this.data.version = version;
    }

    public void setOperationId(int operationId) {
        this.data.operationId = operationId;
    }

    public Serializable getData(){
        return this.data;
    }

    public byte[] getDataAsByte() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = {};
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this.data);
            out.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return bytes;
    }

    public int getOperationId() {
        return this.data.operationId;
    }

    @Override
    public String toString() {
        return "HandshakeRequest{" +
                "identifier = " + data.identifier +
                "version = " + data.version +
                "operationId = " + data.operationId +
                '}';
    }

    class Data implements Serializable {
        int identifier;
        int version;
        int operationId;
    }
}
