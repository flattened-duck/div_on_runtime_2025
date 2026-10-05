# DivKit at Yandex Mobile Runtime 2025

Demo materials from the [DivKit](https://github.com/divkit/divkit) stand at [Yandex Mobile Runtime 2025](https://events.yandex.ru/events/yandex-mobile-runtime-2025/index#program).

The stand showed server-driven UI live: one checklist card is built on the server, and native apps render it. The same card is implemented with each of DivKit's server-side builders, so visitors could compare them side by side. The Android app polls the server every second and re-renders only when the layout changes, so an edit on the server appears on the phone almost instantly.

## Structure

| Folder / file | What it is |
|---|---|
| `dsl_kotlin/` | Spring Boot server that builds the card with DivKit's Kotlin DSL. Serves `GET /divruntime` on port 8080. |
| `dsl_python/` | Flask server that builds the same card with [pydivkit](https://github.com/divkit/divkit/tree/main/json-builder/python). `pydivkit/` is a generated copy of the library. |
| `ts_base.ts` | The same card with DivKit's TypeScript builder (`@divkitframework/jsonbuilder`). |
| `json_base.json`, `resources/json_full.json` | The card as plain DivKit JSON. |
| `app_android/` | Android client: fetches the layout from `10.0.2.2:8080` (the host machine from the emulator) and re-renders on change. |
| `app_ios/` | Minimal iOS client (SwiftUI) that loads a layout from a local server. |

## Running

```bash
# Kotlin server
cd dsl_kotlin && ./gradlew bootRun

# or the Python server
cd dsl_python && ./run.sh
```

Then run `app_android/` from Android Studio on an emulator. Setup notes for the Python server are in [`dsl_python/README_SETUP.md`](dsl_python/README_SETUP.md) (in Russian).
