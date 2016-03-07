@echo off
cd %~dp0
call mvn clean compile package -Dmaven.test.skip=true
pause