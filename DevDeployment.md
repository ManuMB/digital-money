# Guía para Desplegar el Entorno de Desarrollo

Esta guía te ayudará a desplegar tu entorno de desarrollo utilizando Docker y Maven.

## Usando Docker y Maven

Sigue estos pasos para levantar el entorno de desarrollo:

1. **Levantar el servicio de la base de datos del docker-compose**

   Utiliza el siguiente comando para levantar el servicio de la base de datos:

    ```bash
    docker-compose up -d mysql
    ```


2. **Generar el ejecutable de la aplicación**

   Para generar el ejecutable de la aplicación, ejecuta el siguiente comando:

    ```bash
    mvn package
    ```

3. **Correr el servicio de la aplicación del docker-compose**

   Finalmente, utiliza el siguiente comando para correr el servicio de la aplicación:

    ```bash
    docker-compose up -d dm-backend
    ```

## Alternativa: Usando el IDE

Como alternativa, puedes repetir el paso 1 y correr la aplicación directamente desde tu IDE.