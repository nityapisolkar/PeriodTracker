import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PeriodEntry {
    //to create the PeriodEntry object
    private LocalDate startDate;
    private int duration;
    private String note;
    
    //constructor
    public PeriodEntry(LocalDate startDate, int duration, String note) {
        this.startDate = startDate;
        this.duration = duration;
        this.note = note;
    }

    //some getter methods
    public LocalDate getStartDate(){
        return startDate;
    }

    public int getDuration(){
        return duration;
    }

    public String getNote() {
        return note;
    }

    //some setter methods
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setNote(String note){
        this.note = note;
    }
}
