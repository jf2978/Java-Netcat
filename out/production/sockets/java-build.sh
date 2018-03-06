#!/usr/bin/env bash

# compiling all java source files 
find -name "*.java" > sources.txt
javac @sources.txt
	
# run
java MySockets -l localhost 8080