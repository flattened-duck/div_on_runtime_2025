# 🚀 PyDivKit Server - Установка и запуск

Модульный веб-сервер для демонстрации PyDivKit DSL, который генерирует DivKit JSON для клиентов.

## 📋 Требования

- **Python**: 3.8 или выше
- **Операционная система**: Windows, macOS, Linux

## 🛠 Установка и настройка

### Вариант 1: Использование Poetry (рекомендуется)

```bash
# Установите Poetry, если еще не установлен
curl -sSL https://install.python-poetry.org | python3 -

# Установите зависимости
poetry install

# Активируйте виртуальное окружение
poetry shell

# Запустите сервер
python server.py
```

### Вариант 2: Использование pip и virtualenv

```bash
# Создайте виртуальное окружение
python -m venv env

# Активируйте его
# На Windows:
env\Scripts\activate
# На macOS/Linux:
source env/bin/activate

# Установите зависимости
pip install -r requirements.txt

# Запустите сервер
python server.py
```

### Вариант 3: Запуск в PyCharm

1. **Откройте проект** в PyCharm (Open folder with all files)
2. **Настройте интерпретатор Python**:
   - File → Settings → Project → Python Interpreter
   - Add Interpreter → Virtualenv Environment → New Environment
   - Base interpreter: Python 3.8+
   - Location: укажите папку `env` в корне проекта
3. **Установите зависимости**:
   - Откройте Terminal в PyCharm
   - Выполните: `pip install -r requirements.txt`
4. **Запустите server.py**:
   - Правый клик на `server.py` → Run 'server'
   - Или используйте зеленую стрелку ▶️

## 📂 Структура проекта

```
dsl_python/
├── server.py          # 🌐 Flask веб-сервер (главный файл для запуска)
├── config.py          # ⚙️ Конфигурация сервера
├── divkit_layouts/    # 📦 DSL модуль для создания DivKit компонентов
│   ├── __init__.py    # Экспорт функций
│   ├── components.py  # Сложные компоненты
│   ├── templates.py   # Кастомные темплейты
│   └── utils.py       # Утилиты для прототипирования
├── pydivkit/          # 🔧 PyDivKit DSL библиотека (автогенерация)
├── requirements.txt   # 📋 Зависимости для pip
├── pyproject.toml     # 📋 Конфигурация Poetry
└── README_SETUP.md    # 📖 Инструкция по установке
```

## ✅ Проверка установки

После установки откройте браузер и перейдите по адресам:

- **🏠 Главная страница**: http://localhost:8080/
- **🔥 Сложная верстка**: http://localhost:8080/divruntime  
- **📝 Простой пример**: http://localhost:8080/divruntime/simple
- **❤️ Проверка здоровья**: http://localhost:8080/health

## 🚨 Решение проблем

### Проблема: `ModuleNotFoundError: No module named 'divkit_layouts'`

**Решение**: Убедитесь, что вы находитесь в правильной папке:
```bash
cd dsl_python/
python server.py
```

### Проблема: `ModuleNotFoundError: No module named 'flask'`

**Решение**: Установите зависимости:
```bash
pip install -r requirements.txt
```

### Проблема: Сервер не запускается в PyCharm

**Решение**: Проверьте настройки:
1. Убедитесь, что в качестве рабочей директории указана папка `dsl_python/`
2. Проверьте, что интерпретатор Python настроен правильно
3. Убедитесь, что все зависимости установлены в выбранное виртуальное окружение

## 🔧 Разработка

### Добавление новых компонентов

1. **Простые элементы**: используйте утилиты из `divkit_layouts/utils.py`
2. **Сложные компоненты**: добавляйте функции в `divkit_layouts/components.py`  
3. **Темплейты**: создавайте классы в `divkit_layouts/templates.py`
4. **Endpoints**: добавляйте маршруты в `server.py`

### Тестирование

```bash
# Запуск тестов
python -m pytest tests/

# Проверка типов
mypy .
```

## 📱 Использование JSON в клиентах

Получите JSON из API и используйте в ваших DivKit клиентах:

```bash
curl http://localhost:8080/divruntime | jq .
```

## 🌐 Деплой

Для продакшн деплоя используйте WSGI сервер:

```bash
pip install gunicorn
gunicorn -w 4 -b 0.0.0.0:8080 server:app
```

## 📚 Полезные ссылки

- [DivKit Documentation](https://divkit.tech/)
- [PyDivKit GitHub](https://github.com/divkit/divkit/tree/main/json-builder/python)
- [Flask Documentation](https://flask.palletsprojects.com/)
