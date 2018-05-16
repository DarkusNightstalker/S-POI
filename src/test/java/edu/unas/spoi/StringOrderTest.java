/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * The type String order test.
 *
 * @author Darkus Nightmare
 */
public class StringOrderTest {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        
        List<String> list = new ArrayList(Arrays.asList("12", "1", "2", "121", "122"));

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i = 0; i < list.size(); i++) {
            String parent = "-";
            for (int j = i - 1; j >= 0; j--) {
                if (list.get(i).startsWith(list.get(j))) {
                    parent = list.get(j);
                    break;
                }
            }
            System.out.println(list.get(i) + " \t ---- parent : " + parent);
        }
    }
}
