#!/bin/bash

# Script para poblar el sistema CentroEducativo con datos de ejemplo
# Requiere que el backend esté corriendo en http://localhost:9090/CentroEducativo

# 1. Autenticación (como administrador de datos)
echo "Autenticando administrador..."
ADMIN_DNI="11111111M"
ADMIN_PASS="654321"
LOGIN_RESPONSE=$(curl -s --data "{\"dni\":\"$ADMIN_DNI\",\"password\":\"$ADMIN_PASS\"}" \
  -X POST -H "content-type: application/json" \
  http://localhost:9090/CentroEducativo/login -c cucu)

API_KEY=$(cat cucu | grep JSESSIONID | awk '{print $7}' | cut -d'=' -f2)
echo "Sesión iniciada. API_KEY: $API_KEY"

# 2. Añadir alumnos de ejemplo
echo "Añadiendo alumnos de ejemplo..."
curl -s --data '{"apellidos":"Wick","dni":"33445566X","nombre":"John","password":"cuidain","email":"john.wick@email.com"}' \
  -X POST -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/alumnos?key=$API_KEY" \
  -c cucu -b cucu

curl -s --data '{"apellidos":"Doe","dni":"12345678Z","nombre":"Jane","password":"clave123","email":"jane.doe@email.com"}' \
  -X POST -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/alumnos?key=$API_KEY" \
  -c cucu -b cucu

# 3. Añadir asignaturas de ejemplo
echo "Añadiendo asignaturas de ejemplo..."
curl -s --data '{"acronimo":"DEW","nombre":"Desarrollo de Entornos Web"}' \
  -X POST -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/asignaturas?key=$API_KEY" \
  -c cucu -b cucu

curl -s --data '{"acronimo":"PROG","nombre":"Programación"}' \
  -X POST -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/asignaturas?key=$API_KEY" \
  -c cucu -b cucu

# 4. Consultar alumnos y asignaturas para verificar
echo "Consultando listado de alumnos y asignaturas..."
curl -s -X GET -H "accept: application/json" \
  "http://localhost:9090/CentroEducativo/alumnos?key=$API_KEY" \
  -c cucu -b cucu | jq

curl -s -X GET -H "accept: application/json" \
  "http://localhost:9090/CentroEducativo/asignaturas?key=$API_KEY" \
  -c cucu -b cucu | jq

echo "Poblamiento completado." 