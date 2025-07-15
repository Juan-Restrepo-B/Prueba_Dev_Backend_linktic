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
