#!/bin/bash

# Bersihkan direktori bin
rm -rf "bin/"

# Cari semua file .java
find src/ -name "*.java" > sources.txt

# Set classpath secara eksplisit
CLASSPATH="./lib/jansi-2.4.0.jar:./bin"

# Compile semua file ke bin/ dengan classpath yang menyertakan file jar
javac -d bin -cp "$CLASSPATH" @sources.txt

# Hapus file sementara
rm sources.txt

# Jalankan program utama dengan classpath yang menyertakan file jar
java -Xss4m -Xmx4g -cp "$CLASSPATH" Main