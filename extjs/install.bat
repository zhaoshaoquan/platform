@echo off
%~d0
cd %~dp0
call mvn clean install -Dmaven.test.skip=true
pause