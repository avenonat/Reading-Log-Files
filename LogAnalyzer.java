
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for (String line : fr.lines()) {
             records.add(WebLogParser.parseEntry(line));
         }
         // complete method
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs() {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for (LogEntry le : records) {
             String ipAddr = le.getIpAddress();
             if (!uniqueIPs.contains(ipAddr)) {
                 uniqueIPs.add(ipAddr);
             }
         }
         return uniqueIPs.size();
     }
     
     public void printAllHigherThanNum(int num) {
         for (LogEntry le : records) {
             int temp = le.getStatusCode();
             if (temp > num) {
                 System.out.println(le);
             }
         }
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
         ArrayList<String> date = new ArrayList<String>();
         String myString = null;
         for (LogEntry le : records) {
             Date temp = le.getAccessTime();
             String ipAddr = le.getIpAddress();
             myString = temp.toString();
             int index = date.indexOf(ipAddr);
             if (myString.contains(someday) && (!date.contains(ipAddr))) {
                 date.add(ipAddr);
             }
         }
         return date;
     }
     
     public int countUniqueIPsInRange(int low, int high) {
         ArrayList<String> ips = new ArrayList<String>();
         int count = 0;
         for (LogEntry le : records) {
             int status = le.getStatusCode();
             if (status >= low && status <= high) {
                 if (!ips.contains(le.getIpAddress())) {
                     ips.add(le.getIpAddress());
                 }
             }
         }
         return ips.size();
     }
     
     public HashMap<String, Integer> countVisitsPerIP() {
         HashMap<String,Integer> count = new HashMap<String, Integer>();
         for (LogEntry le : records) {
             String ip = le.getIpAddress();
             if (!count.containsKey(ip)) {
                 count.put(ip, 1);
             }
             else {
                 count.put(ip, count.get(ip) + 1);
             }
         }
         return count;
     }
     
     public int mostNumberVisitsByIP(HashMap<String, Integer> counts) {
         int max = 0;
         for (HashMap.Entry<String, Integer> s : counts.entrySet()) {
             if (s.getValue() > max) {
                 max = s.getValue();
             }
         }
         return max;
     }
     
     public ArrayList iPsMostVisits(HashMap<String, Integer> counts) {
         ArrayList<String> max = new ArrayList<String>();
         int maxim = mostNumberVisitsByIP(counts);
         for (HashMap.Entry<String, Integer> s : counts.entrySet()) {
             if (s.getValue() == maxim) {
                 max.add(s.getKey());
             }
         }
         return max;
     }
     
     public HashMap<String, ArrayList<String>>  iPsForDays() {
        HashMap<String, ArrayList<String>> days = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            String date = le.getAccessTime().toString();
            date = date.substring(4,10);
            if (days.containsKey(date)) {
                ArrayList<String> temp = days.get(date);
                temp.add(le.getIpAddress());
                days.put(date, temp);
            }
            else {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(le.getIpAddress());
                days.put(date, temp);
            }
        }
        return days;
    }
    
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> counts) {
        HashMap<String, ArrayList<String>> map = iPsForDays();
        int temp = 0;
        String name = "";
        for (HashMap.Entry<String, ArrayList<String>> s : counts.entrySet()) {
            if (s.getValue().size() > temp) {
                name = s.getKey();
            }
        }
        return name;
    }
    
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String day) {
        ArrayList<String> temp = uniqueIPVisitsOnDay(day);
        HashMap<String,Integer> counts = new HashMap<String,Integer>();
        for (int k = 0; k < temp.size(); k++) {
            String s = temp.get(k);
            if (!counts.containsKey(s)) {
                counts.put(s, 1);
            }
            else {
                int freq = counts.get(s);
                counts.put(s, freq + 1);
            }
        }
        return iPsMostVisits(counts);
    }
}
