@echo off

CALL javac -d ./bin ./src/*.java ./src/model/*.java ./src/utils/*.java
cd ./bin
CALL java Main
