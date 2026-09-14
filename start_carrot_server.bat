@echo off
cd /d "%~dp0"
start "" powershell -NoProfile -Command "Start-Sleep -Seconds 2; Start-Process 'https://localhost:3443/classroom.html'"
python server.py
pause
