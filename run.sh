#!/bin/bash
set -e
cd "$(dirname "$0")"
kotlinc src/Main.kt -include-runtime -d build/emulator.jar
java -jar build/emulator.jar "$@"
