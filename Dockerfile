FROM eclipse-temurin:21-jdk-alpine AS builder

# Install required tools
RUN apk add --no-cache openssl

# Working directory
WORKDIR /app

# Download and add the certificate
RUN openssl s_client -connect swapi.dev:443 -servername swapi.dev < /dev/null | openssl x509 -outform PEM > swapi.pem
RUN keytool -importcert \
    -file swapi.pem \
    -alias swapi \
    -keystore "$JAVA_HOME/lib/security/cacerts" \
    -storepass changeit \
    -noprompt

# Copy application code and build it
COPY . .
RUN ./gradlew bootJar --no-daemon

# Final image
FROM eclipse-temurin:21-jre-alpine

# Working directory in final container
WORKDIR /app

# Copy the modified cacerts from builder stage
COPY --from=builder "$JAVA_HOME/lib/security/cacerts" "$JAVA_HOME/lib/security/cacerts"

# Copy the built JAR
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose application port
EXPOSE 6969

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=docker"]
