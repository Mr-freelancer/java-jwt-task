# java-jwt-task
## Задача с использованием JSON Web Tokens
### Инструкция по запуску

1. Создаем пользователя /register  
   {"name": "your_name", "password": "your_password"}
2. Получаем токен /authenticate  
   {"name": "your_name", "password": "your_password"}
3. Добавляем токен в заголовок Authenticate вида:  
   Bearer ваш_токен
4. Создаем сообщения /message {POST}  
   {
   "name": "имя пользователя",
   "message": "сообщение"
   }  
5. Получаем все сообщения(токен должен быть в заголовках) /message {GET}
6. Получаем последние n сообщений для пользователя /message {POST}  
   {
   "name": "имя пользователя",
   "message": "history число_сообщений"
   } 
   