#!/bin/bash
set -e

ROOT="$(cd "$(dirname "$0")" && pwd)"
JAR="$ROOT/build/emulator.jar"

mkdir -p "$ROOT/build"
kotlinc "$ROOT/src/Main.kt" -include-runtime -d "$JAR"

if [ $# -eq 0 ]; then
    set -- --vfs "$ROOT/vfs.zip" --script "$ROOT/startup.txt"
fi

java -jar "$JAR" "$@"