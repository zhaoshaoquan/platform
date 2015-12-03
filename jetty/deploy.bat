@echo off
mvn clean install deploy -Dmaven.test.skip=true
pause