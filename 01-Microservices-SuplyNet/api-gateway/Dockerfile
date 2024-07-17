# Usa una imagen base de JDK para construir y ejecutar la aplicación
FROM openjdk:21-oracle

# Establecer el directorio de trabajo
WORKDIR 01-Microservices-SuplyNet/api-gateway

# Copiar el archivo JAR generado
COPY target/api-gateway-1.0.0-SNAPSHOT.jar api-gateway-service.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "api-gateway-service.jar"]
