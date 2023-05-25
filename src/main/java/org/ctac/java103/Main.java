package org.ctac.java103;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*Task: Create a Java program that reads two text files containing lists of integers, one per line. The program should merge the contents of the two input files into a single output file, maintaining the original order of the integers. Additionally, the program should create a separate output file containing the integers that are present in both input files.

        Instructions:

        1. Read integers from two text files called "input1.txt" and "input2.txt". Each integer is on a new line in the respective files.
        2. Merge the contents of the two input files, maintaining the original order of the integers, and write the result to a new text file called "merged.txt".
        3. Identify the integers that are present in both input files.
        4. Write the integers that are present in both input files to a new text file called "common.txt".
        5. Handle any exceptions that might occur during the process, such as FileNotFoundException, IOException, and NumberFormatException. Use try-catch blocks as needed.*/
public class Main {
    public static void main(String[] args) {
        try {
            String file1 = "input1.txt";  //files found in the root folder of the project
            String file2 = "input2.txt";

            //creating string lists from file
            List<String> fileString1 = pathsToList(file1);
            List<String> fileString2 = pathsToList(file2);

            //converting string lists to int lists
            List<Integer> fileInt1 = stringListToInt(fileString1);
            List<Integer> fileInt2 = stringListToInt(fileString2);

            //combining list
            List<Integer> combinedIntList = new ArrayList<>(fileInt1);
            combinedIntList.addAll(fileInt2);

            //creating file from combined list
            fileFromIntList("merged.txt", combinedIntList);


            //System.out.println(combinedIntList); //testing combined list, works

            //getting duplicates from combined list
            List<Integer> commonIntList = findDuplicates(combinedIntList);

            System.out.println("Integers found in both files: " + commonIntList);

            //creating file from the common int list
            fileFromIntList("common.txt", commonIntList);
        } catch (NullPointerException npe){
            System.out.println("Somewhere, something went wrong, please try check your files and directory and try again.");
            npe.printStackTrace();

        }


    }

    //method converts list of strings to list of ints
    private static List<Integer> stringListToInt(List<String> fileString1) {

        List<Integer> fileInts1 = null;

        try {
            fileInts1 = fileString1.stream()
                    .map(s -> Integer.parseInt(s))
                    .collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            System.out.println("Cannot convert into int");
            nfe.printStackTrace();
        } catch (NullPointerException npe){
            System.out.println("No file found");
            npe.printStackTrace();
        }

        return fileInts1;
    }

    //Method creates a string list from the input file
    static List<String> pathsToList(String inputFile) {

        List<String> fileInts = null;

        try {
            Path path = Paths.get(inputFile);
            fileInts = Files.readAllLines(path, StandardCharsets.UTF_8);

        } catch (IOException ioe) {

            System.out.println("File not found.");
            //ioe.printStackTrace();

        }

        return fileInts;

    }


    //Method creates a list of duplicates but adding numbers into a set for each element in a list. The elements
    // that don't make it on the set are then added to a new list.
    public static List<Integer> findDuplicates(List<Integer> intList) {
        Set<Integer> set = new HashSet<>();
        List<Integer> duplicatesList = new ArrayList<>();

        try {

            for (Integer element : intList) {
                if (!set.add(element)) {
                    duplicatesList.add(element);
                }
            }
        } catch (NullPointerException npe){
            System.out.println("NPE Exception encountered");
            npe.printStackTrace();
        }

        return duplicatesList;
    }

    public static void fileFromIntList(String fileName, List<Integer> listOfInts ){
        Path filePath = Path.of(fileName);

        List<String> stringList = new ArrayList<>();
        for (Integer number : listOfInts) {
            stringList.add(number.toString());
        }

        try {
            Files.write(filePath, stringList, StandardOpenOption.CREATE);
            System.out.println("List written to file. Check it out");
        } catch (IOException e) {
            System.err.println("Failed to write list to file.");
                    e.printStackTrace();
        }
    }

}