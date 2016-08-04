=========================================================================================================
Monte Carlo Localization Simulator
=========================================================================================================

Contributors: Miralda Rodney, Savanh Lu, Stephen Kristen, Julian Pryde

Tags: robot simulation, MCL, java

Requirements: Java SE Runtime Environment 8 or above

Tested on: jre 1.8.0_73 and jre 1.8.0_60

=========================================================================================================
Description
=========================================================================================================

The Monte Carlo Localization Simulator is a java application that allows you to simulate a robot crossing a field of restricted area with different reference point locations to symbolize "obstacles". The simulation allows the user to alter the environment and the capabilities of the robot in its movement and its sensing. The user can alter the robot's movement by deciding the standard deviation of error every time the robot advances to the next location; the amount of error the sensor the sensor will have along a defined range for the sensor. The user can also alter the physical environment by changing the amount of reference point locations in the field and deciding the start and end locations of the robot. While moving through the field of obstacles, the robot will make estimates of its position relative to the obstacles that it senses.

=========================================================================================================
Installation/Execution
=========================================================================================================

To execute the monte carlo locatization simulator using the .jar file, the user needs to download the project zip file. The file contains the file MCL.jar, which will allow the user to execute the program. In order for the user to properly execute the prgram, the user needs a java 8 runtime environment installed on their system.

To execute the Monte Carlo Localization Simulator using the java source files, the user needs to download the project zip file. Without changing the configuration of the files in the project folder, the user can open up the eclipse IDE and import the project from the development environment into the current workspace. To run the program, the user needs to configure the build path of the project in their IDE by adding the included json .jar files from the project folder and any external .jar files from their system to run the program. Once that is complete, the user can run the program as a java application from the main file of the program.

Requires: Eclipse IDE and JRE 8 or above
