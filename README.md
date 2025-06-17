
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

## Backend
Соберите и запустите:  
  cd backend  
  mvn clean install  
  mvn spring-boot:run  
## Frontend
Запустите фронтенд:  
   cd frontend/frontend-app  
   npm start  

Приложение доступно по адресу: http://localhost:4200
