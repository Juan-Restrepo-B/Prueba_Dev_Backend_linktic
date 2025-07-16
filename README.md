🧩 Descripción General
El sistema está compuesto por dos microservicios:
productos-service: Gestiona el CRUD de productos.
inventario-service: Gestiona el stock de productos, consulta, actualización tras compra, y registra eventos de stock.
Ambos microservicios se comunican vía HTTP + JSON:API, protegidos con autenticación por API Key.

📐 Arquitectura del Sistema
                                                +------------------------+
                                                |     API Gateway (op)   |
                                                |      o Cliente REST    |
                                                +------------------------+
                                                          |
                                                          v
                                                +------------------------+
                                                | productos-service      |
                                                | - CRUD Productos       |
                                                | - JSON:API             |
                                                +------------------------+
                                                          |
                                                          | HTTP + API Key
                                                          v
                                                +------------------------+
                                                | inventario-service     |
                                                | - Verificar stock      |
                                                | - Actualizar stock     |
                                                | - Emitir evento (log)  |
                                                +------------------------+

📄 Funcionalidades por Servicio

🧾 productos-service
POST /productos: Crear producto
GET /productos/{id}: Obtener producto por ID
PUT /productos/{id}: Actualizar producto
DELETE /productos/{id}: Eliminar producto
GET /productos?page=0&size=5: Paginación

📦 inventario-service
GET /inventarios/{productoId}: Obtener cantidad disponible
PUT /inventarios/{productoId}?cantidadComprada=X: Actualizar cantidad

Registra movimiento en consola y en base de datos

🔐 Seguridad
Ambos servicios requieren X-API-KEY en el header de cada request.

La clave se define en application.yml:
app:
  api-key: secreta123

📊 Logs Estructurados
Integración con Logstash Encoder:
<dependency>
  <groupId>net.logstash.logback</groupId>
  <artifactId>logstash-logback-encoder</artifactId>
  <version>7.2</version>
</dependency>
Formato JSON enriquecido para fácil trazabilidad.

⚙️ Health Checks & Actuator
Endpoints expuestos en ambos servicios:
GET /actuator/health
GET /actuator/info
GET /actuator/metrics

Configuración application.yml:
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics
  endpoint:
    health:
      show-details: always

🗂️ Modelo Relacional
📌 productos
| Campo       | Tipo          | Reglas                       |
| ----------- | ------------- | ---------------------------- |
| id          | BIGINT        | PK, AUTO\_INCREMENT          |
| nombre      | VARCHAR(100)  | NOT NULL                     |
| precio      | DECIMAL(10,2) | NOT NULL                     |
| created\_at | TIMESTAMP     | DEFAULT CURRENT\_TIMESTAMP   |
| updated\_at | TIMESTAMP     | ON UPDATE CURRENT\_TIMESTAMP |
📌 inventarios
| Campo        | Tipo      | Reglas                       |
| ------------ | --------- | ---------------------------- |
| id           | BIGINT    | PK, AUTO\_INCREMENT          |
| producto\_id | BIGINT    | FK → productos(id)           |
| cantidad     | INT       | NOT NULL DEFAULT 0           |
| created\_at  | TIMESTAMP | DEFAULT CURRENT\_TIMESTAMP   |
| updated\_at  | TIMESTAMP | ON UPDATE CURRENT\_TIMESTAMP |
📌 movimientos_stock
| Campo              | Tipo         | Reglas                        |
| ------------------ | ------------ | ----------------------------- |
| id                 | BIGINT       | PK, AUTO\_INCREMENT           |
| producto\_id       | BIGINT       | FK → productos(id)            |
| cantidad\_anterior | INT          |                               |
| cantidad\_nueva    | INT          |                               |
| tipo\_movimiento   | ENUM         | 'INICIAL', 'AJUSTE', 'COMPRA' |
| descripcion        | VARCHAR(255) |                               |
| realizado\_en      | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP    |

🐳 Orquestación con Docker Compose
version: '3.8'

services:
  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: prueba_tecnica_dev
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  productos-service:
    build: ./productos-service
    environment:
      DB_USERNAME: root
      DB_PASSWORD: root
    ports:
      - "8081:8081"
    depends_on:
      - mysql

  inventario-service:
    build: ./inventario-service
    environment:
      DB_USERNAME: root
      DB_PASSWORD: root
      PRODUCT_SERVICE_URL: http://productos-service:8081
    ports:
      - "8082:8082"
    depends_on:
      - mysql
      - productos-service

volumes:
  mysql-data:

✅ Cobertura de Código
Pruebas unitarias con JaCoCo

Configuración de umbral mínimo en pom.xml:
<limit>
  <counter>INSTRUCTION</counter>
  <value>COVEREDRATIO</value>
  <minimum>0.80</minimum>
</limit>

Resultados en:
📂 target/site/jacoco/index.html

🧪 Herramientas Usadas
Java 17
Spring Boot 3.2.x
JPA + MySQL
Swagger UI (/swagger-ui.html)
JaCoCo
Logstash Encoder
Docker