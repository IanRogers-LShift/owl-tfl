import org.junit.Test;

public class ProgramExecutorTest {

    private String stopPoint = "940GZZLUWLO";
    private String lineId = "bakerloo";

    @Test
    public void run(){
        ProgramExecutor programExecutor = new ProgramExecutor(stopPoint, lineId);

        programExecutor.run();
    }


}
