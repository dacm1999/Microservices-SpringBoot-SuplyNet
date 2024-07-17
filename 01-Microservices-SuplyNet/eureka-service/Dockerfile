# Usa una imagen base de JDK para construir y ejecutar la aplicación
FROM openjdk:21-oracle

# Establecer el directorio de trabajo
WORKDIR 01-Microservices-SuplyNet/eureka-service

# Copiar el archivo JAR generado
COPY target/eureka-service-0.0.1-SNAPSHOT.jar eureka-service.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 8761

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]
