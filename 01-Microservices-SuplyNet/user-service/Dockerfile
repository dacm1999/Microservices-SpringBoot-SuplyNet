# Usa una imagen base de JDK para construir y ejecutar la aplicación
FROM openjdk:21-oracle

# Establecer el directorio de trabajo
WORKDIR 01-Microservices-SuplyNet/user-service

# Copiar el archivo JAR generado
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 8001

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "user-service.jar"]
