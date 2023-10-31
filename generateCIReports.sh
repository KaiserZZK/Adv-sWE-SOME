echo "Generating CI reports..."

echo "Building project and logging..."
./mvnw clean install >> ./reports/build-report.txt
echo "✅ (1/3) Generated build report at ./reports/build-report.txt"

echo "Running unit tests and logging..."
./mvnw test >> ./reports/unittest-report.txt
echo "✅ (2/3) Generated unit test report at ./reports/unittest-report.txt"

echo "Running style checker and logging..."
./mvnw checkstyle:check >> ./reports/checkstyle-report.txt
echo "✅ (3/3) Generated style checker report at ./reports/checkstyle-report.txt"

echo "🌸 Job done!"