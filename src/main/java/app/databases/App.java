package app.databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

class App
{
    private static String postgrespass = getPass();
    private static String[] commands = new String[0];
    private static Scanner sc = new Scanner(System.in);
    private static String getPass()
    {
        Properties prop = new Properties();
        String fileName = "postgres.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {prop.load(fis);}
        catch (Exception e) {e.printStackTrace(); return null;}
        return prop.getProperty("postgres.pass");
    }
    private static void initialize(Date started) throws Exception
    {
        System.out.println("Initialization process started...");
        // importing script.sql to local postgres //
        for (int m = 1; m < 12; m++)
        {
            App.commands = Arrays.copyOf(App.commands, App.commands.length + 1);
            App.commands[App.commands.length - 1] = "task" + m;
        }
        App.commands = Arrays.copyOf(App.commands, App.commands.length + 1);
        App.commands[App.commands.length - 1] = "quit";
        App.commands = Arrays.copyOf(App.commands, App.commands.length + 1);
        App.commands[App.commands.length - 1] = "help";
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
		PreparedStatement stmt1 = conn.prepareStatement("insert into dbproject.relations (parent, child) values (?, ?)");
        PreparedStatement stmt2 = conn.prepareStatement("insert into dbproject.nodes (node) values (?)");
        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/app/databases/taxonomy_iw.csv")));
        String line;
        while ((line = br.readLine()) != null)
        {
            String[] relation = line.split(",");
            stmt1.setString(1, relation[0]); stmt1.setString(2, relation[1]); stmt1.executeUpdate();
            for (int m = 0; m < 2; m++) {stmt2.setString(1, relation[m]); stmt2.executeUpdate();}
        }
        br.close();
        Date finished = new Date();
        System.out.println("Application successfully initialized after: " + (finished.getTime() - started.getTime()) + "ms");
    }
    private static void showCommands()
    {
        System.out.println("There are " + App.commands.length + " available commands.");
        System.out.println(Arrays.toString(App.commands));
    }
    private static String[] task1() throws Exception
    {
        System.out.print("Select a node: "); String node = App.sc.nextLine();
        System.out.println("Finding all children of a given node...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
		PreparedStatement stmt = conn.prepareStatement("select * from dbproject.relations where parent = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); String[] children = new String[0];
        while (rs.next())
        {
            children = Arrays.copyOf(children, children.length + 1);
            children[children.length - 1] = rs.getString("child");
        }
        return children;
    }
    private static int task2() throws Exception
    {
        return (App.task1()).length;
    }
    private static String[] task3() throws Exception
    {
        String[] children = App.task1(); String[] grandchildren = new String[0];
        System.out.println("Finding all grandchildren of a given node...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
        PreparedStatement stmt = conn.prepareStatement("select * from dbproject.relations where parent = (?)");
        for (int m = 0; m < children.length; m++)
        {
            stmt.setString(1, children[m]); ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                grandchildren = Arrays.copyOf(grandchildren, grandchildren.length + 1);
                grandchildren[grandchildren.length - 1] = rs.getString("child");
            }
        }
        return grandchildren;
    }
    private static String[] task4() throws Exception
    {
        System.out.print("Select a node: "); String node = App.sc.nextLine();
        System.out.println("Finding all parents of a given node...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
		PreparedStatement stmt = conn.prepareStatement("select * from dbproject.relations where child = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); String[] parents = new String[0];
        while (rs.next())
        {
            parents = Arrays.copyOf(parents, parents.length + 1);
            parents[parents.length - 1] = rs.getString("parent");
        }
        return parents;
    }
    private static int task5() throws Exception
    {
        return (App.task4()).length;
    }
    private static String[] task6() throws Exception
    {
        String[] parents = App.task4(); String[] grandparents = new String[0];
        System.out.println("Finding all grandparents of a given node...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
        PreparedStatement stmt = conn.prepareStatement("select * from dbproject.relations where child = (?)");
        for (int m = 0; m < parents.length; m++)
        {
            stmt.setString(1, parents[m]); ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                grandparents = Arrays.copyOf(grandparents, grandparents.length + 1);
                grandparents[grandparents.length - 1] = rs.getString("parent");
            }
        }
        return grandparents;
    }
    private static int task7() throws Exception
    {
        int counter = 0; System.out.println("Finding all uniquely named nodes...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
        PreparedStatement stmt = conn.prepareStatement("select node from dbproject.nodes group by node");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {counter++;}
        return counter;
    }
    private static String[] task9() throws Exception
    {
        System.out.println("Finding node/nodes with the most children...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
        PreparedStatement stmt = conn.prepareStatement("select parent, count(child) as number from dbproject.relations group by parent order by number desc");
        ResultSet rs = stmt.executeQuery(); String[] nodes = new String[1]; boolean flag = true; int number = 0;
        while (rs.next())
        {
            if (flag) {nodes[0] = rs.getString("parent"); number = rs.getInt("number"); flag = false;}
            else 
            {
                if (number == rs.getInt("number"))
                {
                    nodes = Arrays.copyOf(nodes, nodes.length + 1);
                    nodes[nodes.length - 1] = rs.getString("parent");
                }
            }
        }
        return nodes;
    }
    private static String[] task10() throws Exception
    {
        System.out.println("Finding node/nodes with the most children...\n");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", App.postgrespass);
        PreparedStatement stmt = conn.prepareStatement("select parent, count(child) as number from dbproject.relations group by parent order by number asc");
        ResultSet rs = stmt.executeQuery(); String[] nodes = new String[1]; boolean flag = true; int number = 0;
        while (rs.next())
        {
            if (flag) {nodes[0] = rs.getString("parent"); number = rs.getInt("number"); flag = false;}
            else 
            {
                if (number == rs.getInt("number"))
                {
                    nodes = Arrays.copyOf(nodes, nodes.length + 1);
                    nodes[nodes.length - 1] = rs.getString("parent");
                }
            }
        }
        return nodes;
    }
    public static void main(String[] args) throws Exception
    {
        initialize(new Date()); String line;
        System.out.println("Type in command you use wish to use. (check 'help' for all available commands)");
        while (true)
        {
            System.out.print("Your command: "); line = App.sc.nextLine();
            if (line.contains("task1")) {System.out.println(Arrays.toString(App.task1()));}
            else if (line.contains("task2")) {System.out.println(App.task2());}
            else if (line.contains("task3")) {System.out.println(Arrays.toString(App.task3()));}
            else if (line.contains("task4")) {System.out.println(Arrays.toString(App.task4()));}
            else if (line.contains("task5")) {System.out.println(App.task5());}
            else if (line.contains("task6")) {System.out.println(Arrays.toString(App.task6()));}
            else if (line.contains("task7")) {System.out.println(App.task7());}
            else if (line.contains("task8")) {}
            else if (line.contains("task9")) {System.out.println(Arrays.toString(App.task9()));}
            else if (line.contains("task10")) {System.out.println(Arrays.toString(App.task10()));}
            else if (line.contains("task11")) {}
            else if (line.contains("quit")) {break;}
            else if (line.contains("help")) {System.out.println("Available commends: "); showCommands();}
            else {System.out.println("Unrecognized command. Check 'help' for all available commands.");}
            System.out.println("\n");
            // Date started = new Date(); Date finished = new Date(); System.out.println("Query completed in: " + (finished.getTime() - started.getTime()) + "ms.");
        }
    }
}