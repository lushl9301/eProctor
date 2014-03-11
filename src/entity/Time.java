package entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Time {

  private Integer year;
  private Integer month;
  private Integer day;
  private Integer hour;
  private Integer minute;
  private boolean weekday;
  private boolean publicHoliday;
  
  private static final String strMonth[] = {"JAN", "FEB", "MAR",
    "APR", "MAY", "JUN",
    "JUL", "AUG", "SEP",
    "OCT", "NOV", "DEC"};

  private static ArrayList<String> weekendList = new ArrayList<String>(); // format: yyyymmdd

  public Time(String time) {
    String[] value = time.split(" ");
    year = Integer.parseInt(value[0]);
    month = Integer.parseInt(value[1]);
    day = Integer.parseInt(value[2]);
    hour = Integer.parseInt(value[3]);
    minute = Integer.parseInt(value[4]);
    
    updateWeekend();
    
    publicHoliday = false;
    if (value[5].compareTo("true") == 0)
        publicHoliday = true;
  }
  public Time() {}

  public Time(Integer year, Integer month, Integer day) {
    this.setDay(day);
    this.setMonth(month);
    this.setYear(year);
    this.updateWeekend();
  }
  
  public static Time manualNewATime() {
    Scanner sc = new Scanner(System.in);
    Time newTime = new Time();
    System.out.println("Please enter year: ");
    newTime.setYear(sc.nextInt());
    System.out.println("Please enter month: 1~12:");
    newTime.setMonth(sc.nextInt());
    System.out.println("Please enter day:");
    newTime.setDay(sc.nextInt());
    System.out.println("Please enter hour: 0~23");
    newTime.setHour(sc.nextInt());
    System.out.println("Please enter minute: 0~59");
    newTime.setMinute(sc.nextInt());
    newTime.updateWeekend();
    return newTime;
  }

  public Time getCurrentTime() {
    year = Calendar.getInstance().get(Calendar.YEAR);
    month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    this.day = Calendar.getInstance().get(Calendar.DATE);
    this.hour = Calendar.getInstance().get(Calendar.HOUR);
    this.minute = Calendar.getInstance().get(Calendar.MINUTE);
    this.setWeekday();
    publicHoliday = false;

    return this;
  }

  public void printTime() {
    System.out.format("%2d %s %4d\n   %2d:%2d", day,
        strMonth[month - 1], year, hour, minute);
  }

  public String getStr() {
    String str = String.format("%2d %s %4d\n   %2d:%2d", day,
        strMonth[month - 1], year, hour, minute);
    return str;
  }
  
  public String toString() {
    return String.format("%d %d %d %d %d %s", year, month, day, hour, minute, String.valueOf(publicHoliday));
  }
  
  public Integer toInt() {
    return this.year * 10000 + this.month * 100 + this.day;
  }

  public boolean setYear(Integer year) {
    this.year = year;
    return true;
  }

  public Integer getYear() {
    return year;
  }

  public boolean setMonth(Integer month) {
    this.month = month;
    return true;
  }

  public Integer getMonth() {
    return month;
  }

  public boolean setDay(Integer day) {
    this.day = day;
    return true;
  }

  public Integer getDay() {
    return day;
  }

  public boolean setHour(Integer hour) {
    this.hour = hour;
    return true;
  }

  public Integer getHour() {
    return hour;
  }

  public boolean setMinute(Integer minute) {
    this.minute = minute;
    return true;
  }

  public Integer getMinute() {
    return minute;
  }
  //FIX HERE!!!!
  //=================================
  public boolean setWeekday() {
    this.weekday = false;
    return true;
  }

  public boolean getWeekday() {
    return weekday;
  }

  public void updateWeekend() {
    Calendar cal = new GregorianCalendar(year, month - 1, day);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    this.weekday = true;
    if (Calendar.SUNDAY == dayOfWeek || Calendar.SATURDAY == dayOfWeek) {
      this.weekday = false;
    }
  }

  public String toFormat() {
    String ph = "";
    if (this.publicHoliday)
      ph = "PH";
    String weekDay = "";
    Calendar cal = new GregorianCalendar(year, month - 1, day);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    switch (dayOfWeek - 1) {
      case 0 : weekDay = "SUN";
      case 1 : weekDay = "MON";
      case 2 : weekDay = "TUE";
      case 3 : weekDay = "WED";
      case 4 : weekDay = "THU";
      case 5 : weekDay = "FRI";
      case 6 : weekDay = "SAT";
    }
    return (this.year + "-" + this.month + "-" + this.day + " " + weekDay + " " + this.hour + ":" + this.minute + " " + ph);
  }
  
  public int compareTo(Time t) {
    if (this.toInt() > t.toInt())
      return 1;
    if (this.toInt() == t.toInt())
      return 0;
    return -1;
  }
}
