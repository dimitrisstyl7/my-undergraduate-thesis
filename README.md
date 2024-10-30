# [Integrated Service System for a Nutritionist and Clients – Web Platform and Mobile Application](https://dione.lib.unipi.gr/xmlui/handle/unipi/16908)

## [University of Piraeus](https://www.unipi.gr/en/) | [Department of Informatics](https://cs.unipi.gr/en/)

## Abstract
This thesis aims to implement a service system for nutritionists that facilitates communication with their clients. The system supports the use of a **web platform** for the nutritionist and a **mobile application** for clients. The architecture includes a **backend server using Spring Boot Framework**, which serves both the web platform, and the **mobile application developed with the Jetpack Compose toolkit**.

## Backend Server
The backend server is implemented using the **MVC (Model-View-Controller)** software architecture pattern. In this project, the Model layer is further divided into sub-layers, such as [repositories](./DietitianHub-Backend/src/main/java/gr/unipi/thesis/dimstyl/repositories/) and [services](./DietitianHub-Backend/src/main/java/gr/unipi/thesis/dimstyl/services/), to better organize different functionalities and database interactions. Additionally, the Controller layer is also further divided into [web](./DietitianHub-Backend/src/main/java/gr/unipi/thesis/dimstyl/controllers/web/) and [api](./DietitianHub-Backend/src/main/java/gr/unipi/thesis/dimstyl/controllers/api/) controllers, as the same server also handles RESTful API calls from the Android application.

The relational database management system used is **PostgreSQL**, which runs in a **Docker container**. For container creation, the [docker-compose.yaml](./DietitianHub-Backend/docker/docker-compose.yml) file is used, while the [init.sql](./DietitianHub-Backend/docker/sql-scripts/init.sql) and [seed-data.sql](./DietitianHub-Backend/docker/sql-scripts/seed-data.sql) files are used to create the database and populate it with mock data.

### Frameworks, tools and libraries:
The following are the core frameworks, tools, and libraries used in the backend server. These represent the essential components, and additional tools may also be utilized.
- [Spring Boot Framework](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring AOP](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [Spring Email](https://docs.spring.io/spring-boot/reference/io/email.html)
- [Hibernate ORM](https://hibernate.org/orm/)
- [Hibernate Validator](https://hibernate.org/validator/)
- [Project Lombok](https://projectlombok.org/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Docker](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org/)
- [Java JWT](https://github.com/jwtk/jjwt)

### Implemented Features:
#### 1. View clients
![View clients](./images/view-clients.png)
#### 2. Register a new client
![Register a new client](./images/add-new-client.png)
#### 3. Edit client details
![Edit client details](./images/edit-client-details.png)
#### 4. Delete a client
![Delete a client](./images/delete-client.png)
#### 5. Add client tags
![Add client tags](./images/add-client-tags.png)
#### 6. Upload, view, download, and delete diet plans for a client
![Upload, view, download, and delete diet plans for a client](./images/upload-view-download-delete-diet-plans.png)
#### 7. View announcements
![View announcements](./images/view-announcements.png)
#### 8. Preview announcement
![Preview announcement](./images/preview-announcement.png)
#### 9. Edit announcement
![Edit announcement](./images/edit-announcement.png)
#### 10. Delete announcement
![Delete announcement](./images/delete-announcement.png)
#### 11. View articles
![View articles](./images/view-articles.png)
#### 12. Preview article
![Preview article](./images/preview-article.png)
#### 13. Preview article tags
![Preview article tags](./images/preview-article-tags.png)
#### 14. Edit article
![Edit article](./images/edit-article.png)
#### 15. Edit article tags
![Edit article tags](./images/edit-article-tags.png)
#### 16. Delete article
![Delete article](./images/delete-article.png)
#### 17. Create a new appointment
![Create a new appointment](./images/create-new-appointment.png)
#### 18. Edit appointment details
![Edit appointment details](./images/edit-appointment-details.png)
#### 19. Cancel, decline, approve, or complete an appointment
![Cancel, decline, approve, or complete an appointment](./images/cancel-decline-approve-complete-appointment.png)

## Android Application
The Android application is implemented using the **Clean Architecture** software architecture pattern, which is an improved implementation of **MVVM (Model-View-ViewModel)** architecture design pattern.

### Tools and libraries:
The following tools and libraries are central to the Android application's development. However, there may be additional libraries and tools used in the project that are not explicitly mentioned here.
- [Jetpack Compose toolkit](https://developer.android.com/compose)
- [Navigation component](https://developer.android.com/develop/ui/compose/navigation)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [Gson Converter](https://github.com/square/retrofit/blob/trunk/retrofit-converters/gson/README.md)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

### Implemented Features:
#### 1. Edit personal details
![Edit personal details](./images/edit-personal-details.png)
#### 2. Download diet plans
![Download diet plans](./images/download-diet-plans.png)
#### 3. View appointments
![View appointments](./images/view-appointments-android.png)
#### 4. Request a new appointment
![Request a new appointment](./images/request-new-appointment.png)
#### 5. Cancel an appointment
![Cancel an appointment](./images/cancel-appointment.png)
#### 6. View announcements
![View announcements](./images/view-announcements-android.png)
#### 7. View announcement
![View announcement](./images/view-announcement.png)
#### 8. View articles
![View articles](./images/view-articles-android.png)
#### 9. View article
![View article](./images/view-article.png)
