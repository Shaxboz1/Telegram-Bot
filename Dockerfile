# Java 11 Runtimesidan foydalanamiz
FROM openjdk:11-jre-slim

# Loyiha papkasi ichiga kirib olish
WORKDIR /app

# Jar faylni konteynerga nusxalash
COPY target/TelegramBot-1.0-SNAPSHOT.jar.jar /app/bot.jar

# Java botni ishga tushirish
ENTRYPOINT ["java", "-jar", "bot.jar"]
