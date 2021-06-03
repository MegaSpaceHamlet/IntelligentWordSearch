package intelligentwordsearch;

/*
 * @author: Mendel Groner
 * @since: 1/20/2021
 * @version: 1.0
 * <p>
 * Description:
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileMatch {
    String name;
    Path file;
    int counter;
    boolean match;

    public FileMatch(Path file) {
        this.name = file.getFileName().toString();
        this.file = file;
    }
}

class MatchingFiles extends ArrayList<FileMatch> {
    String query;

    public MatchingFiles() {
    }

    public MatchingFiles(String query) {
        this.query = query;
    }

    /* Bubble sort method to sort MatchingFiles list to be fit for output.
    *  The method will sort the files so that the most frequent is first,
    *  and so on, in descending order. The first loop remains the same; it
    *  is just counting the amount of passes possible. That principle remains
    *  the same. The inner loop, however, has been slightly modified. It counts
    *  from the end of the list and pushes the greatest frequency to the front
    *  of the list. */
    public void sort() {
        FileMatch hold;
        int j, pass;
        boolean switched = true;

        for (pass = 0; pass < this.size() - 1 && switched; pass++) {
            // outer loop to control number of passes
            switched = false; // initially no interchanges have been made
            // made one this pass
            for (j = this.size() - 1; j > pass; j--) {
                // inner loop governs each pass
                if (this.get(j).counter > this.get(j - 1).counter) {
                    // elements are out of order; interchange is necessary
                    switched = true;
                    hold = this.get(j);
                    this.set(j, this.get(j - 1));
                    this.set(j - 1, hold);
                } // end if
            } // end inner loop
        } // end outer loop
    } // end sort method

    /* Method will print all files with their data nicely onto file. */
    public Path compileResults() throws IOException {
        Path results = Files.createTempFile("results", ".txt");
        PrintStream writer = new PrintStream("results.txt");
        if (this.size() == 0)
            writer.println("Sorry! Nothing was found.\n\n");
        else if (this.size() == 1)
            writer.println("The word " + this.query + " was found in one file.\n\n");
        else
            writer.println("The word " + this.query + " was found in " + this.size() + " files.\n\n");
        for (FileMatch file : this) {
            writer.println(file.name + "\n");
            if (file.counter > 1)
                writer.println(this.query + " found " + file.counter + " times.\n");
            else
                writer.println(this.query + " found once.\n");
            Scanner reader = new Scanner(file.file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                writer.println(line);
            }
            writer.println("\n---------------------------------------------------------------\n");
        }
        return results;
    }
}

