PAGERANKING ALGORITHM

Description:
	This repository contains the source code that uses a trace.gdf file of a software system and creates a graph using hashmaps and then implements a pageranking algorithm to quantitatively describe the most central nodes (the files that have the most number of dependencies). The output is printed to an external text file.

How to Use

	1. Obtain the trace.gdf file for the software system under consideration using MAKAO. For more instructions on how to do that visit http://mcis.polymtl.ca/makao.html .

	2. In the config.properties file update the location of the trace.gdf file and the destination to store the results.

	3. The file structure follows an eclipse project so one way to run the program is to import it in eclipse

							OR

	3. Remove the first line "package AnalysisTools" from all source code files, then compile and run from terminal.
