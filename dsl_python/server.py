#!/usr/bin/env python3

from flask import Flask, jsonify
from divkit_layouts import create_welcome_card, create_checkbox_layout
from config import SERVER_HOST, SERVER_PORT, SERVER_DEBUG, HTML_TEMPLATE

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False


@app.route('/divruntime/simple', methods=['GET'])
def divruntime_simple():
    try:
        div_component = create_welcome_card()
        
        import pydivkit as dk
        result = dk.make_div(div_component)
        
        return jsonify(result)
    
    except Exception as e:
        return jsonify({
            "error": "Failed to generate simple DivKit JSON",
            "details": str(e)
        }), 500


@app.route('/divruntime', methods=['GET'])
def divruntime():
    try:
        div_data = create_checkbox_layout()
        
        state = div_data.states[0] if div_data.states else None
        templates = {}
        if state and state.div:
            templates = {
                tpl.template_name: tpl.template()
                for tpl in state.div.related_templates()
            }
        
        return jsonify({
            "templates": templates,
            "card": div_data.dict()
        })
        
    except Exception as e:
        return jsonify({
            "error": "Failed to generate complex DivKit JSON", 
            "details": str(e)
        }), 500


@app.route('/', methods=['GET'])
def index():
    return HTML_TEMPLATE


@app.route('/health', methods=['GET'])
def health():
    return jsonify({
        "status": "OK",
        "service": "pydivkit-server",
        "version": "1.0.0"
    })


if __name__ == '__main__':
    print("🚀 Starting PyDivKit Web Server...")
    print(f"📍 Server will be available at: http://localhost:{SERVER_PORT}")
    print(f"🔗 DivKit endpoint: http://localhost:{SERVER_PORT}/divruntime")
    print(f"💡 Open http://localhost:{SERVER_PORT} for more info")
    
    app.run(host=SERVER_HOST, port=SERVER_PORT, debug=SERVER_DEBUG)