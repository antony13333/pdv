# Etapa de build: usa uma imagem que já tem Maven e JDK instalados
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app
# Copia o pom.xml e o diretório src para dentro do container
COPY pom.xml .
COPY src ./src
# Executa o build com Maven (gera o .jar na pasta target)
RUN mvn clean install

# Etapa de runtime: utiliza uma imagem apenas com o JRE para rodar a aplicação
FROM openjdk:17-jre
WORKDIR /app
# Copia o .jar gerado na etapa de build
COPY --from=build /app/target/restauranteAPI-0.0.1-SNAPSHOT.jar app.jar
# Expõe a porta (certifique-se que seu application.properties usa: server.port=${PORT:8080})
EXPOSE 8080
# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
