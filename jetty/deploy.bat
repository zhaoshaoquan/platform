@echo off
cd %~dp0
call mvn clean install deploy -Dmaven.test.skip=true
pause