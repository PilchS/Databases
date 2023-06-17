# Console application using PostgreSQL
This is a console application which can perform simple queries using PostgreSQL using imported data. Application is designed to run within docker container.

## Prerequisites
-	Docker 
-	Docker container
Both of them should be running

## Setup
1.	Clone this repository 
2.	Download project-0.2.jar from assets folder
3.	Put it in target catalogue 
4.	Open terminal in folder where you copied this repository
5.	To run this script us need to write in console this command:

      For windows:
./scripts/windows/start.bat

     For Linux:
./scripts/linux/start.sh 

6.	After running the script the application is ready to use

## Application usage
There is a list of commands which you can use in this application:
1)	help – displays all of the commands
2)	import – imports data from a file to a database
3)	quit – shuts down the application
4)	task1 – finds all children of a given node
5)	task2 – counts all children of a given node
6)	task3 – finds all grandchildren of a given node
7)	task4 – finds all parents of a given node
8)	task5 – counts all parents of a given node
9)	task6 – finds all grandparents of a given node
10)	task7 – counts how many uniquely named nodes there are
11)	task8 – finds a root node, one which is not a subcategory of any other node
12)	task9 – finds nodes with the most children, there could be more the one
13)	task10 – finds nodes with the least children, there could be more the one
14)	task11 – renames a given node
## Data import
The csv file should be named “taxonomy_iw.csv” and it should be placed in:
src/main/resource/app/databases
The taxonomy_iw.csv syntax should look like this:
"2000s_plays","2002_plays"
"2002_in_theatre","2002_plays"
"2002_works","2002_plays"
"Plays_by_year","2002_plays"
"2000s_plays","2001_plays"
"2001_in_theatre","2001_plays"
"2001_works","2001_plays"
"Plays_by_year","2001_plays"
"2000_in_theatre","2000_plays"
"2000_works","2000_plays"
"2000s_plays","2000_plays"
"Plays_by_year","2000_plays"
"1990s_plays","1999_plays"
"1999_in_theatre","1999_plays"
"1999_works","1999_plays"
"Plays_by_year","1999_plays"
"1990s_plays","1997_plays"
"1997_in_theatre","1997_plays"
"1997_works","1997_plays"
"Plays_by_year","1997_plays"
"1990s_plays","1995_plays"

## Software
-	Java: openjdk17
-	PostgreSQL 15
