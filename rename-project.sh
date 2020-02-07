#! /bin/sh

APPLICATION_NAME="$1"
PACKAGE_NAME="$2"

usage () {
  echo "Setup this project after it was cloned."
  echo "USAGE: ./rename-project.sh <APPLICATION_NAME> <PACKAGE_NAME>"
  echo ""
  echo "<APPLICATION_NAME> should use the '-' character to concatenate words"
  echo "<PACKAGE_NAME> should follow the Java package naming convention"
  exit 1
}

if [[ -z "$APPLICATION_NAME" || -z "$PACKAGE_NAME" ]]; then
  usage
fi

echo "Renaming the application to '$APPLICATION_NAME' in package '$PACKAGE_NAME'."

echo "1. Update README"
echo "#### $APPLICATION_NAME" > README.md

echo "2. Rename src package to '$PACKAGE_NAME'"
SRC_FOLDER=./src/main/kotlin/$(sed 's/\./\//g' <<<"$PACKAGE_NAME")
echo "  New src folder is: $SRC_FOLDER"
mkdir -p "$SRC_FOLDER"
mv ./src/main/kotlin/micronaut/service/template/* ./src/main/kotlin/$(sed 's/\./\//g' <<<"$PACKAGE_NAME")
rm -rf ./src/main/kotlin/micronaut
find ./src/main/kotlin -type f -name '*.kt' -exec sed -i "s/micronaut\.service\.template/$PACKAGE_NAME/g" {} \;

echo "3. Rename test package to '$PACKAGE_NAME'"
TEST_FOLDER=./src/test/kotlin/$(sed 's/\./\//g' <<<"$PACKAGE_NAME")
echo "  New test folder is: $TEST_FOLDER"
mkdir -p "$TEST_FOLDER"
mv ./src/test/kotlin/micronaut/service/template/* ./src/test/kotlin/$(sed 's/\./\//g' <<<"$PACKAGE_NAME")
rm -rf ./src/test/kotlin/micronaut
find ./src/test/kotlin -type f -name '*.kt' -exec sed -i "s/micronaut\.service\.template/$PACKAGE_NAME/g" {} \;

echo "4. Update build file"
sed -i "s/micronaut\.service\.template/$PACKAGE_NAME/g" ./build.gradle.kts

echo "5. Update application name in application.yml"
sed -i "s/micronaut-service-template/$APPLICATION_NAME/g" ./src/main/resources/application.yml

echo "6. Update API info"
sed -i "s/Micronaut Service Template/$APPLICATION_NAME/g" "$SRC_FOLDER"/App.kt

echo "7. Execute tests"
./gradlew clean test

echo "8. Update Swagger UI"
sed -i "s/micronaut-service-template/$APPLICATION_NAME/g" ./src/main/kotlin/resources/swaggerui.html

echo "Done. Please have a look at '$SRC_FOLDER/App.kt' and double-check your API description."
