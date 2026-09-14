@echo off
cd /d "%~dp0"
set CARROT_LAN_MODE=1
start "" powershell -NoProfile -Command "Start-Sleep -Seconds 2; Start-Process 'https://192.168.254.123:3443/classroom.html'"
python server.py
pause