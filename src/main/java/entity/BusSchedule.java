package entity;

import util.Converter;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BusSchedule {
    private LocalTime start, end;
    private String companyName;

    public BusSchedule(LocalTime start, LocalTime end, String companyName) {
        this.start = start;
        this.end = end;
        this.companyName = companyName;
    }

    public BusSchedule chooseWorst(BusSchedule busSchedule) {
        if (Converter.between(start, end) >= 60) {
            return this;
        } else if (Converter.between(busSchedule.getStart(), busSchedule.getEnd()) >= 60) return busSchedule;

        if (start.isAfter(busSchedule.getStart())
                && (end.isBefore(busSchedule.getEnd())
                || end.equals(busSchedule.getEnd())))
            return busSchedule;

        if (busSchedule.getStart().isAfter(start) &&
                ((busSchedule.getEnd().isBefore(end) &&
                        Converter.between(busSchedule.getEnd(), end) < 60)
                        || busSchedule.getEnd().equals(end)))
            return this;
        else if (start.equals(busSchedule.getStart())
                && (end.isBefore(busSchedule.getEnd())
                || end.equals(busSchedule.getEnd()))) {
            if (this.companyName.equals("Grotty")) return this;
            else if (busSchedule.getCompanyName().equals("Grotty")) return busSchedule;

            return busSchedule;

        }
        return null;

    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return companyName + " " + start.toString() + " " + end.toString();
    }
}
