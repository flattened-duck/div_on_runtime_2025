@echo off
REM PyDivKit Server - удобный запуск для Windows

echo 🚀 Starting PyDivKit Web Server...

REM Проверяем наличие Python
python --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Python не найден! Установите Python 3.8 или выше
    pause
    exit /b 1
)

REM Проверяем виртуальное окружение  
if not defined VIRTUAL_ENV (
    echo ⚠️  Виртуальное окружение не активировано
    
    REM Пытаемся найти и активировать env
    if exist env (
        echo 📦 Активируем существующее виртуальное окружение...
        call env\Scripts\activate
    ) else (
        echo 📦 Создаем новое виртуальное окружение...
        python -m venv env
        call env\Scripts\activate
        echo 📋 Устанавливаем зависимости...
        pip install -r requirements.txt
    )
)

REM Проверяем зависимости
python -c "import flask" >nul 2>&1
if errorlevel 1 (
    echo 📋 Устанавливаем зависимости...
    pip install -r requirements.txt
)

REM Запускаем сервер
echo 🌐 Запускаем сервер на http://localhost:8080
python server.py

pause
