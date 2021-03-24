# TelemetricTech Demo

Мое первое приложение.

Создано для текущих рабочих нужд. Оно предоставляет доступ к личному кабинету, где пользователь может следить за показаниями приборов учета (вода, электричество и прочее).

Реализованные фичи:
1)  Экран авторизации (без регистрации)
![image](https://github.com/dotdroid/TelemetricTechDemo/blob/main/screenshots/login.jpg?raw=true)
2)  Список всех приборов учета 
![image](https://github.com/dotdroid/TelemetricTechDemo/blob/main/screenshots/list.jpg?raw=true)
3)  Просмотр деталей прибора
![image](https://github.com/dotdroid/TelemetricTechDemo/blob/main/screenshots/device_summary.jpg?raw=true)
4)  Просмотр сообщений от устройства
![image](https://github.com/dotdroid/TelemetricTechDemo/blob/main/screenshots/messages.jpg?raw=true)
5)  Добавление устройства (скан QR кода с уникальным адресом, реализован на сторонней библиотеке)
![image](https://github.com/dotdroid/TelemetricTechDemo/blob/main/screenshots/create_new_device.jpg?raw=true)
6)  Удаление устройства

В приложении я использовал следующие штуки – Fragments, RecyclerView, ViewPager, Gson. REST API (все на POST) запросы самописные на HttpURLConnection.

Для многопоточности используется AsyncTask’и. Иконки приборов, логотип приложения, логотип на первом экране рисовал сам в Figma.

Приложению все еще требуется доработка. Можно перевести ногопоточность на HandlerThread или Rx, сделать так, чтобы устройство запоминало токен авторизации и показывало экран со списком устройств даже если было выгружено из памяти, нужно реализовать поиск и фильтрацию, убрать дублирование кода и еще много всего по мелочи. 

В данный момент проект не развивается, так как я изучаю новые штуки.