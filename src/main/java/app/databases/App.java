package app.databases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class App
{
    private static String[] commands = new String[0];
    private static void initialize()
    {
        for (int m = 1; m < 10; m++)
        {
            commands = Arrays.copyOf(commands, commands.length + 1);
            commands[commands.length - 1] = "task" + m;
        }
        commands = Arrays.copyOf(commands, commands.length + 1);
        commands[commands.length - 1] = "quit";
        // more code in here...
    }
    private static void showCommands()
    {
        System.out.println("There are " + commands.length + " available commands.");
        System.out.println(Arrays.toString(commands));
    }
    public static void main(String[] args) throws Exception
    {
        initialize();
        System.out.println("App successfully started. Type in command you use wish to use. (check 'help' for all available commands)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); String line;
        while (true)
        {
            System.out.print("Your command: ");
            line = br.readLine();
            if (line.contains("task1")) {}
            else if (line.contains("task2")) {}
            else if (line.contains("task3")) {}
            else if (line.contains("task4")) {}
            else if (line.contains("task5")) {}
            else if (line.contains("task6")) {}
            else if (line.contains("task7")) {}
            else if (line.contains("task8")) {}
            else if (line.contains("task9")) {}
            else if (line.contains("quit")) {break;}
            else if (line.contains("help")) {System.out.println("Available commends: "); showCommands();}
            else {System.out.println("Unrecognized command. Check 'help' for all available commands.");}
        }
    }
}