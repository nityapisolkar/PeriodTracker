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
            System.out.println("4: Exit");
            System.out.println("Choose from the menu: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch(choice) {
                case 1 -> logPeriod();
                case 2 -> showPeriods();
                case 3 -> predictNextPeriod();
                case 4 -> {
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
            System.out.println("Period logged. ✅");
            periods.add(new PeriodEntry(startDate, duration));
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private static void showPeriods() {
        //format using datetimeformatter: MMM dd, yyyy ; just like May 27, 2025
        //use for each loop in PeriodEntry to print get start date (formatted using formatter), get duration
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        for (PeriodEntry p : periods) {
            System.out.println("Start: " + p.getStartDate().format(formatter) + ", Duration: " + p.getDuration());
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

        System.out.printf("Your average cycle length is %.1f days.%n", avgCycle);
        System.out.println("Your predicted next period start date is: " + prediction);
    }
}

//features to add next: symptom tracking, make sure that logged periods show up in chronological order (oldest to newest) when u try to see all logged, make sure only 12 appear at once (and u can go page by page)
//for next time; make sure that period entry objects order themselves CHRONOLOGICALLY: if two periods are entered and the one that is entered later has a start date before the other one, and if you try to
//predict the next cycle, you would get a negative avg cycle length and a predicted period that is BACKWARD