# Usando uma imagem oficial do JDK
FROM openjdk:21-slim as build

WORKDIR /app

# Copiando os arquivos necessários para o build
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Ajustando permissões e executando o Maven Wrapper
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw && chmod +x mvnw
RUN ./mvnw clean package -DskipTests -e

# Preparando a imagem de execução
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
