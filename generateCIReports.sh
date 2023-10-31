echo "Generating CI reports..."

echo "Building project and logging..."
./mvnw clean install >> ./reports/build-report.txt


./mvnw test >> ./reports/unittest-report.txt


./mvnw checkstyle:check >> ./reports/checkstyle-report.txt


echo "Job done!"