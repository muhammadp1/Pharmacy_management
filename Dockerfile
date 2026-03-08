# Step 1: Use official Eclipse Temurin OpenJDK 17 image
FROM eclipse-temurin:17-jdk

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy the jar file into the container
COPY target/Pharmacy_mgmt-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port (Spring Boot default)
EXPOSE 8080

# Step 5: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]