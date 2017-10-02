import java.io.*;
import java.util.*;

/** Created by errornosignal on 3/3/2017.
 * ReidNolan_HW2_[PhoneLookup]_[PROG_2413]_
 * Main Class
 * Program for reading data stored in a .csv file
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
public class Main
{
    //declare and initialize class variables
    private final static int kTIMER_DELAY = 250;
    private final static String kANSI_RED = "\u001B[31m";
    private final static String kANSI_RESET = "\u001B[0m";

    /**main method
     * @param args args
     * @throws MaximumColumnsExceededException MCEEx
     * @throws IOException IOEx
     * @throws InterruptedException IEx
     * @throws EmptyFileException EFEx
     */
    public static void main(String[] args) throws MaximumColumnsExceededException, IOException, InterruptedException,
            EmptyFileException
    {
        //display program header
        displayProgramHeader();

        //noinspection InfiniteLoopStatement
        while (true)
        {
            //display main menu
            displayMainMenu();

            //try/catch block 1
            try
            {
                //get main menu selection
                String mainMenuSelection = getUserInput_String(menuSelectionPrompt());

                //main menu switch
                switch (mainMenuSelection)
                {
                    case "1":   //case 1. Specify a file to open and read it's contents into memory.
                    {
                        //prompt for input and assign next string to variable
                        String fileToOpen = "PhoneBook.csv"; //getUserInput_String(fileToOpenPrompt());

                        //try/catch block 1.a
                        try (Scanner input = new Scanner(new BufferedReader(new FileReader(fileToOpen))))
                        {
                            //test file for content
                            if (input.hasNext())
                            {
                                //read file into memory
                                CSVReader.readFile(fileToOpen);
                            }
                            else
                            {
                                //close input file
                                input.close();
                                //throw custom exception for empty file
                                throw new EmptyFileException();
                            }
                        }
                        catch (IOException | MaximumColumnsExceededException | EmptyFileException IOEx)
                        {
                            //display error for thrown exception
                            System.err.println(IOEx.getMessage());
                            Thread.sleep(kTIMER_DELAY);
                            System.out.println();
                        }

                        boolean returnToMain = false;
                        while (!returnToMain)
                        {
                            //display main menu
                            displayPt1Menu();

                            //try/catch block 1
                            try
                            {
                                //get main menu selection
                                String Pt1MenuSelection = getUserInput_String(menuSelectionPrompt());
                                //main menu switch
                                switch (Pt1MenuSelection)
                                {
                                    case "1":   //case 1. Search by name
                                    {
                                        String nameToSearch = getUserInput_String("Enter the name to search: ");
                                        if(CSVReader.treeMap.containsKey(nameToSearch))
                                        {
                                            System.out.println();
                                            System.out.print(String.format("%-22s", nameToSearch));
                                            System.out.print(String.format("%-14s", CSVReader.treeMap.get(nameToSearch)));
                                            System.out.println();
                                            System.out.println();
                                        }
                                        else
                                        {
                                            //displays error message on match not found
                                            displayMatchNotFound();
                                        }
                                        break;
                                    }
                                    case "2":   //case 2. Search by number
                                    {
                                        String numberToSearch = getUserInput_String("Enter the number to search: ");
                                        if(CSVReader.treeMap.containsValue(numberToSearch))
                                        {
                                            System.out.println();
                                            System.out.print(String.format("%-22s", getKeyFromValue(CSVReader.treeMap, numberToSearch)));
                                            System.out.print(String.format("%-14s", numberToSearch));
                                            System.out.println();
                                            System.out.println();
                                        }
                                        else
                                        {
                                            //displays error message on match not found
                                            displayMatchNotFound();
                                        }
                                        break;
                                    }
                                    case "3":   //cse 3. display all records
                                    {
                                        //display all map values
                                        CSVReader.printFile();
                                        break;
                                    }
                                    case "4":
                                    {
                                        //go back to main menu
                                        System.out.println();
                                        returnToMain = true;
                                        break;
                                    }
                                    default:    //default case
                                    {
                                        //displays error message on invalid selection
                                        displayInvalidSelection();
                                    }
                                }
                            }
                            catch (InputMismatchException IMEx)
                            {
                                //display error message on invalid selection
                                displayInvalidSelection();
                                //display error for thrown exception
                                System.err.println(IMEx.getMessage());
                                Thread.sleep(kTIMER_DELAY);
                                System.out.println();
                            }
                        }
                        break;
                    }
                    case "2":   //case 2. Display the file's contents.
                    {
                        String fileToOpen = "RandomNames.txt"; //getUserInput_String(fileToOpenPrompt());

                        //try/catch block 1.a
                        try (Scanner input = new Scanner(new BufferedReader(new FileReader(fileToOpen))))
                        {
                            //test file for content
                            if (input.hasNext())
                            {
                                //read file into memory
                                StringSorter.readFile(fileToOpen);
                                StringSorter.printFile();
                            }
                            else
                            {
                                //close input file
                                input.close();
                                //throw custom exception for empty file
                                throw new EmptyFileException();
                            }
                        }
                        catch (IOException | MaximumColumnsExceededException | EmptyFileException IOEx)
                        {
                            //display error for thrown exception
                            System.err.println(IOEx.getMessage());
                            Thread.sleep(kTIMER_DELAY);
                            System.out.println();
                        }
                        break;
                    }
                    case "3":   //case 6. Exit the program.
                    {
                        //display message and terminate program
                        exitProgram();
                    }
                    default:    //default case
                    {
                        //displays error message on invalid selection
                        displayInvalidSelection();
                    }
                }
            } catch (InputMismatchException IMEx)
            {
                //display error message on invalid selection
                displayInvalidSelection();
                //display error for thrown exception
                System.err.println(IMEx.getMessage());
                Thread.sleep(kTIMER_DELAY);
                System.out.println();
            }
        }
    }

    /**
     * displays program header
     */
    private static void displayProgramHeader()
    {
        System.out.println("ReidNolan_HW2_[PhoneLookup]_[PROG_2413]" + "\n");
    }

    /**
     * displays main menu
     */
    private static void displayMainMenu()
    {
        System.out.println("-----Main Menu-----");
        System.out.println("1. Part One...");
        System.out.println("2. Part Two...");
        System.out.println("3. Exit the program...");
    }

    /**
     * displays part1 sub-menu
     */
    private static void displayPt1Menu()
    {
        System.out.println("-----Part1 Menu-----");
        System.out.println("1. Search By Name...");
        System.out.println("2. Search By Number...");
        System.out.println("3. Display all records...");
        System.out.println("4. Return to Main Menu...");
    }

    /**
     * displays error message on invalid selection
     */
    private static void displayInvalidSelection()
    {
        System.out.println();
        System.out.println(get_kANSI_RED() + "Error! Invalid selection. Try again." +  get_kANSI_RESET());
        System.out.println();
    }

    /**
     * displays error message on match not found
     */
    private static void displayMatchNotFound()
    {
        System.out.println();
        System.out.println(get_kANSI_RED() + "Error! Match not found.." +  get_kANSI_RESET());
        System.out.println();
    }

    /**
     * displays program exiting message and exits program
     */
    private static void exitProgram()
    {
        System.out.println("exiting program...");
        System.exit(1);
    }

    /**
     * gets kTIMER_DELAY value
     * @return kTIMER_DELAY
     */
    static int get_kTIMER_DELAY()
    {
        return kTIMER_DELAY;
    }

    /**
     * gets kANSI_RED value
     * @return ANSI_RED
     */
    static String get_kANSI_RED()
    {
        return kANSI_RED;
    }

    /**
     * gets kANSI_RESET value
     * @return ANSI_RESET
     */
    static String get_kANSI_RESET()
    {
        return kANSI_RESET;
    }

    /**
     * gets make a selection prompt
     * @return "Make a selection"
     */
    private static String menuSelectionPrompt()
    {
        return "Make a selection> ";
    }

    /**
     * gets String user input from scanner
     * @param prompt prompt
     * @return inputString
     */
    private static String getUserInput_String(String prompt)
    {
        //create new scanner
        Scanner sc = new Scanner(System.in);
        String inputString = "";
        //loop until string is valid
        boolean stringIsValid = false;
        while(!stringIsValid)
        {
            //prompt for input
            System.out.print(prompt);
            //assign next scanner input string to variable
            inputString = sc.nextLine();
            //verify input is entered
            if (inputString.isEmpty())  //if no input entered
            {
                //doNothing()
            }
            else    //if input entered
            {
                //set boolean to true to exit loop
                stringIsValid = true;
            }
        }
        return inputString;
    }

    /**
     * gets integer user input from scanner
     * @param prompt  prompt
     * @return inputInt
     * @throws InterruptedException NFEx
     */
    private static int getUserInput_int(String prompt) throws InterruptedException
    {
        //create new scanner
        Scanner sc = new Scanner(System.in);
        //declare and initialize local variable
        int inputInt = 0;

        //loop until input string is entered
        boolean stringIsValid = false;
        while(!stringIsValid)
        {
            try
            {
                //prompt for input
                System.out.print(prompt);
                //assign next scanner input string to variable
                String inputString = sc.nextLine();
                //verify input is entered
                if (inputString.isEmpty())  //if no input entered
                {
                    //doNothing()
                }
                else    //if input entered
                {
                    //convert string to integer
                    inputInt = Integer.parseInt(inputString);
                    //set boolean to true to exit loop
                    stringIsValid = true;
                }
            }
            catch(NumberFormatException NFEx)
            {
                //display error message on invalid selection
                displayInvalidSelection();
                Thread.sleep(kTIMER_DELAY);
            }
        }
        return inputInt;
    }

    /**
     * get key from value in map
     * @param hm hm
     * @param value value
     * @return o / null
     */
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}