
# Установка и запуск

## 1. База данных

1. Установите PostgreSQL
2. Создайте базу данных и пользователя:
   ```sql
   CREATE DATABASE subscription_db;
   CREATE USER user_name WITH PASSWORD 'password';
   GRANT ALL PRIVILEGES ON DATABASE subscription_db TO user_name;
   GRANT CREATE ON SCHEMA public TO user_name;
   
3. Настройте подключение к БД (Отредактируйте backend/src/main/resources/application.properties):
   spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_db  
   spring.datasource.username=user_name  
   spring.datasource.password=password  
   spring.jpa.hibernate.ddl-auto=update  

## 2. Backend
### Установка компонентов
   winget install -e --id Oracle.JDK.17  
   winget install -e --id PostgreSQL.PostgreSQL  
   winget install -e --id Git.Git  
   winget install -e --id Node.js  
   winget install -e --id Apache.Maven  
### Первый запуск
   cd backend  
   mvn lombok:install  
   mvn clean install  
   mvn spring-boot:run   
## 3. Frontend
Запустите фронтенд:  
   cd frontend/frontend-app  
   npm install --force  
   npm install -g @angular/cli  
   npm start  

Приложение доступно по адресу: http://localhost:4200

Для проверки работы оповещения через email рассылку измените значение subscription.reminder.cron в backend/src/resources/application.properties на подходящее время.  
Оповещение придет на адрес, указанный при создании подписки.
