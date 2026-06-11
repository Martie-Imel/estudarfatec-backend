# =============================================
# EstudarFatec — Dockerfile para deploy no Render
# =============================================

# Estágio 1: build com Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências primeiro (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o código-fonte e gera o JAR
COPY src src
RUN mvn clean package -DskipTests -q

# =============================================
# Estágio 2: imagem final enxuta (só o JRE)
# =============================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia apenas o JAR gerado no estágio anterior
COPY --from=build /app/target/estudarfatec-1.0.0.jar app.jar

# Expõe a porta (o Render injeta a variável PORT automaticamente)
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
