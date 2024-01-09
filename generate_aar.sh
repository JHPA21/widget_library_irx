#!/bin/bash

# Name of the destination folder where the AARs will be placed
AARS_DEST='AARs'

# Name of the Gradle's settings file
SETTINGS_GRADLE_FILE="settings.gradle"

# Name of the Top-level Gradle's build file
TOP_LEVEL_BUILD_GRADLE_FILE="build.gradle"

# Name of the folder where the templates are located
TEMPLATE_DIR="DxpTemplate"

# Folder's name where the template for every component are located. Placed one level below TEMPLATE_DIR.
COMPONENT_TEMPLATE_DIR="DxpComponent_Template"

# Placeholder set in the build.gradle template to be renamed after the new DxpComponent
TEMPLATE_PLACEHOLDER='DXP_COMPONENT.aar'

# Placeholder set within build.gradle
IMPLEMENTATION_PLACEHOLDER="{{IMPLEMENTATION_BLOCK}}"

rm -rf "${AARS_DEST:?}/"
mkdir $AARS_DEST
echo "Creating destination folder... $AARS_DEST"
find . -name "*.aar" -print0 | xargs -0 -I {} cp {} $AARS_DEST/
echo "Copying AARs..."
cd $AARS_DEST/ || exit

touch $SETTINGS_GRADLE_FILE
cp "../$TEMPLATE_DIR/$TOP_LEVEL_BUILD_GRADLE_FILE" .

TOP_GRADLE_CONTENT=""
SETTINGS_CONTENT=""
for AAR_FILE in *.aar
do
	DXP_COMPONENT_FILE="${AAR_FILE//-debug/}"
	DXP_COMPONENT="${DXP_COMPONENT_FILE//.aar/}"
	DXP_COMPONENT_DIR="$DXP_COMPONENT"
	cp -R "../$TEMPLATE_DIR/$COMPONENT_TEMPLATE_DIR" "$DXP_COMPONENT_DIR/"
	sed -i '' "s/$TEMPLATE_PLACEHOLDER/$DXP_COMPONENT_FILE/g" "$DXP_COMPONENT_DIR/build.gradle"
	mv "$AAR_FILE" "$DXP_COMPONENT/$DXP_COMPONENT_FILE"

  TOP_GRADLE_CONTENT+="implementation project(':$DXP_COMPONENT')"$'\r'$'\t'
  SETTINGS_CONTENT+="include ':$DXP_COMPONENT'"$'\r'

  echo "Component ready: $DXP_COMPONENT"
done

echo "Updating... $TOP_LEVEL_BUILD_GRADLE_FILE"
sed -i '' "s/$IMPLEMENTATION_PLACEHOLDER/$TOP_GRADLE_CONTENT/" "$TOP_LEVEL_BUILD_GRADLE_FILE"
echo "Updating... $SETTINGS_GRADLE_FILE"
echo "$SETTINGS_CONTENT" >> "$SETTINGS_GRADLE_FILE"
echo "Finished. All set."