#!/usr/bin/env bash

if [[ "$1" == "first" ]]; then
  file="scripts/test-commands-first.txt"
else
  file="scripts/test-commands.txt"
fi

mc=$(xdotool search --name "Minecraft[*]")
echo "Found MC: $mc"
xdotool windowfocus  --sync "$mc"
xdotool windowactivate  --sync "$mc"

xdotool type --clearmodifiers -- "Starting test..."
xdotool key --clearmodifiers Enter

while IFS= read -r line; do
  echo "$line"
  xdotool type --clearmodifiers -- "t$line"
  xdotool key --clearmodifiers Enter
done < "$file"