package com.cyclesync.cyclesync.service;

import com.cyclesync.cyclesync.model.PeriodEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PeriodService {
    private final List<PeriodEntry> periodEntries = new ArrayList<>();

    public List<PeriodEntry> getAllPeriods() {
        return new ArrayList<>(periodEntries); // defensive copy
    }

    public void addPeriod(PeriodEntry entry) {
        periodEntries.add(entry);
        periodEntries.sort(Comparator.comparing(PeriodEntry::getStartDate));
    }

    public void updatePeriod(int index, PeriodEntry updatedEntry) {
        if (index >= 0 && index < periodEntries.size()) {
            periodEntries.set(index, updatedEntry);
            periodEntries.sort(Comparator.comparing(PeriodEntry::getStartDate));
        }
    }

    public void deletePeriod(int index) {
        if (index >= 0 && index < periodEntries.size()) {
            periodEntries.remove(index);
        }
    }

    public LocalDate predictNextPeriod() {
        if (periodEntries.size() < 2) {
            return null;
        }

        List<Long> cycleLengths = new ArrayList<>();
        for (int i = 1; i < periodEntries.size(); i++) {
            long diff = periodEntries.get(i).getStartDate()
                    .toEpochDay() - periodEntries.get(i - 1).getStartDate().toEpochDay();
            cycleLengths.add(diff);
        }

        double averageCycle = cycleLengths.stream().mapToLong(Long::longValue).average().orElse(28);
        LocalDate lastStartDate = periodEntries.get(periodEntries.size() - 1).getStartDate();
        return lastStartDate.plusDays((long) averageCycle);
    }

    public double getAverageDuration() {
        return periodEntries.stream()
                .mapToInt(PeriodEntry::getDuration)
                .average()
                .orElse(0);
    }

    public int getTotalPeriods() {
        return periodEntries.size();
    }

    public LocalDate getFirstPeriodDate() {
        return periodEntries.isEmpty() ? null : periodEntries.get(0).getStartDate();
    }

    public LocalDate getLastPeriodDate() {
        return periodEntries.isEmpty() ? null : periodEntries.get(periodEntries.size() - 1).getStartDate();
    }
}
