# Documentación Técnica del Proyecto NOL2425

## 1. Filtros (Filters)

### 1.1. AuthFiltro
**Ubicación**: `dew.filtros.AuthFiltro`

**Propósito**: Gestiona la autenticación de los usuarios. Valida las credenciales, obtiene el API key y la cookie de sesión, y guarda los datos en la sesión.

**Métodos importantes**:
- `init(FilterConfig config)`: Inicializa el filtro cargando las credenciales desde `/WEB-INF/credenciales`.
- `doFilter(ServletRequest, ServletResponse, FilterChain)`: Verifica la autenticación del usuario y realiza el login si es necesario.

**Relación con otras clases**: 
- Utiliza `ClienteCentro` para la autenticación
- Interactúa con `ValidadorInput` para validar credenciales
- Usa `AuthResult` para manejar la respuesta de autenticación

### 1.2. Logs (Versión 2)
**Ubicación**: `dew.filtros.Logs`

**Propósito**: Implementa el registro de logs como Filter. Registra todas las peticiones HTTP con información detallada.

**Configuración de logs**:
- Ruta del archivo: `/home/dew/git/trabajo-dew/NOL2425/src/main/webapp/logs/nol2425.log`
- Formato de log: `[FECHA_HORA] [USUARIO] [IP] [URI] [MÉTODO]`

**Ejemplo de línea de log**:
```
2024-03-21T14:30:22.123 alumno1 192.168.1.100 /NOL2425/asignaturas GET
```

### 1.3. DebugFilter
**Ubicación**: `dew.filtros.DebugFilter`

**Propósito**: Filtro de depuración que imprime información sobre las peticiones HTTP en la consola.

**Métodos importantes**:
- `doFilter(ServletRequest, ServletResponse, FilterChain)`: Imprime el método y URI de cada petición.

## 2. Servlets

### 2.1. AlumnoServlet
**Ubicación**: `dew.main.AlumnoServlet`

**Propósito**: Maneja las peticiones relacionadas con los datos de los alumnos. Permite ver el perfil individual o la lista de todos los alumnos.

**Métodos importantes**:
- `doGet(HttpServletRequest, HttpServletResponse)`: Procesa peticiones GET para mostrar datos de alumnos
- `mostrarPerfil(HttpServletRequest, HttpServletResponse)`: Muestra el perfil del alumno actual
- `mostrarLista(HttpServletRequest, HttpServletResponse)`: Muestra la lista de todos los alumnos

**Relación con otras clases**:
- Utiliza `ServicioAlumno` para obtener datos
- Interactúa con las vistas JSP: `ficha_alumno.jsp` y `lista_alumnos.jsp`

### 2.2. AsignaturaServlet
**Ubicación**: `dew.main.AsignaturaServlet`

**Propósito**: Gestiona la visualización de detalles de asignaturas específicas.

**Métodos importantes**:
- `doGet(HttpServletRequest, HttpServletResponse)`: Procesa peticiones GET para mostrar detalles de una asignatura

**Relación con otras clases**:
- Utiliza `ServicioAsignatura` para obtener datos
- Interactúa con la vista `info_asignatura.jsp`

## 3. Servicios

### 3.1. ClienteCentro
**Ubicación**: `dew.servicios.ClienteCentro`

**Propósito**: Cliente HTTP para interactuar con la API REST del Centro Educativo.

**Métodos importantes**:
- `autenticarUsuario(String dni, String pass)`: Realiza la autenticación y obtiene apiKey y sessionCookie
- `obtenerRecurso(String recurso, String apiKey, String cookie)`: Método general para obtener recursos REST

**Relación con otras clases**:
- Retorna objetos `AuthResult`
- Utilizado por los servicios `ServicioAlumno` y `ServicioAsignatura`

### 3.2. ServicioAlumno
**Ubicación**: `dew.servicios.ServicioAlumno`

**Propósito**: Gestiona la recuperación de información de alumnos desde la API.

**Métodos importantes**:
- `obtenerAlumno(ServletContext, HttpSession)`: Recupera datos completos de un alumno y sus asignaturas

**Relación con otras clases**:
- Utiliza `ClienteCentro` para las peticiones HTTP
- Maneja objetos `Alumno` y `Asignatura`

### 3.3. ServicioAsignatura
**Ubicación**: `dew.servicios.ServicioAsignatura`

**Propósito**: Maneja la información de asignaturas de los alumnos.

**Métodos importantes**:
- `obtenerAsignaturasPorAlumno(ServletContext, HttpSession)`: Obtiene lista de asignaturas de un alumno
- `obtenerDetallesAsignatura(ServletContext, HttpSession, String)`: Obtiene detalles de una asignatura específica

**Relación con otras clases**:
- Utiliza `ClienteCentro` para las peticiones HTTP
- Maneja objetos `Asignatura`

## 4. Clases de Utilidad

### 4.1. ValidadorInput
**Ubicación**: `dew.filtros.ValidadorInput`

**Propósito**: Valida entradas de formularios y datos para prevenir inyecciones y ataques.

**Métodos importantes**:
- `esDniValido(String)`: Valida formato de DNI
- `esValido(String)`: Verifica si una entrada es segura
- `tieneRiesgoSql(String)`: Detecta posibles inyecciones SQL
- `tieneRiesgoXss(String)`: Detecta posibles ataques XSS

### 4.2. AuthResult
**Ubicación**: `dew.servicios.AuthResult`

**Propósito**: Almacena el resultado de la autenticación (apiKey y sessionCookie).

**Métodos importantes**:
- `getApiKey()`: Obtiene la apiKey
- `getSessionCookie()`: Obtiene la cookie de sesión

## 5. Configuración

### 5.1. Web.xml
**Ubicación**: `/WEB-INF/web.xml`

**Configuraciones importantes**:
- Base URL del Centro Educativo: `http://localhost:9090/CentroEducativo`
- Filtros configurados:
  - Logs (aplicado a todas las URLs: `/*`)
  - AuthFiltro (aplicado a todas las URLs: `/*`)
- Servlets mapeados:
  - AlumnoServlet: `/alumno` y `/alumnos`
  - AsignaturaServlet: `/asignatura` y `/asignaturas` 