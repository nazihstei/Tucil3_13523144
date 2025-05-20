@echo off

@REM CALL javac -cp .:lib/jansi-2.4.0.jar -d ./bin ./src/*.java ./src/model/*.java ./src/utils/*.java ./src/solver/algorithm/*.java ./src/solver/heuristic/*.java
@REM cd ./bin
@REM CALL java -cp .:lib/jansi-2.4.0.jar Main

@REM Clear bin
rmdir /s /q "bin/"

@REM Cari semua file .java
dir /s /b src\*.java > sources.txt

@REM Compile semua file ke bin/
javac -d bin -cp bin @sources.txt

@REM Hapus file sementara
del sources.txt

@REM Jalankan program utama
java -Xss4m -Xmx8g -cp bin Main