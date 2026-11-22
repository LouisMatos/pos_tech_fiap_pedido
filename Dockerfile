FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw \
    && sed -i 's/\r$//' mvnw \
    && ./mvnw -q dependency:go-offline

COPY src src

RUN ./mvnw -q package -DskipTests

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

RUN groupadd --system appuser \
    && useradd --system --gid appuser appuser

COPY --from=build /app/target/jlapp-pedido-0.0.1-SNAPSHOT.jar app.jar

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
