import java.util.Scanner;

public class Main {

    private final static String[] WEEKDAY = {"TU", "WE", "TH", "FR", "SA", "SU",
            "MO", "TU", "WE", "TH", "FR", "SA", "SU",
            "MO", "TU", "WE", "TH", "FR", "SA", "SU",
            "MO", "TU", "WE", "TH", "FR", "SA", "SU",
            "MO", "TU", "WE"};
    private final static int MAXLENGTHNAME = 10;
    private final static int MAXCOUNTNAME = 10;
    private final static int MAXCOUNTCLASSSCHEDULE = 10;

    private static String[] inputNameStudent;
    private static String[][] inputClassSchedule;
    private static String[][] schedule;
    private static String[][] resultSchedules;

    public static void main(String[] args) {
        DataProcessing();
    }

    private static void DataProcessing() {
        Scanner sc = new Scanner(System.in);

        RecordInputName(sc);
        RecordInputClassSchedule(sc);
        CreatingSchedule();
        FillingAttendance(sc);
        sc.close();

        FillingResultSchedules();
        PrintSchedules();
    }

    private static void RecordInputName(Scanner sc) {
        String name = sc.next();
        inputNameStudent = new String[MAXLENGTHNAME];
        for (int i = 0; !name.equals("."); ++i, name = sc.next()) {
            CheckCountName(i);
            CheckName(name);
            inputNameStudent[i] = name;
        }
    }

    private static void CheckCountName(int count) {
        if (count > MAXCOUNTNAME - 1) {
            System.err.println("Illegal Argument: the number of names should be no more than 10");
            System.exit(1);
        }
    }

    private static void CheckName(String name) {
        if (name.length() > MAXLENGTHNAME) {
            System.err.println("Illegal Argument: the length of the name should not be more than 10 characters");
            System.exit(1);
        }
    }

    private static void RecordInputClassSchedule(Scanner sc) {
        String schedule = sc.next();
        inputClassSchedule = new String[MAXCOUNTCLASSSCHEDULE][2];
        for (int i = 0; !schedule.equals("."); ++i, schedule = sc.next()) {
            CheckCountClassSchedule(i);
            CheckTime(schedule);
            inputClassSchedule[i][0] = schedule;
            schedule = sc.next();
            inputClassSchedule[i][1] = schedule;
        }
    }

    private static void CheckCountClassSchedule(int count) {
        if (count > MAXCOUNTCLASSSCHEDULE) {
            System.err.println("Illegal Argument: the number of classes per week should not exceed 10");
            System.exit(1);
        }
    }

    private static void CheckTime(String time) {
        int tTime = Integer.parseInt(time);
        if (!(tTime >= 1 && tTime <= 6)) {
            System.err.println("Illegal Argument: classes can be held on any day of week between 1 pm and 6 pm");
            System.exit(1);
        }
    }

    private static void CreatingSchedule() {
        schedule = new String[CalculatingCountStudent() + 1][CalculatingNumberScheduleColumns() * 3 + 1];
        for (int i = 0; i < schedule.length; ++i) {
            for (int j = 0; j < schedule[i].length; ++j) {
                schedule[i][j] = "";
            }
        }
        for (int i = 1; i < schedule.length; ++i) {
            schedule[i][0] = inputNameStudent[i - 1];
        }
        for (int i = 0, j = 1; i < WEEKDAY.length; ++i) {
            for (int k = 0; k < inputClassSchedule.length && inputClassSchedule[k][1] != null; ++k) {
                if (WEEKDAY[i].equals(inputClassSchedule[k][1])) {
                    schedule[0][j] = inputClassSchedule[k][0];
                    schedule[0][j + 1] = inputClassSchedule[k][1];
                    schedule[0][j + 2] = Integer.toString(i + 1);
                    j += 3;
                }
            }
        }
    }

    private static int CalculatingCountStudent() {
        int countStudent = 0;
        for (int i = 0; i < inputNameStudent.length && inputNameStudent[i] != null; ++i) {
            ++countStudent;
        }
        return countStudent;
    }

    private static int CalculatingNumberScheduleColumns() {
        int countWorkDay = 0;
        for (int i = 0; i < inputClassSchedule.length && inputClassSchedule[i][1] != null; ++i) {
            for (int j = 0; j < WEEKDAY.length; ++j) {
                if (inputClassSchedule[i][1].equals(WEEKDAY[j])) {
                    ++countWorkDay;
                }
            }
        }
        return countWorkDay;
    }

    private static void FillingAttendance(Scanner sc) {
        String attendanceName = sc.next();
        for (int i = 1; !attendanceName.equals("."); ++i) {
            String attendanceTime = sc.next();
            String attendanceDay = sc.next();
            String attendanceMark = sc.next();
            int reapeatName = SearchRepeatRow(attendanceName);
            if (reapeatName == 0) {
                reapeatName = i;
            }
            schedule[reapeatName][0] = attendanceName;
            for (int j = 1; j < schedule[0].length; ) {
                if (attendanceTime.equals(schedule[0][j]) && attendanceDay.equals(schedule[0][j + 2])) {
                    if (attendanceMark.equals("HERE")) {
                        schedule[reapeatName][j + 2] = "1";
                    } else {
                        schedule[reapeatName][j + 2] = "-1";
                    }
                }
                j += 3;
            }
            attendanceName = sc.next();
        }
    }

    private static int SearchRepeatRow(String name) {
        int numberRow = 0;
        for (int i = 1; i < schedule.length && schedule[i][0] != null; ++i) {
            if (schedule[i][0].equals(name)) {
                numberRow = i;
            }
        }
        return numberRow;
    }

    private static void FillingResultSchedules() {
        resultSchedules = new String[schedule.length][(schedule[0].length - 1) / 3 + 1];
        for (int j = 0, k = 1; j < resultSchedules[0].length; ++j) {
            if (j == 0) {
                resultSchedules[0][j] = schedule[0][j];
            } else {
                resultSchedules[0][j] = schedule[0][k] + ":00 " + schedule[0][k + 1] + (Integer.parseInt(schedule[0][k + 2]) > 10 ? " " : "  ") + schedule[0][k + 2] + "|";
                k += 3;
            }
        }
        for (int i = 1; i < resultSchedules.length; ++i) {
            resultSchedules[i][0] = schedule[i][0];
        }
        for (int i = 1; i < resultSchedules.length; ++i) {
            for (int j = 1, k = 3; j < resultSchedules[i].length; ++j, k += 3) {
                if (schedule[i][k].equals("1") || schedule[i][k].equals("-1")) {
                    resultSchedules[i][j] = schedule[i][k] + "|";
                } else {
                    resultSchedules[i][j] = "|";
                }
            }
        }
    }

    private static void PrintSchedules() {
        for (int i = 0; i < resultSchedules.length; ++i) {
            for (int j = 0; j < resultSchedules[i].length; ++j) {
                System.out.printf("%11s", resultSchedules[i][j]);
            }
            System.out.println();
        }
    }
}
