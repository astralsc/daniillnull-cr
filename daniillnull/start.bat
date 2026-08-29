@echo off 
cd bin
mode con: cols=120 lines=32
java -Djava.library.path="..\lib" daniillnull.javacr.server.Main http://dnull.xyz ..\gamefiles\fingerprint.json
pause
