import java.io.*;
import java.util.*;

/** Created by errornosignal on 3/3/2017.
 * ReidNolan_HW2_[StringSorter]_[PROG_2413]
 * StringSorter Class
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
class StringSorter
{
    //declare and initialize class variables
    private static int lineCount = 0;

    //create map to hold data from input file
    public static List<String> list = new ArrayList<>();

    /**
     * reads file into memory and parses data
     * @param fileToOpen fileToOpen
     * @throws MaximumColumnsExceededException MCEEx
     * @throws IOException IOEx
     * @throws InterruptedException IEx
     */
    static void readFile(String fileToOpen) throws MaximumColumnsExceededException, IOException, InterruptedException
    {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileToOpen))))
        {
            while (scanner.hasNextLine())
            {
                String nextEntry = scanner.nextLine();
                list.add(nextEntry);
                lineCount++;
            }
            scanner.close();
            displayFileReadConfirmation();
            //System.out.print("count = " + count);
        }
    }

    /**
     * prints formatted file contents file
     * @throws IOException IOEx
     */
    static void printFile() throws IOException
    {
        File file = new File("RandomNamesOut.txt");
        try
        {
            FileWriter fileWriter = new FileWriter(file,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //test for data in memory
            if (lineCount > 0)
            {
                for (String entry : list)
                {
                    //print output to file
                    bufferedWriter.write(entry + "\n");
                }

                bufferedWriter.close();

                //System.out.println("lineCount = " + lineCount);
                System.out.println(lineCount + " entries written to " + file + "\n");
            }
            else
            {
                //display message for no data in memory
                displayNoData();
            }
        }
        catch(IOException IOEx)
        {
            System.err.println("IOException: " + IOEx.getMessage());
        }
    }

    /**
     * displays no data in memory status message
     */
    private static void displayNoData()
    {
        System.out.println("\n" + Main.get_kANSI_RED() + "Error! No data exists in memory." + Main.get_kANSI_RESET() + "\n");
    }

    /**
     * displays file read successfully status message
     */
    private static void displayFileReadConfirmation()
    {
        System.out.println("\n" + "The selected file has been successfully read." + "\n");
    }

    /**
     * displays data output header
     */
    private static void displayDataOutputHeader()
    {
        System.out.println("\n" + "The file contains the following data:");
    }
}