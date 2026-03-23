#!/usr/bin/env bash
# Build fresh APK, install on device, and open the app.
# adb install never auto-opens the app; this script launches it after install.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT"
export ANDROID_HOME="${ANDROID_HOME:-$HOME/Android/Sdk}"

echo "==> Building debug APK (so you get the latest code)..."
./gradlew assembleDebug --no-daemon

APK="$ROOT/app/build/outputs/apk/debug/app-debug.apk"
echo "==> Installing: $APK"
adb install -r "$APK"

echo "==> Launching Mental Health..."
adb shell am start -n com.example.mentalhealth/.MainActivity

echo ""
echo "Tip: Success only means install worked. The app should open now from the command above."
echo "     New stuff: use bottom tabs — Journal = journal entries; Home = mood counter."
echo ""
