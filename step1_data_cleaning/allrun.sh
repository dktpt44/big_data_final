#!/bin/bash

# compile
javac -classpath `yarn classpath` -d . CleanMapper.java
javac -classpath `yarn classpath` -d . CleanReducer.java
javac -classpath `yarn classpath`:. -d . Clean.java

jar -cvf prg.jar *.class

hadoop jar prg.jar Clean proj/input/covid_19_data.csv output