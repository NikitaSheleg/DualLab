package util;

import entity.BusSchedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Converter {

    public static List<BusSchedule> readFromFile(String path) throws IOException {
        List<BusSchedule> busSchedules = new ArrayList<>();
        FileReader file = new FileReader(path);
        BufferedReader reader = new BufferedReader(file);
        String line = reader.readLine();
        while (line != null) {

            String[] words = line.split(" ");
            line = reader.readLine();
            busSchedules.add(new BusSchedule(LocalTime.parse(words[1]), LocalTime.parse(words[2]), words[0]));
        }

        for (int i = 0; i < busSchedules.size() - 1; i++) {
            BusSchedule schedule = busSchedules.get(i).chooseWorst(busSchedules.get(i + 1));
            if (schedule != null) {
                busSchedules.remove(schedule);
                i--;
            }
        }
        return busSchedules;
    }

    public static long between(LocalTime start,LocalTime end){
        if(end.isBefore(start)){
            end=end.plusHours(12);
            start=start.minusHours(12);
        }
        return MINUTES.between(start,end);
    }

    public static void toFile(List<BusSchedule>schedules){
        Comparator<BusSchedule> comparator = Comparator.comparing(BusSchedule::getCompanyName).reversed()
                .thenComparing(BusSchedule::getStart);
        schedules.sort(comparator);
        List<String> lines = new ArrayList<>();
        for(BusSchedule schedule:schedules){
            if (schedule.getCompanyName().equals("Grotty"))
                lines.add("");
            lines.add(schedule.toString());
        }
        Path path =Paths.get("output.txt");
        if (Files.exists(path)) {

            System.out.println("File already exists");
        } else {

            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(path, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
