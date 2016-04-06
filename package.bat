@echo off
cd %~d0
cd %~dp0
call mvn clean compile package -Dmaven.test.skip=true
pause