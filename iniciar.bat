@echo off
echo Iniciando MySQL y phpMyAdmin...
docker-compose up -d
echo.
echo Esperando a que MySQL este listo...
timeout /t 10 /nobreak >nul
echo.
echo Verificando contenedores...
docker ps
echo.
echo MySQL y phpMyAdmin estan corriendo!
echo.
echo Ahora puedes ejecutar la aplicacion Java desde tu IDE.
echo.
pause
