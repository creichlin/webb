#!/bin/bash

java -jar /usr/share/java/antlr-4.2-complete.jar -package ch.kerbtier.webb.transform2d.parser -visitor -no-listener src/ch/kerbtier/webb/transform2d/parser/T2d.g4 
