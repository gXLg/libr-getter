#!/usr/bin/env bash

if [[ "$1" == "first" ]]; then
  file="scripts/test-commands-first.txt"
else
  file="scripts/test-commands.txt"
fi

# NOTICE: Starting with 26.3, this command no longer finds the window
#mc=$(xdotool search --name "Minecraft")
mc=81788989
echo "Found MC: $mc"
xdotool windowfocus  --sync "$mc"
xdotool windowactivate  --sync "$mc"

xdotool type --clearmodifiers --delay 20 -- "Starting test..."
xdotool key --clearmodifiers Enter

while IFS= read -r line; do
  echo "$line"
  xdotool key --window "$mc" --clearmodifiers t
  sleep 0.2
  xdotool type --window "$mc" --clearmodifiers --delay 100 -- "$line"
  sleep 0.2
  xdotool key --window "$mc" --clearmodifiers Enter
done < "$file"
