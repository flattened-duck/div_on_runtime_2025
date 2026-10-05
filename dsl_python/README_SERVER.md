# PyDivKit Web Server

Модульный веб-сервер для демонстрации pydivkit DSL, который генерирует DivKit JSON для клиентов.

## 📁 Структура проекта

```
dsl_python/
├── server.py          # Flask веб-сервер и endpoints
├── config.py          # Конфигурация сервера и HTML шаблоны
├── divkit_layouts/    # 📦 DSL модуль для создания DivKit компонентов
│   ├── __init__.py    # Экспорт основных функций
│   ├── components.py  # Функции создания сложных компонентов
│   ├── templates.py   # Кастомные темплейты с параметрами
│   └── utils.py       # Утилиты для быстрого создания простых элементов
├── pydivkit/          # PyDivKit DSL библиотека
└── tests/             # Тесты
```

## 🚀 Быстрый старт

### Установка зависимостей

```bash
# Создаём и активируем виртуальное окружение
python3 -m venv env
source env/bin/activate

# Устанавливаем зависимости
poetry install
```

### Запуск сервера

```bash
# Запуск сервера
python server.py
```

Сервер запустится на `http://localhost:8080`

## 📍 Доступные endpoints

| Endpoint | Метод | Описание |
|----------|--------|----------|
| `/` | GET | Главная информационная страница |
| `/divruntime` | GET | Получить DivKit JSON для HelloWorld примера |
| `/health` | GET | Health check сервера |

## 💡 Использование

### Получить DivKit JSON

```bash
curl "http://localhost:8080/divruntime" | python -m json.tool
```

### Пример ответа

Сервер возвращает валидный DivKit JSON со структурой:

```json
{
  "templates": {},
  "card": {
    "log_id": "card",
    "states": [
      {
        "state_id": 0,
        "div": {
          "type": "container",
          "items": [
            {
              "type": "text",
              "text": "Добро пожаловать в PyDivKit!",
              "font_size": 28,
              "font_weight": "bold",
              "text_alignment_horizontal": "center",
              "text_color": "#2c3e50"
            },
            {
              "type": "text", 
              "text": "Hello, World!",
              "font_size": 24,
              "background": [{"type": "solid", "color": "#f0f8ff"}],
              "border": {"corner_radius": 12}
            }
          ]
        }
      }
    ]
  }
}
```

## 🛠 Расширение функциональности

### Добавление новых темплейтов

Создайте класс в `divkit_layouts/templates.py`:

```python
class MyCustomTemplate(dk.DivContainer):
    title: str = dk.Field()
    color: str = dk.Field(default="#000000")
    
    items = [
        dk.DivText(
            text=dk.Ref(title),
            text_color=dk.Ref(color)
        )
    ]
```

### Добавление новых компонентов

Создайте функцию в `divkit_layouts/components.py`:

```python
def create_my_component():
    return dk.DivText(text="My Component")
```

### Использование утилит

Быстрое создание простых элементов через `divkit_layouts/utils.py`:

```python
from divkit_layouts import create_simple_text, create_button, create_card

# Простой текст
text = create_simple_text("Заголовок", size=24, color="#2c3e50")

# Кнопка с действием
button = create_button("Нажать", "div-action://click", bg_color="#e74c3c")

# Карточка 
card = create_card([text, button], bg_color="#f8f9fa")
```

### Добавление новых endpoints

Добавьте в `server.py`:

```python
from divkit_layouts import create_simple_text
import pydivkit as dk

@app.route('/my-endpoint')
def my_endpoint():
    result = create_simple_text("My Component")
    return jsonify(dk.make_div(result))
```

## 🔗 Полезные ссылки

- [DivKit Documentation](https://divkit.tech/)
- [PyDivKit GitHub](https://github.com/divkit/divkit/tree/main/json-builder/python)

## 📝 Архитектура

### Разделение ответственности

- **`server.py`** - Flask routes и HTTP логика
- **`config.py`** - настройки сервера и статичный контент  
- **`divkit_layouts/`** - DSL модуль для генерации компонентов:
  - **`components.py`** - сложные функции создания DivKit структур
  - **`templates.py`** - переиспользуемые темплейты с параметрами  
  - **`utils.py`** - утилиты для быстрого создания простых элементов
  - **`__init__.py`** - удобный API для импорта

### Преимущества модульной структуры

- ✅ Четкое разделение сервера и DSL логики
- ✅ Легко добавлять новые темплейты и компоненты
- ✅ Простое тестирование каждого модуля отдельно
- ✅ Переиспользование DSL модуля в других проектах
- ✅ Утилиты для быстрого прототипирования

## 🐛 Отладка

Если сервер не запускается:

1. Убедитесь, что активировано виртуальное окружение
2. Проверьте, что установлены все зависимости: `poetry install`
3. Проверьте, что порт 8080 свободен: `lsof -i :8080`
