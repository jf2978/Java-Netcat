#!/usr/bin/env bash

# compiling all java source files 
find -name "*.java" > sources.txt
javac @sources.txt