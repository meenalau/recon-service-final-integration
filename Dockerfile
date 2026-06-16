# ─────────────────────────────────────────────────────────────────────────────
# Stage 1 – build the fat JAR with Maven
# ─────────────────────────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom and resolve dependencies into the Docker layer cache.
# -Dmaven.repo.local keeps them in /app/.m2 so stage-2 copy is not needed.
# We do NOT use dependency:go-offline — it fails on legacy HTTP snapshot repos
# that some transitive dependencies reference (e.g. old javax.xml.bind snapshots).
COPY pom.xml .
RUN mvn dependency:resolve dependency:resolve-plugins -q \
    -Dmaven.wagon.http.retryHandler.count=3 \
    || true

# Copy source and build (skip tests – they need a running DB)
COPY src ./src
RUN mvn package -DskipTests

# ─────────────────────────────────────────────────────────────────────────────
# Stage 2 – lean runtime image
# ─────────────────────────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]