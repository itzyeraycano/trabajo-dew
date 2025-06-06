#!/bin/bash

# Script para poblar el sistema CentroEducativo con datos de ejemplo
# Requiere que el backend esté corriendo en http://localhost:9090/CentroEducativo

# -- Configuración --
BASE_URL="http://localhost:9090/CentroEducativo"
ADMIN_DNI="111111111"
ADMIN_PASS="654321"
ADMIN_COOKIE_FILE="cucu_admin.txt"

PROFESOR_DNI_PABLO="69696969J" # DNI del profesor Pablo Martines
PROFESOR_PASS_PABLO="hola1234" # Password del profesor Pablo Martines
PROFESOR_NOMBRE_PABLO="pablo"
PROFESOR_APELLIDOS_PABLO="martines"
PROFESOR_COOKIE_FILE="cucu_profesor_pablo.txt" # Cookie file para este profesor

# Limpiar archivos de cookies anteriores
rm -f $ADMIN_COOKIE_FILE $PROFESOR_COOKIE_FILE
echo "Archivos de cookies anteriores eliminados (si existían)."
echo ""

# --- PASO 1: AUTENTICACIÓN DEL ADMINISTRADOR ---
echo "--- PASO 1: Autenticando administrador ($ADMIN_DNI)... ---"
ADMIN_API_KEY_RAW=$(curl -s -d "{\"dni\":\"$ADMIN_DNI\",\"password\":\"$ADMIN_PASS\"}" \
  -X POST "${BASE_URL}/login" -H "content-type: application/json" \
  -c $ADMIN_COOKIE_FILE) 

ADMIN_API_KEY=$(echo "$ADMIN_API_KEY_RAW" | tr -d '"')

if [[ -z "$ADMIN_API_KEY" || "$ADMIN_API_KEY" == \{* ]]; then
  echo "Error: No se pudo obtener la API Key del administrador o el login falló."
  echo "Respuesta del servidor (login admin): $ADMIN_API_KEY_RAW"
  exit 1
fi
echo "Autenticación de administrador exitosa. ADMIN_API_KEY: $ADMIN_API_KEY"
echo ""


# --- PASO 2: AÑADIR ALUMNOS DE EJEMPLO ---
echo "--- PASO 2: Añadiendo alumnos de ejemplo... ---"
# (John Wick, Bruce Wayne, Clark Kent)
curl -s --data "{\"apellidos\":\"Wick\",\"dni\":\"33445566X\",\"nombre\":\"John\",\"password\":\"cuidadin\"}" \
  -X POST -H "content-type: application/json" -H "accept: text/plain" \
  "${BASE_URL}/alumnos?key=$ADMIN_API_KEY" -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  John Wick procesado."

curl -s --data "{\"apellidos\":\"Wayne\",\"dni\":\"11223344A\",\"nombre\":\"Bruce\",\"password\":\"batman\"}" \
  -X POST -H "content-type: application/json" -H "accept: text/plain" \
  "${BASE_URL}/alumnos?key=$ADMIN_API_KEY" -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  Bruce Wayne procesado."

curl -s --data "{\"apellidos\":\"Kent\",\"dni\":\"55667788B\",\"nombre\":\"Clark\",\"password\":\"superman\"}" \
  -X POST -H "content-type: application/json" -H "accept: text/plain" \
  "${BASE_URL}/alumnos?key=$ADMIN_API_KEY" -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  Clark Kent procesado."
echo ""

# --- PASO 3: AÑADIR ASIGNATURAS DE EJEMPLO ---
echo "--- PASO 3: Añadiendo asignaturas de ejemplo (PRG, PPE, DEW)... ---"
# DEW se asegura su creación (si no es un default estricto).
curl -s --data '{"acronimo":"DEW","nombre":"Desarrollo de Entornos Web","curso":3,"cuatrimestre":"B","creditos":4.5}' \
  -X POST -H "content-type: application/json" -H "accept: text/plain" \
  "${BASE_URL}/asignaturas?key=$ADMIN_API_KEY" -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  DEW procesada/verificada."

curl -s --data '{"acronimo":"PRG","nombre":"Programación","curso":1,"cuatrimestre":"A","creditos":6.0}' \
  -X POST -H "content-type: application/json" -H "accept: text/plain" \
  "${BASE_URL}/asignaturas?key=$ADMIN_API_KEY" -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  PRG procesada."

curl -s --data '{"acronimo":"PPE","nombre":"Prácticas Profesionales Externas","curso":4,"cuatrimestre":"B","creditos":12.0}' \
  -X POST -H "content-type: application/json" -H "accept: text/plain" \
  "${BASE_URL}/asignaturas?key=$ADMIN_API_KEY" -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  PPE procesada."
echo ""

# --- PASO 4: AÑADIR PROFESOR DE EJEMPLO ---
echo "--- PASO 4: Añadiendo profesor de ejemplo (Pablo Martines)... ---"
curl -s --data "{\"apellidos\":\"$PROFESOR_APELLIDOS_PABLO\",\"dni\":\"$PROFESOR_DNI_PABLO\",\"nombre\":\"$PROFESOR_NOMBRE_PABLO\",\"password\":\"$PROFESOR_PASS_PABLO\"}" \
  -X POST "${BASE_URL}/profesores?key=$ADMIN_API_KEY" \
  -H "accept: text/plain" \
  -H "Content-Type: application/json" \
  -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE
echo "  Profesor Pablo Martines ($PROFESOR_DNI_PABLO) procesado."
echo ""

# --- PASO 5: ASIGNAR ASIGNATURAS AL PROFESOR PABLO MARTINES (usando API Key del Admin) ---
# MODIFICADO: Usando POST /profesores/{dni}/asignaturas y cuerpo como string simple (ACRONIMO)
echo "--- PASO 5: Asignando asignaturas al Profesor Pablo Martines ($PROFESOR_DNI_PABLO)... ---"
ASIGNATURAS_PARA_PROFESOR_PABLO=("DEW" "PRG") # Asignaturas que impartirá Pablo
for ACRONIMO_ASIG_PARA_PROF in "${ASIGNATURAS_PARA_PROFESOR_PABLO[@]}"; do
  echo "  Asignando asignatura: $ACRONIMO_ASIG_PARA_PROF a profesor $PROFESOR_DNI_PABLO"
  # Cuerpo es el ACRÓNIMO de la asignatura como una cadena simple.
  # Content-Type: application/json (como en la inscripción de alumnos que funciona). Sin Accept explícito.
  RESPONSE_ASIG_PROF_BODY_AND_CODE=$(curl -s -w "\nHTTP_STATUS_ASIG_A_PROF:%{http_code}\n" \
    --data "$ACRONIMO_ASIG_PARA_PROF" \
    -X POST "${BASE_URL}/profesores/${PROFESOR_DNI_PABLO}/asignaturas?key=$ADMIN_API_KEY" \
    -H "Content-Type: application/json" \
    -b $ADMIN_COOKIE_FILE -c $ADMIN_COOKIE_FILE)
  echo "    Respuesta al asignar $ACRONIMO_ASIG_PARA_PROF:"
  echo -e "$RESPONSE_ASIG_PROF_BODY_AND_CODE"
done
echo ""

# --- PASO 6: INSCRIBIR ALUMNOS EN ASIGNATURAS (usando API Key del Admin y método que indicaste) ---
echo "--- PASO 6: Inscribiendo alumnos en asignaturas (método que indicaste)... ---"
# John Wick (33445566X) en DEW y PRG
DNI_WICK="33445566X"; ASIGNATURAS_WICK=("DEW" "PRG") 
echo "Inscribiendo a John Wick ($DNI_WICK)..."
for ASIG_ACRONIMO in "${ASIGNATURAS_WICK[@]}"; do
  echo "  Intentando inscribir en: $ASIG_ACRONIMO"
  # Usando el cuerpo como string simple $ASIG_ACRONIMO, Content-Type application/json, sin Accept explícito.
  HTTP_CODE_INSC=$(curl -s -w "%{http_code}" -o /dev/null \
    -X POST "${BASE_URL}/alumnos/${DNI_WICK}/asignaturas?key=$ADMIN_API_KEY" \
    -H "Content-Type: application/json" \
    -d "$ASIG_ACRONIMO" \
    -b $ADMIN_COOKIE_FILE) 
  echo "    Código de Estado HTTP para inscripción en $ASIG_ACRONIMO: $HTTP_CODE_INSC"
done; echo ""

# Bruce Wayne (11223344A) en DEW
DNI_WAYNE="11223344A"; ASIGNATURAS_WAYNE=("DEW" "PPE")
echo "Inscribiendo a Bruce Wayne ($DNI_WAYNE)..."
for ASIG_ACRONIMO in "${ASIGNATURAS_WAYNE[@]}"; do
  echo "  Intentando inscribir en: $ASIG_ACRONIMO"
  HTTP_CODE_INSC=$(curl -s -w "%{http_code}" -o /dev/null \
    -X POST "${BASE_URL}/alumnos/${DNI_WAYNE}/asignaturas?key=$ADMIN_API_KEY" \
    -H "Content-Type: application/json" \
    -d "$ASIG_ACRONIMO" \
    -b $ADMIN_COOKIE_FILE)
  echo "    Código de Estado HTTP para inscripción en $ASIG_ACRONIMO: $HTTP_CODE_INSC"
done; echo ""

# Clark Kent (55667788B) en PRG
DNI_KENT="55667788B"; ASIGNATURAS_KENT=("PRG" "DCU") 
echo "Inscribiendo a Clark Kent ($DNI_KENT)..."
for ASIG_ACRONIMO in "${ASIGNATURAS_KENT[@]}"; do
  echo "  Intentando inscribir en: $ASIG_ACRONIMO"
  HTTP_CODE_INSC=$(curl -s -w "%{http_code}" -o /dev/null \
    -X POST "${BASE_URL}/alumnos/${DNI_KENT}/asignaturas?key=$ADMIN_API_KEY" \
    -H "Content-Type: application/json" \
    -d "$ASIG_ACRONIMO" \
    -b $ADMIN_COOKIE_FILE)
  echo "    Código de Estado HTTP para inscripción en $ASIG_ACRONIMO: $HTTP_CODE_INSC"
done; echo ""


DNI_MINERVA="37264096W"; ASIGNATURAS_MINERVA=("PRG" "DEW") 
echo "Inscribiendo a Clark Kent ($DNI_KENT)..."
for ASIG_ACRONIMO in "${ASIGNATURAS_MINERVA[@]}"; do
  echo "  Intentando inscribir en: $ASIG_ACRONIMO"
  HTTP_CODE_INSC=$(curl -s -w "%{http_code}" -o /dev/null \
    -X POST "${BASE_URL}/alumnos/${DNI_MINERVA}/asignaturas?key=$ADMIN_API_KEY" \
    -H "Content-Type: application/json" \
    -d "$ASIG_ACRONIMO" \
    -b $ADMIN_COOKIE_FILE)
  echo "    Código de Estado HTTP para inscripción en $ASIG_ACRONIMO: $HTTP_CODE_INSC"
done; echo ""

# --- PASO 7: AUTENTICACIÓN DEL PROFESOR PABLO MARTINES (para poner notas) ---
echo "--- PASO 7: Autenticando como Profesor ($PROFESOR_DNI_PABLO) para poner notas... ---"
PROFESOR_API_KEY_RAW=$(curl -s -d "{\"dni\":\"$PROFESOR_DNI_PABLO\",\"password\":\"$PROFESOR_PASS_PABLO\"}" \
  -X POST "${BASE_URL}/login" -H "content-type: application/json" \
  -c $PROFESOR_COOKIE_FILE)

PROFESOR_API_KEY=$(echo "$PROFESOR_API_KEY_RAW" | tr -d '"')

if [[ -z "$PROFESOR_API_KEY" || "$PROFESOR_API_KEY" == \{* ]]; then
  echo "Error: No se pudo obtener la API Key del Profesor ($PROFESOR_DNI_PABLO) o el login falló."
  echo "Respuesta del servidor (login profesor): $PROFESOR_API_KEY_RAW"
  exit 1
fi
echo "Autenticación de profesor ($PROFESOR_DNI_PABLO) exitosa. PROFESOR_API_KEY: $PROFESOR_API_KEY"
echo ""

# --- PASO 8: PONER NOTAS A LOS ALUMNOS (usando API Key del Profesor Pablo) ---
echo "--- PASO 8: Poniendo notas a los alumnos... ---"
# Notas: DNI_ALUMNO;ACRONIMO_ASIGNATURA;NOTA_A_PONER
declare -a notas_a_poner
notas_a_poner[0]="33445566X;DEW;7.5"
notas_a_poner[1]="33445566X;PRG;8.0"
notas_a_poner[2]="11223344A;DEW;9.0"
notas_a_poner[3]="11223344A;PPE;8.0"
notas_a_poner[4]="55667788B;PRG;6.5"
notas_a_poner[5]="55667788B;DCU;9.5"
notas_a_poner[6]="12345678W;DEW;7.0"
notas_a_poner[7]="12345678W;IAP;5.5"
notas_a_poner[8]="12345678W;DCU;9.5"
notas_a_poner[9]="23456387R;DEW;3.5"
notas_a_poner[10]="23456387R;DCU;9.5"
notas_a_poner[11]="34567891F;IAP;5.5"
notas_a_poner[12]="34567891F;DCU;6.5"
notas_a_poner[13]="93847525G;IAP;7.0"
notas_a_poner[14]="93847525G;DEW;6.5"
notas_a_poner[15]="37264096W;PRG;7.0"
notas_a_poner[16]="37264096W;DEW;6.5"

for nota_data in "${notas_a_poner[@]}"; do
  IFS=';' read -r DNI_ALUMNO_NOTA ACRONIMO_ASIG_NOTA NOTA_VALOR <<< "$nota_data"
  
  # MODIFICADO: Acrónimo en el path SIN comillas URL-codificadas.
  # MODIFICADO: Cuerpo de la nota como un número simple (ej. 7.5).
  # MODIFICADO: Sin cabecera "accept: text/plain" explícita (curl usará Accept: /).
  NOTA_BODY="$NOTA_VALOR" 
  
  echo "Poniendo nota $NOTA_VALOR a $DNI_ALUMNO_NOTA en $ACRONIMO_ASIG_NOTA..."
  HTTP_CODE_NOTA=$(curl -s -w "%{http_code}" -o /dev/null \
    -X PUT "${BASE_URL}/alumnos/${DNI_ALUMNO_NOTA}/asignaturas/${ACRONIMO_ASIG_NOTA}?key=$PROFESOR_API_KEY" \
    -H "Content-Type: application/json" \
    --data "$NOTA_BODY" \
    -b $PROFESOR_COOKIE_FILE) 
  echo "  Código de Estado HTTP al poner nota: $HTTP_CODE_NOTA"
done
echo ""


# --- PASO 9: VERIFICACIONES FINALES (usando API Key del Admin) ---
# (Sin cambios en esta sección)
echo "--- PASO 9: Verificaciones finales... ---"
echo "Consultando listado de TODOS los alumnos:"
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/alumnos?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

echo "Consultando listado de TODAS las asignaturas:"
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/asignaturas?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

echo "Consultando listado de TODOS los profesores:"
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/profesores?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

# Verificar asignaturas del profesor Pablo Martines
echo "Consultando asignaturas del profesor Pablo Martines ($PROFESOR_DNI_PABLO)..."
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/profesores/${PROFESOR_DNI_PABLO}/asignaturas?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

ASIGNATURA_VERIF_DEW="DEW"
echo "Consultando alumnos de la asignatura $ASIGNATURA_VERIF_DEW (después de poner notas)..."
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/asignaturas/${ASIGNATURA_VERIF_DEW}/alumnos?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

ASIGNATURA_VERIF_PRG="PRG"
echo "Consultando alumnos de la asignatura $ASIGNATURA_VERIF_PRG (después de poner notas)..."
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/asignaturas/${ASIGNATURA_VERIF_PRG}/alumnos?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

ASIGNATURA_VERIF_PPE="PPE"
echo "Consultando alumnos de la asignatura $ASIGNATURA_VERIF_PPE (después de poner notas)..."
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/asignaturas/${ASIGNATURA_VERIF_PPE}/alumnos?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""

# Verificar notas de un alumno específico
DNI_VERIF_NOTAS="33445566X" # John Wick
echo "Consultando asignaturas (y notas) del alumno $DNI_VERIF_NOTAS:"
curl -s -X GET -H "accept: application/json" \
  "${BASE_URL}/alumnos/${DNI_VERIF_NOTAS}/asignaturas?key=$ADMIN_API_KEY" \
  -b $ADMIN_COOKIE_FILE | jq
echo ""


echo "Poblamiento, inscripciones y asignación de notas completados."
