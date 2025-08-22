#!/bin/bash
# PyDivKit Server - удобный запуск

echo "🚀 Starting PyDivKit Web Server..."

# Проверяем наличие Python
if ! command -v python3 &> /dev/null; then
    echo "❌ Python 3 не найден! Установите Python 3.8 или выше"
    exit 1
fi

# Проверяем виртуальное окружение
if [[ -z "${VIRTUAL_ENV}" ]]; then
    echo "⚠️  Виртуальное окружение не активировано"
    
    # Пытаемся найти и активировать env
    if [[ -d "env" ]]; then
        echo "📦 Активируем существующее виртуальное окружение..."
        source env/bin/activate
    else
        echo "📦 Создаем новое виртуальное окружение..."
        python3 -m venv env
        source env/bin/activate
        echo "📋 Устанавливаем зависимости..."
        pip install -r requirements.txt
    fi
fi

# Проверяем зависимости
if ! python -c "import flask" 2>/dev/null; then
    echo "📋 Устанавливаем зависимости..."
    pip install -r requirements.txt
fi

# Запускаем сервер
echo "🌐 Запускаем сервер на http://localhost:8080"
python server.py
