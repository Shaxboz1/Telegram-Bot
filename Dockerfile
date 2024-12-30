# Java versiyasini o'rnatish
FROM openjdk:11-jre-slim

# Jar faylini konteynerga ko'chirish
COPY target/TelegramBot-1.0-SNAPSHOT.jar /app/TelegramBot-1.0-SNAPSHOT.jar

# 8080 portni ochish
EXPOSE 8080

# Dastur ishga tushadigan komandani belgilash
CMD ["java", "-jar", "/app/mybot.jar"]
