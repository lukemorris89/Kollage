#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 3 ]; then
  echo "Usage: $0 <properties_file> <property_name> <property_value>"
  exit 1
fi

properties_file="$1"
property_name="$2"
property_value="$3"

# Check if the properties file exists, create it if it doesn't
if [ ! -f "$properties_file" ]; then
  echo "Creating properties file: $properties_file"
  touch "$properties_file"
fi

# Use sed to update or add the property
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  sed -i "" -e "/^${property_name}=/s/=.*$/=${property_value}/" "$properties_file" || \
    echo "${property_name}=${property_value}" >> "$properties_file"
else
  # Linux
  sed -i -e "/^${property_name}=/s/=.*$/=${property_value}/" "$properties_file" || \
    echo "${property_name}=${property_value}" >> "$properties_file"
fi

echo "Updated ${property_name} in ${properties_file} to ${property_value}"