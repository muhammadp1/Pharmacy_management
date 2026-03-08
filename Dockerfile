# Step 1: Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy the jar file into the container
# Make sure your backend jar is built already using 'mvn clean package'
COPY target/Pharmacy_mgmt-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port (Spring Boot default)
EXPOSE 8080

# Step 5: Run the Spring Boot application
# It will pick up environment variables for DB and ImgBB API key
ENTRYPOINT ["java", "-jar", "app.jar"]