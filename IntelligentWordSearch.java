package intelligentwordsearch;

/*
 * @author: Mendel Groner
 * @since: 1/20/2021
 * @version: 1.0
 * <p>
 * @Description: Word Search.
 */


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.lang.InterruptedException;

public class IntelligentWordSearch {
    static File fileDirectory = new File("Enter the absolute path to the directory with all your .txt files here!");

    public static void main(String[] args) throws IOException, InterruptedException {
        
        String query = presentSearchQuery();
        MatchingFiles matchingFiles = searchIndex(query, gatherFiles());
        matchingFiles.sort();
        Path result = matchingFiles.compileResults();
        try (Scanner printer = new Scanner(result)) { // try-with-resource clause
            while (printer.hasNextLine()) {
                String line = printer.nextLine();
                System.out.println(line);
            }
        } catch (Exception e) { // if there is a problem closing the Scanner object
            System.err.printf("%s%n", e.getMessage());
        }
            for (int i = 0; i < 3; i++) {
                try {
                    Files.delete(result);
                } catch (Exception e) {
                    Thread.sleep(1000);
                }
            }
            if (Files.exists(result))
                System.out.println("The file has been successfully deleted.");
            else
                System.out.println("The file has not been deleted.");
        }

    public static String[] gatherFiles() {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };
        return fileDirectory.list(filter);
    }

    public static String presentSearchQuery() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What would you like to search?");
        String search = sc.next();
        sc.close();
        return search;
    }

    public static MatchingFiles searchIndex(String query, String[] files) throws IOException {
        MatchingFiles matchingFiles = new MatchingFiles(query);
        for (String file : files) {
            Path currentFile = Paths.get(fileDirectory + "/" + file);
            FileMatch fm = new FileMatch(currentFile);
            Scanner fileReader = new Scanner(currentFile);
            while (fileReader.hasNext()) {
                String currentWord = fileReader.next();
                if (currentWord.compareToIgnoreCase(query) == 0) {
                    fm.counter++;
                }
            }
            fileReader.close();
            if (fm.counter > 0) {
                fm.match = true;
                matchingFiles.add(fm);
            }
        }
        return matchingFiles;
    }
}
