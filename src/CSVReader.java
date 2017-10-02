import java.io.*;
import java.util.*;

/** Created by errornosignal on 3/3/2017.
 * ReidNolan_HW2_[CSVReader]_[PROG_2413]
 * CSVReader Class
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
class CSVReader
{
    //declare and initialize class variables
    private static int lineCount = 0;

    //create map to hold data from input file
    public static Map<String, String> treeMap = new TreeMap<>();

    /**
     * reads file into memory and parses data
     * @param fileToOpen fileToOpen
     * @throws MaximumColumnsExceededException MCEEx
     * @throws IOException IOEx
     * @throws InterruptedException IEx
     */
    static void readFile(String fileToOpen) throws MaximumColumnsExceededException, IOException, InterruptedException
    {
        //initialize counter for rows
        lineCount = 0;

        //try/catch block
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileToOpen))))
        {
            //specify delimiter
            scanner.useDelimiter(",");

            //get input until end of file
            while (scanner.hasNext())
            {
                String s2 = scanner.next();

                //add next line to string
                String s1 = scanner.nextLine();

                //split string into separate elements, preserving protected fields, and add to array
                String[] s1_string = s1.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                //remove leading and trailing double quotes from string, preserving internal double quotes and commas
                for (int i = 0; i < s1_string.length; i++)
                {
                    if (s1_string[i].startsWith("\""))
                    {
                        s1_string[i] = s1_string[i].substring(1, s1_string[i].length());
                    }
                    else
                    {
                        //doNothing()
                    }
                    if (s1_string[i].endsWith("\""))
                    {
                        s1_string[i] = s1_string[i].substring(0, s1_string[i].length() - 1);
                    }
                    else
                    {
                        //doNothing()
                    }
                }

                //throw custom exception for max columns exceeded
                if(s1_string.length > MaximumColumnsExceededException.get_kMAX_COLUMNS())
                {
                    throw new MaximumColumnsExceededException();
                }
                else
                {
                    //doNothing()
                }

                //add key-value pair to map
                treeMap.put(s2,s1_string[1]);

                //increment line count
                lineCount++;
            }
            //close input file
            scanner.close();
            //displays file read successfully status message
            displayFileReadConfirmation();
        }
        catch(MaximumColumnsExceededException MCEEx)
        {
            //display error for thrown exception
            System.err.println(MCEEx.getMessage());
            Thread.sleep(Main.get_kTIMER_DELAY());
            System.out.println();
        }
    }

    /**
     * prints formatted file contents to console
     */
    static void printFile()
    {
        //test for data in memory
        if(lineCount > 0)
        {
            //display file data formatted with header
            displayDataOutputHeader();
            System.out.println("//Beginning of file//");
            for (Map.Entry entry : treeMap.entrySet())
            {
                System.out.print(String.format("%-20s", entry.getKey()));
                System.out.print(String.format("%-14s", entry.getValue()));
                System.out.print("\n");
            }

            System.out.println("//End of file//");
            System.out.println();
        }
        else
        {
            //display message for no data in memory
            displayNoData();
        }
    }

    /**
     * displays no data in memory status message
     */
    private static void displayNoData()
    {
        System.out.println("\n" + Main.get_kANSI_RED() + "Error! No data exists in memory." + Main.get_kANSI_RESET());
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