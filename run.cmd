@echo off

@REM CALL javac -cp .:lib/jansi-2.4.0.jar -d ./bin ./src/*.java ./src/model/*.java ./src/utils/*.java ./src/solver/algorithm/*.java ./src/solver/heuristic/*.java
@REM cd ./bin
@REM CALL java -cp .:lib/jansi-2.4.0.jar Main

CALL javac -d ./bin ./src/*.java ./src/model/*.java ./src/utils/*.java ./src/solver/algorithm/*.java ./src/solver/heuristic/*.java
cd ./bin
CALL java Main