# Ionix API

API REST desarrollada con Java 17 y Spring Boot 3, siguiendo principios SOLID y Clean Code.

## Características

- ✅ Java 17
- ✅ Spring Boot 3.2.0
- ✅ Spring Data JPA / Hibernate
- ✅ Spring Security
- ✅ MySQL 8.0
- ✅ Docker Compose para MySQL y phpMyAdmin
- ✅ Cifrado DES
- ✅ Integración con API externa
- ✅ Tests unitarios
- ✅ Principios SOLID y Clean Code

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/ionix/
│   │   ├── domain/              # Capa de dominio
│   │   │   ├── model/           # Entidades JPA
│   │   │   └── dto/             # Data Transfer Objects
│   │   ├── application/         # Capa de aplicación
│   │   │   ├── service/         # Interfaces de servicios
│   │   │   ├── service/impl/    # Implementaciones de servicios
│   │   │   └── mapper/          # Mappers
│   │   └── infrastructure/      # Capa de infraestructura
│   │       ├── config/          # Configuraciones
│   │       ├── controller/      # Controladores REST
│   │       └── repository/      # Repositorios JPA
│   └── resources/
│       └── application.properties
└── test/                        # Tests unitarios
```

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- Docker y Docker Compose

## Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd Ionix
```

### 2. Iniciar MySQL y phpMyAdmin con Docker

```bash
docker-compose up -d
```

Esto iniciará:
- **MySQL** en el puerto `3306`
- **phpMyAdmin** en el puerto `8081`

Acceso a phpMyAdmin: http://localhost:8081
- Usuario: `root`
- Contraseña: `rootpassword`

### 3. Configurar la aplicación

Las configuraciones están en `src/main/resources/application.properties`.

#### Variable de Entorno para Cifrado DES

La llave de cifrado DES puede configurarse mediante variable de entorno:

**Windows (PowerShell):**
```powershell
$env:IONIX_DES_KEY="ionix123456"
```

**Windows (CMD):**
```cmd
set IONIX_DES_KEY=ionix123456
```

**Linux/Mac:**
```bash
export IONIX_DES_KEY=ionix123456
```

Si no se define la variable de entorno, se usará el valor por defecto: `ionix123456`

**Otras configuraciones en `application.properties`:**

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/ionix_db
spring.datasource.username=root
spring.datasource.password=rootpassword

# Seguridad
app.security.username=admin
app.security.password=admin123

# API Externa
app.external.api.url=https://my.api.mockaroo.com/test-tecnico/search
app.external.api.key=f2f719e0

# Cifrado DES (puede ser sobrescrito con IONIX_DES_KEY)
app.encryption.des.key=${IONIX_DES_KEY:ionix123456}
```

### 4. Compilar y ejecutar

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

## Endpoints de la API

### Usuarios

#### 1. Crear Usuario (Protegido)
```http
POST /api/users
Authorization: Basic admin:admin123
Content-Type: application/json

{
  "name": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

**Respuesta:**
```json
{
  "id": 1,
  "name": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "phone": "1234567890",
  "createdAt": "2024-01-01T10:00:00"
}
```

#### 2. Listar Usuarios
```http
GET /api/users
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "phone": "1234567890",
    "createdAt": "2024-01-01T10:00:00"
  }
]
```

#### 3. Obtener Usuario por Email
```http
GET /api/users/email/{email}
```

**Ejemplo:**
```http
GET /api/users/email/john@example.com
```

#### 4. Eliminar Usuario (Protegido)
```http
DELETE /api/users/{id}
Authorization: Basic admin:admin123
```

### Búsqueda Externa

#### 5. Buscar en API Externa
```http
POST /api/search
Content-Type: application/json

{
  "param": "1-9"
}
```

**Respuesta:**
```json
{
  "responseCode": 0,
  "description": "OK",
  "elapsedTime": 245,
  "result": {
    "registerCount": 5
  }
}
```

**Nota:** Este servicio:
1. Recibe un parámetro (`param`)
2. Lo cifra usando DES con la llave "ionix123456"
3. Consulta la API externa con el parámetro cifrado
4. Retorna la cantidad de registros y el tiempo de ejecución

## Seguridad

Los endpoints de creación y eliminación de usuarios están protegidos con **Spring Security** usando autenticación HTTP Basic.

**Credenciales por defecto:**
- Usuario: `admin`
- Contraseña: `admin123`

Estas credenciales se pueden modificar en `application.properties`.

## Principios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada clase tiene una única responsabilidad
- `UserService`: gestión de usuarios
- `EncryptionService`: cifrado
- `ExternalApiService`: comunicación con API externa

### Open/Closed Principle (OCP)
- Uso de interfaces para servicios, permitiendo extensión sin modificación
- `UserService`, `EncryptionService`, `ExternalApiService` son interfaces

### Liskov Substitution Principle (LSP)
- Las implementaciones pueden sustituirse por sus interfaces sin afectar el comportamiento

### Interface Segregation Principle (ISP)
- Interfaces específicas y pequeñas (`EncryptionService`, `ExternalApiService`)

### Dependency Inversion Principle (DIP)
- Dependencias hacia abstracciones (interfaces), no hacia implementaciones concretas

## Clean Code

- ✅ Nombres descriptivos y significativos
- ✅ Funciones pequeñas y con un solo propósito
- ✅ Separación de responsabilidades por capas
- ✅ Uso de DTOs para transferencia de datos
- ✅ Manejo de excepciones centralizado
- ✅ Logging apropiado
- ✅ Validaciones de entrada
- ✅ Documentación en código

## Testing

Ejecutar tests unitarios:

```bash
mvn test
```

Los tests cubren:
- Servicios de usuario
- Servicio de cifrado DES
- Servicio de búsqueda
- Controladores REST

## Tecnologías Utilizadas

- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **Hibernate**: ORM
- **Spring Security**: Seguridad
- **MySQL 8.0**: Base de datos
- **Docker**: Contenedores
- **Maven**: Gestión de dependencias
- **JUnit 5**: Testing
- **Mockito**: Mocking en tests
- **Lombok**: Reducción de boilerplate

## Ejemplos de Uso

### Crear un usuario (con autenticación)

```bash
curl -X POST http://localhost:8080/api/users \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "phone": "1234567890"
  }'
```

### Listar usuarios

```bash
curl http://localhost:8080/api/users
```

### Buscar en API externa

```bash
curl -X POST http://localhost:8080/api/search \
  -H "Content-Type: application/json" \
  -d '{
    "param": "1-9"
  }'
```

## Base de Datos

La base de datos se crea automáticamente al iniciar la aplicación. El esquema se genera mediante Hibernate con `ddl-auto=update`.

### Estructura de la tabla `users`:

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);
```

## Troubleshooting

### Error de conexión a MySQL
- Verificar que Docker Compose esté ejecutándose: `docker-compose ps`
- Verificar que el puerto 3306 no esté en uso
- Revisar las credenciales en `application.properties`

### Error de autenticación
- Verificar las credenciales en `application.properties`
- Usar el formato correcto de Basic Auth: `admin:admin123`

### Error al llamar API externa
- Verificar conectividad a internet
- Verificar que la API key sea válida
- Revisar los logs de la aplicación

## Autor

Desarrollado siguiendo las mejores prácticas de desarrollo de software.

## Licencia

Este proyecto es de uso interno.
