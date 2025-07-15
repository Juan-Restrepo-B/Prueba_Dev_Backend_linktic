                    +------------------------+
                    |     API Gateway (op)   |
                    |      o Cliente REST    |
                    +------------------------+
                              |
                              |
                   +----------+----------+
                   | productos-service    |
                   | - CRUD Productos     |
                   | - JSON:API           |
                   +----------+-----------+
                              |
                              | HTTP + JSON:API + API Key
                              v
                   +----------+-----------+
                   | inventario-service   |
                   | - Verificar stock    |
                   | - Actualizar stock   |
                   | - Evento por consola |
                   +----------------------+

      Docker Compose: orquesta ambos + MySQL en AWS


### 🗂️ Diagrama Relacional de la Base de Datos

**productos**

| Campo       | Tipo        | Detalles        |
|-------------|-------------|-----------------|
| id          | BIGINT      | PK, AUTO_INCREMENT |
| nombre      | VARCHAR(100)| NOT NULL        |
| precio      | DECIMAL(10,2)| NOT NULL        |
| created_at  | TIMESTAMP   | DEFAULT CURRENT_TIMESTAMP |
| updated_at  | TIMESTAMP   | ON UPDATE CURRENT_TIMESTAMP |

---

**inventarios**

| Campo       | Tipo        | Detalles        |
|-------------|-------------|-----------------|
| id          | BIGINT      | PK, AUTO_INCREMENT |
| producto_id | BIGINT      | FK → productos(id) |
| cantidad    | INT         | NOT NULL, DEFAULT 0 |
| created_at  | TIMESTAMP   | DEFAULT CURRENT_TIMESTAMP |
| updated_at  | TIMESTAMP   | ON UPDATE CURRENT_TIMESTAMP |

---

**movimientos_stock**

| Campo           | Tipo        | Detalles                    |
|------------------|-------------|-----------------------------|
| id               | BIGINT      | PK, AUTO_INCREMENT          |
| producto_id      | BIGINT      | FK → productos(id)          |
| cantidad_anterior| INT         |                             |
| cantidad_nueva   | INT         |                             |
| tipo_movimiento  | ENUM        | 'INICIAL', 'AJUSTE', 'COMPRA' |
| descripcion      | VARCHAR(255)|                             |
| realizado_en     | TIMESTAMP   | DEFAULT CURRENT_TIMESTAMP   |



-- Creacion de la tabla de productos --
CREATE TABLE `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Creacion de la tabla de inventario --
CREATE TABLE `inventarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `producto_id` bigint NOT NULL,
  `cantidad` int NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `inventarios_productos_FK` (`producto_id`),
  CONSTRAINT `inventarios_productos_FK` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Creacion a la tabla encargada de manejar los movimientos del stock --
CREATE TABLE `movimientos_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `producto_id` bigint NOT NULL,
  `cantidad_anterior` int NOT NULL,
  `cantidad_nueva` int NOT NULL,
  `tipo_movimiento` enum('INICIAL','AJUSTE','COMPRA') NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `realizado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `movimientos_stock_productos_FK` (`producto_id`),
  CONSTRAINT `movimientos_stock_productos_FK` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
