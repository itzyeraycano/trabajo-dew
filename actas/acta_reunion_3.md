# Acta de Reunión #3 - Desarrollo de Servicios y Gestión de Notas

## Fecha: 03/06/2025

## Participantes
- Caballero Martínez, Fabio
- Cano Moya, Yeray
- Domenech Gascó, Marc

## Temas Tratados

### 1. Desarrollo de Servicios REST
- Cano Moya, Yeray: Explicación de la arquitectura de servicios
  - Servlet como túnel entre código local y API
  - Servicios para manipulación de datos locales
  - Implementación de métodos REST (GET, POST, PUT)

### 2. Implementación de Gestión de Notas
- Caballero Martínez, Fabio: Modificación de info_asignatura.jsp
- Desarrollo de nuevo JavaScript para gestión de alumnos
- Implementación de servicios para modificación de notas
- Integración con el servlet existente

### 3. Optimización de Código
- Análisis de la estructura de servlets
- Integración de funcionalidades en servlets existentes
- Mejora en la gestión de datos JSON

## Decisiones Técnicas
1. Uso de métodos PUT para actualización de notas
2. Estructura de comunicación API-Servlet-Servicio
3. Formato de transferencia de datos
4. Optimización de llamadas a la API

## Componentes Desarrollados
- Modificación de info_asignatura.jsp
- Nuevo JavaScript para tabla de alumnos
- Servicios de modificación de notas
- Integración con API REST

## Tareas Asignadas
- Cano Moya, Yeray: Arquitectura de servicios
- Caballero Martínez, Fabio: Implementación de gestión de notas
- Domenech Gascó, Marc: Testing y validación

## Próximos Pasos
1. Testing de modificación de notas
2. Implementación de listado de asignaturas con notas
3. Optimización de servicios

## Documentación
- Arquitectura de servicios REST
- Flujo de datos entre componentes
- Guía de implementación de servicios 