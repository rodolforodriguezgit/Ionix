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

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- Docker y Docker Compose

## Instalación y Configuración

clonar el repo:
url https://github.com/rodolforodriguezgit/Ionix

iniciar docker:
docker-compose up -d

##BD Mysql y e manejador de BD Phpmyadmin que va a estar iniciado:
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


La aplicación estará disponible en: http://localhost:8082

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

#### 2. Listar Usuarios
```http
GET /api/users
```

#### 3. Obtener Usuario por Email
```http
GET /api/users/email/{email}
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

