import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PeriodEntry {
    //to create the PeriodEntry object
    private LocalDate startDate;
    private int duration;
    
    //constructor
    public PeriodEntry(LocalDate startDate, int duration) {
        this.startDate = startDate;
        this.duration = duration;
    }

    //some getter methods
    public LocalDate getStartDate(){
        return startDate;
    }

    public int getDuration(){
        return duration;
    }
}
