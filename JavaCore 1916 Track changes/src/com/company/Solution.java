package com.company;

/*

1916 Track changes
Read from the console 2 file names - file1, file2.
Files contain lines, file2 is an updated version of file1, some of the lines are the same.
You need to create a combined version of the lines, write them to the list of lines.
ADDED and REMOVED operations cannot go in a row; they are always separated by SAME.
There are no empty lines in the original and edited files.
Example:
original edited general
file1: file2: result: (lines)
 
string1 string1 SAME string1
line2 REMOVED line2
line3 line3 SAME line3
line4 REMOVED line4
line5 line5 SAME line5
                string0 ADDED string0
string1 string1 SAME string1
line2 REMOVED line2
line3 line3 SAME line3
                line5 ADDED line5
line4 line4 SAME line4
line5 REMOVED line5

Requirements:
1. The Solution class must contain the LineItem class.
2. The Solution class must contain enum Type.
3. The Solution class should contain a public static field of lines of type List, which is immediately initialized.
4. In the main (String [] args) method, the program should read the file names from the console (use BufferedReader).
5. In the main (String [] args) method, the BufferedReader must be closed to read data from the console.
6. The program should read the contents of the first and second file (use FileReader).
7. Read streams from files (FileReader) must be closed.
8. The lines list should contain a combined version of lines from files, where for each line one of the operations ADDED, REMOVED, SAME is indicated.

*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Solution {
    public static List<LineItem> lines = new ArrayList<LineItem>();

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileReader fileReader1 = new FileReader(reader.readLine());
             FileReader fileReader2 = new FileReader(reader.readLine())) {

            List<String> original = new BufferedReader(fileReader1).lines().collect(Collectors.toList());
            List<String> modified = new BufferedReader(fileReader2).lines().collect(Collectors.toList());

            while (original.size() != 0 & modified.size() != 0) {
                if (original.get(0).equals(modified.get(0))) {
                    lines.add(new LineItem(Type.SAME, original.remove(0)));
                    modified.remove(0);
                } else if (modified.size() != 1 && original.get(0).equals(modified.get(1))) {
                    lines.add(new LineItem(Type.ADDED, modified.remove(0)));
                } else if (original.size() != 1 && original.get(1).equals(modified.get(0))) {
                    lines.add(new LineItem(Type.REMOVED, original.remove(0)));
                }
            }

            if (original.size() != 0) {
                lines.add(new LineItem(Type.REMOVED, original.remove(0)));
            } else if (modified.size() != 0) {
                lines.add(new LineItem(Type.ADDED, modified.remove(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines.forEach(System.out::println);
    }

    public static enum Type {
        ADDED,        //добавлена новая строка
        REMOVED,      //удалена строка
        SAME          //без изменений
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }

        @Override
        public String toString() {
            return "LineItem{" +
                    "type=" + type +
                    ", line='" + line + '\'' +
                    '}';
        }
    }
}



