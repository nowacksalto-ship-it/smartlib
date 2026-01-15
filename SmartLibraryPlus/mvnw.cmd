@REM Maven Wrapper batch script
@echo off
setlocal

set JAVA_HOME=
for /f "tokens=*" %%a in ('where java') do set JAVA_EXE=%%a

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Maven not found. Please install Maven or set MAVEN_HOME.
    echo You can run this project in IntelliJ IDEA or Eclipse.
    pause
    exit /b 1
)

call "%MAVEN_HOME%\bin\mvn.cmd" %*
