# Java 11 Runtimesidan foydalanamiz
FROM openjdk:11-jre-slim

# Loyiha papkasi ichiga kirib olish
WORKDIR /app

# Jar faylni konteynerga nusxalash
COPY target/your-bot.jar /app/bot.jar

# Java botni ishga tushirish
ENTRYPOINT ["java", "-jar", "bot.jar"]
