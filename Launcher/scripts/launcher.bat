@echo off
REM Script de lancement du Minecraft Educational Launcher (Windows)
REM Ce script facilite le démarrage pour les utilisateurs Windows

echo ==================================
echo Minecraft Educational Launcher
echo Version 2.0.0
echo ==================================
echo.

REM Vérifier Java
echo Verification de Java...
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Java n'est pas installe ou n'est pas dans le PATH
    echo Telechargez Java 17+ sur : https://adoptium.net/
    pause
    exit /b 1
)

java -version 2>&1 | findstr /C:"version" >nul
if %ERRORLEVEL% EQU 0 (
    echo Java detecte
) else (
    echo ERREUR: Impossible de detecter la version Java
    pause
    exit /b 1
)

echo.

REM Vérifier Maven
echo Verification de Maven...
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Maven n'est pas installe ou n'est pas dans le PATH
    echo Telechargez Maven sur : https://maven.apache.org/
    pause
    exit /b 1
)

echo Maven detecte
echo.

REM Menu
echo Que voulez-vous faire ?
echo 1) Compiler le projet (clean package)
echo 2) Lancer l'application (javafx:run)
echo 3) Compiler ET lancer
echo 4) Nettoyer le projet (clean)
echo.
set /p choice="Votre choix (1-4) : "

if "%choice%"=="1" goto compile
if "%choice%"=="2" goto run
if "%choice%"=="3" goto compile_and_run
if "%choice%"=="4" goto clean
echo Choix invalide
pause
exit /b 1

:compile
echo.
echo Compilation du projet...
call mvn clean package
goto end

:run
echo.
echo Lancement de l'application...
call mvn javafx:run
goto end

:compile_and_run
echo.
echo Compilation du projet...
call mvn clean package
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Lancement de l'application...
    call mvn javafx:run
) else (
    echo ERREUR: La compilation a echoue
    pause
    exit /b 1
)
goto end

:clean
echo.
echo Nettoyage du projet...
call mvn clean
echo Nettoyage termine
goto end

:end
echo.
echo Termine !
pause
