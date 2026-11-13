@echo off
echo Compiling Student Management System...
if not exist out mkdir out
javac -d out src/sms/*.java
if %errorlevel% equ 0 (
    echo Compilation successful! Starting application...
    java -cp out sms.Main
) else (
    echo Compilation failed!
    pause
)
