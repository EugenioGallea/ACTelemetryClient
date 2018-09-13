import data.OperationId;
import service.TelemetryService;

public class Main{
   public static void main(String[] args){
       TelemetryService telemetryService = new TelemetryService();
       telemetryService.run(OperationId.SUBSCRIBE_UPDATE);
   }
}
