import entity.BusSchedule;
import util.Converter;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<BusSchedule> busSchedules;
        try {
            busSchedules = Converter.readFromFile(args[0]);
            Converter.toFile(busSchedules);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
