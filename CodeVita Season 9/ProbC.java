
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public final class ProbC {

    private static class StringLT implements Comparator<String>, Serializable {
        private static final long serialVersionUID = -8554858568197410078L;

        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    private static void getLongestSubString(String str,
            HashMap<String, Integer> map, int initial, int len, String current,
            ArrayList<String> mapArr) {
        int pos = initial;
        String longest = current;
        if (pos < len && !current.contains("" + str.charAt(pos))) {
            getLongestSubString(str, map, initial + 1, len,
                    current + str.charAt(pos), mapArr);
        } else {
            if (checkUnique(longest, map, initial - longest.length(),
                    initial - 1)) {
                map.put(longest, initial - longest.length());
                mapArr.add(longest);
            }
        }
    }

    private static boolean checkUnique(String subStr,
            HashMap<String, Integer> map, int startPosOfSubStr,
            int endPosOfSubStr) {
        boolean add = true;
        Set<Entry<String, Integer>> mapSet = map.entrySet();
        for (Entry<String, Integer> pair : mapSet) {
            if (pair.getKey().contains(subStr)) {
                int startPosOfKey = map.get(pair.getKey()),
                        endPosOfKey = pair.getKey().length() + startPosOfKey
                                - 1;
                if (startPosOfSubStr > startPosOfKey
                        && endPosOfSubStr <= endPosOfKey) {
                    //means proper substring
                    add = false;
                }
            }
        }
        return add;
    }

    private static void createMap(String str, HashMap<String, Integer> map,
            int pos, int len, ArrayList<String> mapArr) {
        if (pos < len - 1) {
            getLongestSubString(str, map, pos, len, "", mapArr);
            createMap(str, map, pos + 1, len, mapArr);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int strLen = Integer.parseInt(in.nextLine());
        String str = in.nextLine().toLowerCase();

//        int strLen = 8;
//        String str = "abcdcbac";
//        int strLen = 8;
//        String str = "abcdcbcd";

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<String> mapArr = new ArrayList<String>();
        createMap(str, map, 0, strLen, mapArr);

        //sort according to key. alphabetical order
        Comparator<String> sortInt = new StringLT();
        mapArr.sort(sortInt);

        //print result
        int count = 0, mapSize = mapArr.size();
        for (String key : mapArr) {
            if (count == mapSize) {
                System.out.print(key);
            } else {
                System.out.print(key + " ");
            }
        }
        in.close();
    }

}
