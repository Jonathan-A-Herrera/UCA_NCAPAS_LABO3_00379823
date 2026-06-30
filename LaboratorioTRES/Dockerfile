# ---------- Etapa de build ----------
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw clean package -DskipTests -B

# ---------- Etapa de runtime ----------
FROM eclipse-temurin:17-jre-jammy

# Crear usuario y grupo sin privilegios
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Cambiar propietario del archivo al usuario no root
RUN chown spring:spring app.jar

# Cambiar al usuario no root
USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]