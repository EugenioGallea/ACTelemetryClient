package it.polito.s241876.client.ac_interaction;

import it.polito.s241876.client.ac_interaction.data.OperationId;
import it.polito.s241876.client.ac_interaction.service.TelemetryService;

public class ACTelemetryClient {

    public static void run() {
        TelemetryService telemetryService = new TelemetryService();
        telemetryService.run(OperationId.SUBSCRIBE_UPDATE);
    }
}