@echo off

CALL javac -d ./bin ./src/*.java ./src/puzzle/*.java
cd ./bin
CALL java Main
