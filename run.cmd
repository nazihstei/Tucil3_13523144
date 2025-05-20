@echo off

@REM Clear bin
rmdir /s /q "bin/"

@REM Cari semua file .java
dir /s /b src\*.java > sources.txt

@REM Set classpath secara eksplisit
set CLASSPATH=.\lib\jansi-2.4.0.jar;.\bin

@REM Compile semua file ke bin/ dengan classpath yang menyertakan file jar
javac -d bin -cp "%CLASSPATH%" @sources.txt

@REM Hapus file sementara
del sources.txt

@REM Jalankan program utama dengan classpath yang menyertakan file jar
java -Xss4m -Xmx4g -cp "%CLASSPATH%" Main