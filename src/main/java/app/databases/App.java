package app.databases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

class App
{
    private static String[] commands = new String[0];
    private static Scanner sc = new Scanner(System.in);
    private static void initialize()
    {
        for (int m = 1; m < 12; m++) {App.commands = Arrays.copyOf(App.commands, App.commands.length + 1); App.commands[App.commands.length - 1] = "task" + m;}
        App.commands = Arrays.copyOf(App.commands, App.commands.length + 1); App.commands[App.commands.length - 1] = "exit";
        App.commands = Arrays.copyOf(App.commands, App.commands.length + 1); App.commands[App.commands.length - 1] = "help";
        App.commands = Arrays.copyOf(App.commands, App.commands.length + 1); App.commands[App.commands.length - 1] = "import";
        System.out.print("Application is running and ready to go!\n");
    }
    private static void showCommands()
    {
        System.out.print("There are " + App.commands.length + " available commands.\n");
        for (int m = 0; m < App.commands.length; m++) {System.out.print(App.commands[m] + "  ");}
        System.out.print("\n\n");
    }
    private static void data_import() throws Exception
    {
        System.out.print("Preparing the database\t"); PreparedStatement stmt = null;
        Connection conn = DriverManager.getConnection("jdbc:postgresql://database:5432/postgres", "postgres", "password");
        try
        {
            stmt = conn.prepareStatement("drop database dbproject"); stmt.execute();
            stmt = conn.prepareStatement("create database dbproject with encoding utf8"); stmt.execute();
        }
        catch (Exception e)
        {
            stmt = conn.prepareStatement("create database dbproject with encoding utf8"); stmt.execute();
        }
        conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");
        try
        {
            stmt = conn.prepareStatement("drop table relations"); stmt.execute();
            stmt = conn.prepareStatement("create table relations (parent character varying(255), child character varying(255))"); stmt.execute();
        }
        catch (Exception e)
        {
            stmt = conn.prepareStatement("create table relations (parent character varying(255), child character varying(255))"); stmt.execute();
        }
        System.out.print("\tDone!\nImporting the data\t");
        stmt = conn.prepareStatement("insert into relations (parent, child) values (?, ?)");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data.csv"), "UTF-8"));
        String line; String[] temp; String[] relation = new String[2]; int counter = 0;
        while ((line = br.readLine()) != null)
        {
            temp = line.split("\"*\",\"*\"");
            relation[0] = temp[0].substring(1, temp[0].length()); relation[1] = temp[1].substring(0, temp[1].length() - 1);
            stmt.setString(1, relation[0]); stmt.setString(2, relation[1]); stmt.addBatch();
            if (counter == 1000000) {stmt.executeBatch(); counter = 0;} counter += 1;
        }
        stmt.executeBatch(); br.close();
        System.out.print("\tDone!\nCreating the indexes\t");
        stmt = conn.prepareStatement("create index parent_idx on relations (parent)"); stmt.execute();
        stmt = conn.prepareStatement("create index child_idx on relations (child)"); stmt.execute();
        System.out.print("\tDone!\n"); conn.close();
    }
    private static void task1(String node) throws Exception
    {
        if (node.contains("'")) 
        {
            String temp = "";
            for (int m = 0; m < node.length(); m++)
            {
                if (node.charAt(m) == "'".charAt(0)) {temp += "'";}
                temp += node.charAt(m);
            }
            node = temp;
        }
        System.out.print("Finding all children of a given node...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
		PreparedStatement stmt = conn.prepareStatement("select child from relations where parent = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); System.out.print("All children of a given node: \n\n");
        while (rs.next()) {System.out.print(rs.getString("child") + "  ");} System.out.print("\n"); conn.close();
    }
    private static void task2(String node) throws Exception
    {
        if (node.contains("'")) 
        {
            String temp = "";
            for (int m = 0; m < node.length(); m++)
            {
                if (node.charAt(m) == "'".charAt(0)) {temp += "'";}
                temp += node.charAt(m);
            }
            node = temp;
        }
        System.out.print("Finding the number of children of a given node...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
		PreparedStatement stmt = conn.prepareStatement("select count(child) from relations where parent = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); rs.next(); System.out.print("Number of the children found: " + rs.getInt("count") + "\n"); conn.close();
    }
    private static void task3(String node) throws Exception
    {
        System.out.print("Finding all grandchildren of a given node...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
		PreparedStatement stmt = conn.prepareStatement("select child from relations where parent = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); System.out.print("All grandchildren of a given node: \n\n"); ResultSet rs2;
        while (rs.next())
        {
            node = rs.getString("child");
            if (node.contains("'"))
            {
                String temp = "";
                for (int m = 0; m < node.length(); m++)
                {
                    if (node.charAt(m) == "'".charAt(0)) {temp += "'";}
                    temp += node.charAt(m);
                }
                node = temp;
            }
            stmt = conn.prepareStatement("select child from relations where parent = '" + node + "'");
            rs2 = stmt.executeQuery(); while (rs2.next()) {System.out.print(rs2.getString("child") + "  ");}
        }
        System.out.print("\n"); conn.close();
    }
    private static void task4(String node) throws Exception
    {
        if (node.contains("'")) 
        {
            String temp = "";
            for (int m = 0; m < node.length(); m++)
            {
                if (node.charAt(m) == "'".charAt(0)) {temp += "'";}
                temp += node.charAt(m);
            }
            node = temp;
        }
        System.out.print("Finding all parents of a given node...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
		PreparedStatement stmt = conn.prepareStatement("select parent from relations where child = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); System.out.print("All parents of a given node: \n\n");
        while (rs.next()) {System.out.print(rs.getString("parent") + "  ");} System.out.print("\n"); conn.close();
    }
    private static void task5(String node) throws Exception
    {
        if (node.contains("'"))
        {
            String temp = "";
            for (int m = 0; m < node.length(); m++)
            {
                if (node.charAt(m) == "'".charAt(0)) {temp += "'";}
                temp += node.charAt(m);
            }
            node = temp;
        }
        System.out.print("Finding the number of parents of a given node...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
		PreparedStatement stmt = conn.prepareStatement("select count(parent) from relations where child = '" + node + "'");
        ResultSet rs = stmt.executeQuery();  rs.next(); System.out.print("Number of the parents found: " + rs.getInt("count") + "\n"); conn.close();
    }
    private static void task6(String node) throws Exception
    {
        System.out.print("Finding all grandparents of a given node...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
		PreparedStatement stmt = conn.prepareStatement("select parent from relations where child = '" + node + "'");
        ResultSet rs = stmt.executeQuery(); System.out.print("All grandparents of a given node: \n\n"); ResultSet rs2;
        while (rs.next())
        {
            node = rs.getString("parent");
            if (node.contains("'"))
            {
                String temp = "";
                for (int m = 0; m < node.length(); m++)
                {
                    if (node.charAt(m) == "'".charAt(0)) {temp += "'";}
                    temp += node.charAt(m);
                }
                node = temp;
            }
            stmt = conn.prepareStatement("select parent from relations where child = '" + node + "'");
            rs2 = stmt.executeQuery(); while (rs2.next()) {System.out.print(rs2.getString("parent") + "  ");}
        }
        System.out.print("\n"); conn.close();
    }
    private static void task7() throws Exception
    {
        System.out.print("Finding the number of all uniquely named nodes...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
        PreparedStatement stmt = conn.prepareStatement("select count(rows) from (select child from relations group by child union select parent from relations group by parent) as rows");
        ResultSet rs = stmt.executeQuery(); rs.next(); System.out.print("Number of the nodes found: " + rs.getInt("count") + "\n"); conn.close();
    } 
    private static void task8() throws Exception
    {
        System.out.print("Finding the root nodes...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
        PreparedStatement stmt = conn.prepareStatement("select parent from relations group by parent except select child from relations group by child order by parent");
        ResultSet rs = stmt.executeQuery(); System.out.print("All root nodes found: \n\n");
        while (rs.next()) {System.out.print(rs.getString("parent") + "  ");} System.out.print("\n"); conn.close();
    }
    private static void task9() throws Exception
    {
        System.out.print("Finding nodes with the most children...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
        PreparedStatement stmt = conn.prepareStatement("select parent, count(child) from relations group by parent order by count desc");
        ResultSet rs = stmt.executeQuery(); boolean flag = false; int number = 0; System.out.print("Nodes with the most children: \n\n");
        while (rs.next())
        {
            if (flag == false) {System.out.print(rs.getString("parent") + "\t"); number = rs.getInt("count"); flag = true;}
            else {if (number == rs.getInt("count")) {System.out.print(rs.getString("parent") + "  ");}}
        }
        System.out.print("\n"); conn.close();
    }
    private static void task10() throws Exception
    {
        System.out.println("Finding nodes with the least children...\n\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;}
        PreparedStatement stmt = conn.prepareStatement("select child from relations group by child except select parent from relations group by parent");
        ResultSet rs = stmt.executeQuery(); boolean flag = false; int number = 0; System.out.print("Nodes with the least children: \n\n");
        while (rs.next()) {if (flag == false) {flag = true;} System.out.print(rs.getString("child") + "  ");}
        if (flag == false)
        {
            stmt = conn.prepareStatement("select parent, count(child) from relations group by parent order by count asc"); rs = stmt.executeQuery();
            while (rs.next())
            {
                if (flag == false) {System.out.print(rs.getString("child") + "  "); number = rs.getInt("count"); flag = true;}
                else {if (number == rs.getInt("count")) {System.out.println(rs.getString("child") + "  ");}}
            }
        }
        System.out.print("\n"); conn.close();
    }
    private static void task11(String old_node, String new_node) throws Exception
    {
        if (old_node.contains("'")) 
        {
            String temp = "";
            for (int m = 0; m < old_node.length(); m++)
            {
                if (old_node.charAt(m) == "'".charAt(0)) {temp += "'";}
                temp += old_node.charAt(m);
            }
            old_node = temp;
        }
        if (new_node.contains("'")) 
        {
            String temp = "";
            for (int m = 0; m < new_node.length(); m++)
            {
                if (new_node.charAt(m) == "'".charAt(0)) {temp += "'";}
                temp += new_node.charAt(m);
            }
            new_node = temp;
        }
        int counter = 0; System.out.println("Renaming given node...\n"); Connection conn = null;
        try {conn = DriverManager.getConnection("jdbc:postgresql://database:5432/dbproject", "postgres", "password");}
        catch (Exception e) {System.out.print("No data imported yet\n\n"); return;} conn.setAutoCommit(false);
		PreparedStatement stmt = conn.prepareStatement("update relations set parent = '" + new_node + "' where parent = '" + old_node + "'");
        counter += stmt.executeUpdate();
        stmt = conn.prepareStatement("update relations set child = '" + new_node + "' where child = '" + old_node + "'");
        counter += stmt.executeUpdate();
        conn.commit(); System.out.println("Number of updated rows: " + counter); conn.close();
    }
    public static void main(String[] args) throws Exception
    {
        initialize(); String line; System.out.print("Type the command you use wish to use (check 'help' for all available commands).\n\n");
        while (true)
        {
            System.out.print("Your command: "); line = App.sc.nextLine();
            if (line.equalsIgnoreCase("task1"))
            {
                System.out.print("Select a node: "); String node = App.sc.nextLine();
                Date started = new Date(); App.task1(node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task2"))
            {
                System.out.print("Select a node: "); String node = App.sc.nextLine();
                Date started = new Date(); App.task2(node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task3"))
            {
                System.out.print("Select a node: "); String node = App.sc.nextLine();
                Date started = new Date(); App.task3(node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task4"))
            {
                System.out.print("Select a node: "); String node = App.sc.nextLine();
                Date started = new Date(); App.task4(node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task5"))
            {
                System.out.print("Select a node: "); String node = App.sc.nextLine();
                Date started = new Date(); App.task5(node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task6"))
            {
                System.out.print("Select a node: "); String node = App.sc.nextLine();
                Date started = new Date(); App.task6(node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task7"))
            {
                Date started = new Date(); App.task7(); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task8"))
            {
                Date started = new Date(); App.task8(); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task9"))
            {
                Date started = new Date(); App.task9(); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task10"))
            {
                Date started = new Date(); App.task10(); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("task11"))
            {
                System.out.print("Select a node: "); String old_node = App.sc.nextLine();
                System.out.print("Select a new name for a given node: "); String new_node = App.sc.nextLine();
                Date started = new Date(); App.task11(old_node, new_node); Date finished = new Date();
                System.out.print("\nTask completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("import"))
            {
                Date started = new Date(); App.data_import(); Date finished = new Date();
                System.out.print("\nProcess completed after: " + (finished.getTime() - started.getTime()) + "ms\n\n");
            }
            else if (line.equalsIgnoreCase("exit")) {break;}
            else if (line.equalsIgnoreCase("help")) {App.showCommands();}
            else {System.out.print("Unrecognized command (check 'help' for all available commands).\n\n");}
        }
    }
}