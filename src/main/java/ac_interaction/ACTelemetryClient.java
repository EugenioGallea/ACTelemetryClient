package ac_interaction;

import ac_interaction.data.OperationId;
import ac_interaction.service.TelemetryService;

public class ACTelemetryClient {

    public static void run() {
        TelemetryService telemetryService = new TelemetryService();
        telemetryService.run(OperationId.SUBSCRIBE_UPDATE);
    }
}