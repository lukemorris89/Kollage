#!/bin/bash
source "$(dirname "${BASH_SOURCE[0]}")/echo.sh"

PROPERTIES_FILE="$1"
PROPERTY_KEY="$2"
PROPERTY_VALUE="$3"

echoTitle "Properties Writer"
echoText "- File: $PROPERTIES_FILE"
echoText "-- Key: $PROPERTY_KEY"
echoText "-- Value: $PROPERTY_VALUE"

if ! grep -R "^[#]*\s*${PROPERTY_KEY}=.*" "$PROPERTIES_FILE" > /dev/null; then
  echoSuccess "APPENDING because '${PROPERTY_KEY}' not found"
  echo "$PROPERTY_KEY=$PROPERTY_VALUE" >> "$PROPERTIES_FILE"
else
  echoSuccess "SETTING because '${PROPERTY_KEY}' found already"
  # sed property -i has difference between platforms.
  # For OSX it requires the argument '', while for Ubuntu it doesn't
  if [[ "$OSTYPE" == "darwin"* ]]; then
    sed -i '' "s/^[#]*\s*${PROPERTY_KEY}=.*/$PROPERTY_KEY=$PROPERTY_VALUE/" "$PROPERTIES_FILE"
  else
    sed -i "s/^[#]*\s*${PROPERTY_KEY}=.*/$PROPERTY_KEY=$PROPERTY_VALUE/" "$PROPERTIES_FILE"
  fi
fi