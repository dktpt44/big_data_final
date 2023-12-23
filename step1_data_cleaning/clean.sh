#!/bin/bash

rm *.class 
rm *.jar
hdfs dfs -rm -r -f hw
hdfs dfs -rm -r output