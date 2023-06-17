# Java console application using PostgreSQL
This is a console application which can perform simple queries using PostgreSQL with imported data. Application is designed to run within docker container.

## Prerequisites
These services should be running before using the application.

      - Docker
      - Docker Compose

## Setup
Todo list before the first launch of the application.
      
      - Clone this repository.
      - Download project-0.3.jar from repository assets.
      - Put it inside the target directory.
      - Open terminal in folder where you copied this repository.
      - To run this script us need to write in console this command:
            for Windows users:
                  .\scripts\windows\start.bat
            for Linux users:
                  ./scripts/linux/start.sh 

After running the script the application is ready to use.

## Application usage
List of commands which you can use in this application:
      
      help – displays all these commands.
      import – imports the data from a file to a database.
      exit – shuts down the application.
      task1 – finds all children of a given node.
      task2 – counts all children of a given node.
      task3 – finds all grandchildren of a given node.
      task4 – finds all parents of a given node.
      task5 – counts all parents of a given node.
      task6 – finds all grandparents of a given node.
      task7 – counts how many uniquely named nodes there are.
      task8 – finds a root nodes.
      task9 – finds nodes with the most children.
      task10 – finds nodes with the least children.
      task11 – renames a given node.

After exiting the app the postgreSQL container is still running. To close it you can run the following script:

      for Windows users:
            .\scripts\windows\stop.bat
      for Linux users:
            ./scripts/linux/stop.sh

## Data import
The .csv file should be named "taxonomy_iw.csv", and should be placed in:

      ./src/main/resource/app/databases

Every single row syntax in taxonomy_iw.csv file should look like this:

      "parent_node","child_node"

## Software
      Java: openjdk:17
      PostgreSQL: postgres:15
