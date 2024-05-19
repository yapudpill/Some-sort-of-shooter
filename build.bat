@echo off
setlocal

rem Directories
set SRC_DIR=src\main\java
set RES_DIR=src\main\resources
set BUILD_DIR=build
set JAR_DIR=jar

set MAIN_CLASS=controller.MainController
set MAIN_CLASS_FILE=controller\MainController.java

set JAVAC=javac

set JAVA=java
set JAR=jar

goto :main

rem Functions
:make_build_dir
    if not exist %BUILD_DIR% (
        echo Creating build directory...
        mkdir %BUILD_DIR%
    )
    goto :eof

:compile
    call :make_build_dir
    echo Compiling Java files...
	%JAVAC% -d %BUILD_DIR% --class-path %SRC_DIR% %SRC_DIR%\%MAIN_CLASS_FILE%
    goto :eof




:copy_resources
    echo Copying resource files...
    if exist %RES_DIR% (
        xcopy /E /I /Y %RES_DIR%\* %BUILD_DIR%
    )
    goto :eof

:run
    echo Running the application...
    cd %BUILD_DIR%
    %JAVA% %MAIN_CLASS%
    if errorlevel 1 goto :error
    popd
    goto :eof

:clean
    echo Cleaning build directory...
    if exist %BUILD_DIR% (
        rmdir /S /Q %BUILD_DIR%
    )
	if exist %JAR_DIR% (
		rmdir /S /Q %JAR_DIR%
	)
    goto :eof

:jar
	%JAR% cfe %JAR_DIR%\shooter.jar %MAIN_CLASS% -C %BUILD_DIR% .
	goto :eof

:error
    echo Error occurred during execution.
    exit /b 1

rem Main
:main
if "%1" == "all" (
    call :compile
    if errorlevel 1 goto :error
    call :copy_resources
	call :jar
	call :run
) else if "%1" == "compile" (
	call :compile
) else if "%1" == "copy_resources" (
	call :copy_resources
) else if "%1" == "run" (
    call :run
) else if "%1" == "clean" (
    call :clean
) else if "%1" == "jar" (
	call :jar
) else (
    echo Usage: build.bat [all^|run^|clean^|jar]
)

endlocal