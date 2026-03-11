# Описание проекта

Тестовое приложение для изучения возможностей GPT. В конечном итоге план - написать DnD игру на базе GPT.

## Установка и запуск

### Локальный запуск

1. Клонировать репозиторий:
```bash
git clone https://github.com/meloman-team/gpt-dnd-game.git
```
2. Создать переменные среды, или передать в параметрах при запуске.
```yaml
   YANDEX_API_KEY: fix_me
   YANDEX_FOLDER_ID: fix_me
   SPRING_PROFILES_ACTIVE: local
```
Получение API_KEY: https://yandex.cloud/ru/docs/iam/operations/authentication/manage-api-keys#create-api-key \
Получение FOLDER_ID: https://yandex.cloud/ru/docs/resource-manager/operations/folder/get-id \
Общая инструкция по аутентификации: https://aistudio.yandex.ru/docs/ru/ai-studio/api-ref/authentication.html?tabs=authentication_yandex-account

3. Учесть тарифы. Токены с доступом по API платные. https://yandex.cloud/ru/price-list?services=dn21smrl41t3kuid1qm5%2Cdn2o984h40l32p7t5nsv&utm_referrer=https%3A%2F%2Fyandex.cloud%2Fru%2Fdocs%2Fai-studio%2Fconcepts%2Fgeneration%2Fmodels 
4. Сбилдить и запустить проект
5. После запуска приложения для тестирования доступен swagger: http://localhost:8080/swagger-ui/index.html