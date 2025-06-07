# Acta de Reunión #1 - Implementación del Sistema de Login

## Fecha: 28/05/2025

## Participantes
- Cano Moya, Yeray
- Marín Hernández, José
- Makmar, Laila
- Domenech Gascó, Marc
- Caballero Martínez, Fabio

## Temas Tratados

### 1. Sistema de Login Unificado
- Decisión de implementar un único login para alumnos y profesores
- Implementación del CookieStore por Cano Moya, Yeray para gestión automática de cookies
- Desarrollo del sistema de logout con limpieza de cookies

### 2. Configuración del Sistema
- Marín Hernández, José: Actualización de tomcat-users.xml con usuarios de profesores
- Marín Hernández, José: Creación del script poblar-centro-educativo.sh
- Configuración inicial de la base de datos

### 3. Desarrollo Frontend Inicial
- Makmar, Laila: Diseño del modelo frontend para vistas de profesor
- Creación de ficha_profesor.jsp provisional
- Establecimiento de estándares de diseño diferenciados para alumnos y profesores

## Decisiones Técnicas
1. Implementación de CookieStore para gestión de sesiones
2. Sistema de login unificado
3. Estructura de vistas separadas para profesores y alumnos

## Componentes Desarrollados
- Sistema de autenticación unificado
- Script de población de datos
- Vistas JSP iniciales
- Configuración de usuarios en Tomcat

## Tareas Asignadas
- Cano Moya, Yeray: Gestión de cookies y autenticación
- Marín Hernández, José: Scripts y configuración del sistema
- Makmar, Laila: Frontend de profesor
- Domenech Gascó, Marc & Caballero Martínez, Fabio: Análisis de requisitos de acceso

## Próximos Pasos
1. Implementación de servlets de profesor
2. Desarrollo de vistas principales
3. Integración con la base de datos

## Problemas Identificados
- Necesidad de gestión de permisos diferenciados
- Requisitos de seguridad en el acceso
- Estructura de datos para profesores

## Documentación
- Configuración del sistema de logs
- Estructura de autenticación
- Guía de despliegue inicial 