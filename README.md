﻿# Java console application using PostgreSQL
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
      - It is strongly recommended to change the charset for your terminal to UTF-8 before launching the application.
      - To start using the app you need to run this scripts located in this directories:
            for Windows users:
                  ./scripts/windows/start.bat
            for Linux users:
                  ./scripts/linux/start.sh 

After running the script the application is ready to use.

## Application usage
List of commands which you can use in this application:
      
      help – displays all these commands.
      exit – shuts down the application.
      import – imports the data from a file to a database.
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
            ./scripts/windows/stop.bat
      for Linux users:
            ./scripts/linux/stop.sh

## Data import
The .csv file should be named "taxonomy_iw.csv", and should be placed in:

      ./src/main/resource/app/databases

Every single row syntax in taxonomy_iw.csv file should look like this:

      "parent_node","child_node"

## Hardware
      CPU: AMD Ryzen 7 3700X 8-Core (3.6GHz base speed)
      GPU: AMD Radeon RX 6600 XT (8GB VRAM)
      RAM: Kingston FURY 2x8GB 3200MHz (running on 3000MHz with lower CL)
      STORAGE: Samsung SSD Evo PLUS 970 250GB (M.2 connector)

## Software
      Java: openjdk:17
      PostgreSQL: postgres:15

## Times
      import:     max 104009 miliseconds
      task1:      max 417 miliseconds
      task2:      max 314 miliseconds
      task3:      max 1572 miliseconds
      task4:      max 315 miliseconds
      task5:      max 320 miliseconds
      task6:      max 312 miliseconds
      task7:      max 5165 miliseconds
      task8:      max 5044 miliseconds
      task9:      max 4234 miliseconds
      task10:     max 24788 miliseconds
      task11:     max 377 miliseconds
All the tasks were performed using 'Years' node (printing out time included).
