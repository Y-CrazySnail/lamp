package com.yeem;

public class Name {
    public static void main(String[] args) {
        String[] strList1 = {"飊", "飙", "飆"};
        String[] strList2 = {"鳯", "鳳"};
        String[] strList3 = {"飚", "飈", "颷", "飇"};
        String[] strList4 = {"飚", "飈", "颷", "飇"};
        int i = 1;
        for (String str1 : strList1) {
            for (String str2 : strList2) {
                for (String str3 : strList3) {
                    for (String str4 : strList4) {
                        System.out.println(i + ":" + str1 + str2 + str3 + str4);
                        i++;
                    }
                }
            }
        }
    }
}
