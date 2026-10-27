# MediSoft - GA7-220501096-AA3-EV01

Módulo web para gestionar citas médicas. Se seleccionó el canal web de MediSoft y se implementó con **Spring Boot, Spring MVC, Thymeleaf, Spring JDBC (`JdbcTemplate`) y MySQL**. La selección continúa el flujo de citas del proyecto existente y aplica el framework Java Spring Boot revisado en el componente formativo *Desarrollo de aplicaciones web con Java* (apartado 5, pp. 98-127).

## Alcance de la evidencia

- **Framework Java:** Spring Boot 3.5.16 y Spring MVC.
- **Interfaz web:** formularios Thymeleaf con solicitudes GET y POST.
- **Persistencia:** JDBC parametrizado mediante `JdbcTemplate` y MySQL.
- **Operaciones:** insertar, listar, consultar por ID, actualizar y eliminar citas.
- **Validación:** campos obligatorios, longitudes y valores permitidos para el estado.
- **Versionamiento:** repositorio Git local con commit inicial.
- **Convenciones:** paquetes en minúscula, clases en PascalCase, métodos/variables en camelCase, constantes en MAYÚSCULAS y comentarios JavaDoc en los componentes principales.

## Requisitos

- JDK 17 o superior.
- Apache Maven 3.6.3 o superior.
- MySQL 8.x en ejecución.

## 1. Preparar la base de datos

En MySQL Workbench, abre y ejecuta `database/schema.sql` con una cuenta administradora. El script crea el esquema independiente `medisoft_aa3` y la tabla `citas`. Luego crea el usuario de aplicación siguiendo los comentarios del mismo script y reemplaza `CAMBIAR_POR_CLAVE_LOCAL` por una clave propia. No guardes la clave en Git.

## 2. Configurar la conexión en PowerShell

Abre PowerShell y establece las variables en la misma ventana en la que iniciarás el servidor. Sustituye la clave por la que asignaste al usuario MySQL:

```powershell
$env:MEDISOFT_AA3_DB_URL = 'jdbc:mysql://localhost:3306/medisoft_aa3?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8'
$env:MEDISOFT_AA3_DB_USER = 'medisoft_aa3_app'
$env:MEDISOFT_AA3_DB_PASSWORD = 'TU_CLAVE_LOCAL'
```

## 3. Ejecutar el módulo

Desde la carpeta raíz del proyecto (donde está `pom.xml`):

```powershell
mvn spring-boot:run
```

Abre `http://localhost:8080`. Spring Boot inicia su servidor web embebido. Si la conexión falla, verifica que MySQL esté iniciado, que se haya creado `medisoft_aa3`, y que usuario/clave coincidan con las variables de entorno.

## 4. Empaquetar

```powershell
mvn clean package
```

El ejecutable se genera en `target\medisoft-aa3-web-1.0.0.jar` y se puede ejecutar con:

```powershell
java -jar target\medisoft-aa3-web-1.0.0.jar
```

## Flujo CRUD

1. **Consultar:** el listado se carga en `GET /citas`; filtrar por ID utiliza `GET /citas?id=...`.
2. **Crear:** `GET /citas/nueva` muestra el formulario y `POST /citas` inserta.
3. **Actualizar:** `GET /citas/{id}/editar` carga el registro y `POST /citas/{id}` guarda los cambios.
4. **Eliminar:** el botón de la tabla envía `POST /citas/{id}/eliminar`.

## Estructura

```text
src/main/java/co/medisoft/aa3/
  model/Cita.java
  repository/CitaRepository.java
  service/CitaService.java
  web/CitaController.java
  web/InicioController.java
src/main/resources/
  templates/citas/lista.html
  templates/citas/formulario.html
  static/assets/styles.css
  application.properties
 database/schema.sql
```

## Git y enlace remoto

El directorio contiene el repositorio Git local y su commit inicial. El archivo `repositorio.txt` está listo para completar la URL después de crear el repositorio remoto en GitHub o GitLab; no se incluye una URL ficticia. Para publicarlo, crea un repositorio remoto vacío y ejecuta desde esta carpeta:

```powershell
git remote add origin https://github.com/USUARIO/REPOSITORIO.git
git push -u origin main
```

Actualiza `repositorio.txt` con la URL real y registra ese cambio:

```powershell
git add repositorio.txt
git commit -m "Documenta enlace del repositorio remoto"
git push
```

## Trazabilidad con MediSoft

La evidencia reutiliza el módulo de citas médicas del proyecto MediSoft y sus campos ya definidos (documento y nombre del paciente, especialidad, médico, fecha/hora y estado). La interfaz conserva la identidad visual del módulo anterior; la persistencia se mantiene aislada en `medisoft_aa3`.
