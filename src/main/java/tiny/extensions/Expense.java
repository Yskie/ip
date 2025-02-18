package tiny.extensions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import tiny.exceptions.TinyException;

/**
 * Represents an expense.
 */
public class Expense {
    protected String description;
    protected Double amount;
    protected LocalDate transactionDate;

    /**
     * Initializes Expense.
     *
     * @param description     Description of the expense.
     * @param amount          Amount of the expense.
     * @param transactionDate Transaction date of the expense.
     */
    public Expense(String description, double amount, String transactionDate) throws TinyException {
        this.description = description;
        this.amount = amount;
        this.transactionDate = dateParser(transactionDate);
    }

    private LocalDate dateParser(String date) throws TinyException {
        int year = 0;
        int month = 0;
        int day = 0;
        String errorMsg = "Please ensure that you are using the format expense <description> /for "
                + "<amount> /on <yyyy-MM-dd>. eg. expense chicken rice /for 3.50 /on 2024-01-29";
        String invalidDateErrorMessage = "Please ensure that the date is valid. eg. 2024-01-29";
        // Processes the date
        try {
            String[] dateSplit = date.split("-");
            assert dateSplit.length == 3;
            year = Integer.parseInt(dateSplit[0]);
            month = Integer.parseInt(dateSplit[1]);
            day = Integer.parseInt(dateSplit[2]);
            if (!isValidDate(month, day)) {
                throw new TinyException(invalidDateErrorMessage);
            }
            return LocalDate.of(year, month, day);
        } catch (TinyException e) {
            throw e;
        } catch (Exception e) {
            throw new TinyException(errorMsg);
        }
    }

    private Boolean isValidDate(int month, int day) {
        if (month > 12 || month < 1) {
            return false;
        }

        if (month == 2) {
            return day <= 29;
        }

        int[] thirtyDayMonth = new int[] { 4, 6, 9, 11 };
        int[] thirtyOneDayMonth = new int[] { 1, 3, 5, 7, 8, 10, 12 };

        for (int i = 0; i < thirtyDayMonth.length; i++) {
            if (thirtyDayMonth[i] == month) {
                return day <= 30;
            }
        }

        for (int i = 0; i < thirtyOneDayMonth.length; i++) {
            if (thirtyOneDayMonth[i] == month) {
                return day <= 31;
            }
        }
        return true;
    }

    private String dateSaveFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return date.format(formatter);
    }

    public String formatToSave() {
        return "EX | " + description + " | " + amount + " | " + dateSaveFormat(transactionDate);
    }

    @Override
    public String toString() {
        return "Description: " + description + " | Amount: " + amount + " |  Date: " + dateToString(transactionDate);
    }
}
