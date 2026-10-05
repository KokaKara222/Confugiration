#!/bin/bash
# Демонстрация этапа 2: сборка через kotlinc и запуск со всеми
# поддерживаемыми параметрами командной строки.
set -e
cd "$(dirname "$0")/.."

echo "Сборка проекта..."
kotlinc src/Main.kt -include-runtime -d build/emulator.jar
echo "Сборка завершена."
echo

run() { java -jar build/emulator.jar "$@"; }

echo "=== 1. Без параметров ==="
echo "exit" | run

echo
echo "=== 2. Только --vfs ==="
echo "exit" | run --vfs ./vfs.zip

echo
echo "=== 3. Только --script (успешный сценарий) ==="
run --script tests/ok.txt

echo
echo "=== 4. --vfs и --script вместе ==="
run --vfs ./vfs.zip --script tests/ok.txt

echo
echo "=== 5. Скрипт с ошибкой (должен остановиться на unknown_command) ==="
run --script tests/fail.txt

echo
echo "=== 6. Несуществующий скрипт ==="
run --script tests/missing.txt
chmod +x tests/stage2.sh
./tests/stage2.sh