SERVER_HOST = '0.0.0.0'
SERVER_PORT = 8080
SERVER_DEBUG = True

HTML_TEMPLATE = """
<!DOCTYPE html>
<html>
<head>
    <title>PyDivKit Web Server</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; }
        .endpoint { background: #ecf0f1; padding: 15px; border-radius: 4px; font-family: monospace; margin: 10px 0; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🚀 PyDivKit Web Server</h1>
        <p>Сервер для генерации DivKit JSON из Python DSL запущен и работает!</p>
        
        <h2>📍 Доступные endpoints:</h2>
        <div class="endpoint">GET /divruntime - сложная верстка с чекбоксами, темплейтами и actions</div>
        <div class="endpoint">GET /divruntime/simple - простой HelloWorld пример</div>
        
        <h2>✨ Что нового в сложном примере:</h2>
        <ul>
            <li>🎯 Кастомные темплейты EventCheckmarkDefault с параметрами</li>
            <li>☑️ Интерактивные чекбоксы с состояниями</li>  
            <li>⚡ DivActions для переключения переменных</li>
            <li>🔄 DivState с переключением между text и input</li>
            <li>🔗 Variable triggers для автоматических изменений</li>
            <li>🎨 Stroke styles для границ элементов</li>
        </ul>
        
        <h2>💡 Как использовать:</h2>
        <ul>
            <li>Откройте <a href="/divruntime" target="_blank">/divruntime</a> для сложного примера</li>
            <li>Откройте <a href="/divruntime/simple" target="_blank">/divruntime/simple</a> для простого</li>
            <li>Используйте полученный JSON в вашем DivKit клиенте</li>
            <li>Модифицируйте код в server.py для создания собственных компонентов</li>
        </ul>
        
        <h2>🔗 Быстрые ссылки:</h2>
        <a href="/divruntime" target="_blank" style="display: inline-block; background: #e74c3c; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; margin-right: 10px;">Сложная верстка</a>
        <a href="/divruntime/simple" target="_blank" style="display: inline-block; background: #3498db; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px;">Простой пример</a>
    </div>
</body>
</html>
"""
