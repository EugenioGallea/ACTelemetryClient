package ac_interaction.utils;

import ac_interaction.data.OperationId;
import ac_interaction.data.RTCarInfo;
import ac_interaction.data.RTLap;
import ac_interaction.protocol.HandshakeResponse;

import java.io.IOException;

/**
 * Class for print utilities
 */
public class PrintUtils {
    /**
     * Function to print the data received for a certain response
     *
     * @param subscriptionType : which kind of response we received
     * @param data : data received
     * @return ret: string to be printed
     */
    public static String printResponse(int subscriptionType, byte[] data) throws IOException {
        String ret = "Unrecognized Operation id";
        switch (subscriptionType) {
            case OperationId.HANDSHAKE:
                System.out.println("Data len: " + data.length);
                HandshakeResponse hr = new HandshakeResponse(data);
                ret = hr.toString();
                break;

            case OperationId.SUBSCRIBE_UPDATE:
                RTCarInfo rci = new RTCarInfo(data);
                ret = rci.toString();
                break;

            case OperationId.SUBSCRIBE_SPOT:
                RTLap rl = new RTLap(data);
                ret = rl.toString();
                break;

            default:

                break;
        }

        return ret;
    }
}
