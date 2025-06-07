# Acta de Reunión #2 - Implementación de Gestión de Asignaturas

## Fecha: 29/05/2025

## Participantes
- Domenech Gascó, Marc
- Caballero Martínez, Fabio
- Marín Hernández, José
- Genés Bastidas, Rubén 
- Makmar, Laila

## Temas Tratados

### 1. Gestión de Asignaturas
- Marín Hernández, José: Implementación de asignación de asignaturas a profesores
- Actualización del script de población para incluir asignaciones
- Desarrollo de la lógica de gestión de asignaturas

### 2. Desarrollo de Servlets
- Caballero Martínez, Fabio: Implementación del listado de alumnos por asignatura
- Desarrollo de servicios para gestión de asignaturas
- Integración con la API REST

### 3. Permisos y Accesos
- Domenech Gascó, Marc & Caballero Martínez, Fabio: Análisis de permisos de acceso
- Definición de visibilidad de información:
  - Alumnos: Solo acceso a sus propias notas
  - Profesores: Acceso a todos los alumnos de sus asignaturas

## Decisiones Técnicas
1. Estructura de datos para asignaturas
2. Sistema de permisos basado en roles
3. Implementación de servicios REST
4. Formato JSON para transferencia de datos

## Componentes Desarrollados
- Servlets de gestión de asignaturas
- Servicios de acceso a datos
- Interfaces de listado de alumnos
- Sistema de permisos

## Tareas Asignadas
- Marín Hernández, José: Gestión de asignaturas y profesores
- Caballero Martínez, Fabio: Desarrollo de listados y servicios
- Domenech Gascó, Marc: Análisis de seguridad
- Cano Moya, Yeray: Integración con API

## Próximos Pasos
1. Implementación de modificación de notas
2. Desarrollo de vista de asignaturas por alumno
3. Testing de permisos y accesos

## Problemas Identificados
- Control de acceso granular
- Gestión de datos entre roles
- Optimización de consultas

## Documentación
- Estructura de servicios REST
- Mapeo de datos JSON
- Flujo de navegación 