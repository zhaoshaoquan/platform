@echo off
cd %~d0
cd %~dp0
call mvn clean install deploy -Dmaven.test.skip=true
pause