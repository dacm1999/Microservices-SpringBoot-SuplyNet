FROM openjdk:21-oracle

# Establecer el directorio de trabajo
WORKDIR 01-Microservices-SuplyNet/authorization-service

# Copiar el archivo JAR generado
COPY target/authorization-service-1.0.0-SNAPSHOT.jar authorization-service.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 8000

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "authorization-service.jar"]