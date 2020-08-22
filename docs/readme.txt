Application Programming in Java
Group Project
Dylan Ross
Margot Rajkovic
Zhangcheng Li

The project is structured as follows (relative to the parent directory of this file):
	/bin contains the compiled .class files
	/docs contains the javadoc output files, including index.html and other end-product documentation files
	/scripts contains executable scripts to compile, run, and generate documentation
	/src contains the source code
	/res contains resources used in this project, such as images and csv files

All code was developed and tested on Ubuntu 20.04 LTS and macOS 10.13.6. Windows scripts were written and tested on Windows 10 Education.
The code was developed and tested using JDK and JRE version 14.01.

To run the program, navigate to the /scripts folder and run the corresponding script for your operating system. 
Windows scripts have a .cmd extension while Linux/Mac scripts have a .sh extension.
	Example: ./run.sh 

The various scripts do as follows (.sh extension for Linux/Mac, .cmd extension for Windows):
	compile: compile the java source into .class files and store them in the /bin directory
	run: run compiled .class files from the /bin directory
	compileRun: both compile and run in one step
	generateDocs: generates javadoc files and stores them in /docs. Open /docs/index.html to view the output
