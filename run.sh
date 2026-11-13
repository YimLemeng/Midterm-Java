#!/bin/bash
# Compile and run Student Management System
echo "Compiling Student Management System..."
javac -d out src/sms/*.java
if [ $? -eq 0 ]; then
    echo "Compilation successful! Starting application..."
    java -cp out sms.Main
else
    echo "Compilation failed!"
fi
