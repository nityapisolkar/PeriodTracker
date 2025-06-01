package com.periodtracker.cli;

import com.periodtracker.model.PeriodEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PeriodTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<PeriodEntry> periods = new ArrayList<>();
    public static void main(String[] args){
        while(true) {
            //main loop (menu)
            System.out.println("\n--- Period Tracker ---");
            System.out.println("1. Log a period");
            System.out.println("2. See all logged periods");
            System.out.println("3. Predict next period");
            System.out.println("4. View period statistics");
            System.out.println("5. Edit a period");
            System.out.println("6: Delete a period");
            System.out.println("7: Exit");
            System.out.println("Choose from the menu: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, please enter a number.");
                continue;
            }

            switch(choice) {
                case 1 -> logPeriod();
                case 2 -> showPeriods();
                case 3 -> predictNextPeriod();
                case 4 -> showStatistics();
                case 5 -> editPeriod();
                case 6 -> deletePeriod();
                case 7 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    //method time!
    private static void logPeriod() {
        //use try and catch: 
        //ask user to enter in a start date in a certain format
        //parse it, save it as startDate (w/ scanner)
        //ask user to enter in the duration of the period
        //parse it, save it as duration (w/ scanner)
        //add this new PeriodEntry object
        //the exception to catch -> oooops invalid input!

        try {
            System.out.println("Enter your period's start date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.println("Enter the duration of your period in days, as an integer: ");
            int duration = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter a note about your period symptoms (mood, bloating, cramps, pain, intensity of bleeding, etc.): ");
            String note = scanner.nextLine();
            System.out.println("Period logged. ✅");
            periods.add(new PeriodEntry(startDate, duration, note));
            periods.sort(Comparator.comparing(PeriodEntry::getStartDate));
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private static void showPeriods() {
        //format using datetimeformatter: MMM dd, yyyy ; just like May 27, 2025
        //use for each loop in PeriodEntry to print get start date (formatted using formatter), get duration
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        if (periods.size() < 1) {
            System.out.println("No periods logged.");
            return;
        }
        for (int i = 0; i < periods.size(); i++){
            PeriodEntry p = periods.get(i);
            System.out.println((i+1) + ". Start: " + p.getStartDate().format(formatter) + ", Duration: " + p.getDuration() + ", Note: " + p.getNote());
        }

    }

    private static void predictNextPeriod() {
        //need at least 2 periods to predict: use if (control flow)

        //to calculate average cycle length, make an arraylist of the cycle lengths (use a for loop to go through each) and get daysbetween, add that to cycle : remember that cycles are start date to start date
        //average all the elements in the arraylist -- else choose 28 days
        //take the last period entered in periodEntry, and add the average cycle (from above) to its start date!
        //then print average cycle length, and predicted start date for next period

        if (periods.size() < 2) {
            System.out.println("At least 2 periods are needed to predict the next cycle.");
            return;
        }

        List<Long> cycles = new ArrayList<>();
        for (int i = 1; i < periods.size(); i++) {
            long daysBetween = periods.get(i).getStartDate().toEpochDay() - periods.get(i-1).getStartDate().toEpochDay();
            cycles.add(daysBetween);
        }

        double avgCycle = cycles.stream().mapToLong(Long::longValue).average().orElse(28);

        //predicting next period based on last logged period
        PeriodEntry last = periods.get(periods.size() - 1);
        LocalDate prediction = last.getStartDate().plusDays((long) avgCycle);
        LocalDate ovulationDate = prediction.minusDays(14);

        System.out.printf("Your average cycle length is %.1f days.%n", avgCycle);
        System.out.println("Your predicted next period start date is: " + prediction);
        System.out.println("Estimated ovulation date: " + ovulationDate);
    }

    private static void showStatistics() {
        if (periods.isEmpty()) {
            System.out.println("No periods logged yet.");
            return;
        }
        System.out.println("\n--- Period Statistics ---");
        
        //Total periods logged
        System.out.println("Total periods logged: " + periods.size());

        //First and last logged periods
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate firstDate = periods.get(0).getStartDate();
        LocalDate lastDate = periods.get(periods.size() - 1).getStartDate();
        System.out.println("First logged period: " + firstDate.format(formatter));
        System.out.println("Last logged period: " + lastDate.format(formatter));

        //Duration
        double avgDuration = periods.stream().mapToInt(PeriodEntry::getDuration).average().orElse(0);
        System.out.println("Average period duration: " + avgDuration + " days.");

        predictNextPeriod();
    }

    private static void editPeriod(){
        if (periods.isEmpty()) {
            System.out.println("No period entries to edit.");
            return;
        }

        int toModify = -1;
        try {
            System.out.println("Enter the number of the period entry you want to modify: ");
            toModify = Integer.parseInt(scanner.nextLine()); 
        } catch (Exception e) {
            System.out.println("Invalid input. Please choose a number between 1 and " + periods.size());
            return;
        }

        if (toModify < 1 || toModify > periods.size()){
            System.out.println("Invalid selection. Please choose a number between 1 and " + periods.size());
            return;
        }
        toModify--; // to get a zero based index
        PeriodEntry periodToModify = periods.get(toModify);

        //showing current values
        System.out.println("Current start date: " + periodToModify.getStartDate());
        System.out.println("Enter new start date (YYYY-MM-DD) or press enter to keep it: ");
        String newStartDateStr = scanner.nextLine();
        if (!newStartDateStr.isBlank()) {
            try {
                LocalDate newDate = LocalDate.parse(newStartDateStr);
                periodToModify.setStartDate(newDate);
            } catch (Exception e) {
                System.out.println("Invalid date format, start date not changed.");
            }
        }

        System.out.println("Current duration: " + periodToModify.getDuration());
        System.out.println("Enter new duration in days or press enter to keep it: ");
        String newDurationStr = scanner.nextLine();
        if (!newDurationStr.isBlank()) {
            try {
                int newDuration = Integer.parseInt(newDurationStr);
                periodToModify.setDuration(newDuration);
            } catch (Exception e) {
                System.out.println("Invalid number, duration not changed.");
            }
        }

        System.out.println("Current note: " + periodToModify.getNote());
        System.out.println("Enter new note or press enter to keep it: ");
        String newNote = scanner.nextLine();
        if (!newNote.isBlank()) {
            periodToModify.setNote(newNote);
        }

        //reorder in case date changed
        periods.sort(Comparator.comparing(PeriodEntry::getStartDate));
        System.out.println("Period entry updated");

    }

    private static void deletePeriod(){
        if (periods.isEmpty()) {
            System.out.println("No period entries to delete.");
            return;
        }

        showPeriods();
        int toModify = -1;
        try {
            System.out.println("Enter the number of the period entry you want to delete: ");
            toModify = Integer.parseInt(scanner.nextLine()); 
        } catch (Exception e) {
            System.out.println("Invalid input. Please choose a number between 1 and " + periods.size());
        }

        if (toModify < 1 || toModify > periods.size()){
            System.out.println("Invalid selection. Please choose a number between 1 and " + periods.size());
            return;
        }
        toModify--; // to get a zero based index
        PeriodEntry periodToModify = periods.get(toModify);
        
        System.out.println("Are you sure you want to delete this entry? (Y/N)");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            periods.remove(toModify);
            System.out.println("Period entry deleted.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }
}

//features to add next: 
//FOR GUI: make sure only 12 appear at once (and u can go page by page)
// edit and delete periods that have been logged
//✅ period history summary: avg duration, longest/shortest cycle, number of periods logged, first and most recent period dates
//✅ expand PeriodEntry to include a "String note"
//✅ ovulation prediction
//FOR GUI: potentially overdue period?
//FOR GUI: graphs/trends for changes: is the cycle getting shorter/longer

