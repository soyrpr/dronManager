# Gestor de Drones

Este proyecto es un **Gestor de Drones** desarrollado en **Spring Boot**. El objetivo principal de la aplicación es gestionar matrices de vuelo para drones, permitiendo la creación, edición y manejo de los mismos, todo ello con verificación de colisiones al ejecutar órdenes de vuelo.

## Descripción

La API permite realizar las siguientes acciones:

- **Crear y editar matrices de vuelo**: Configura las dimensiones de la matriz y los drones asociados.
- **Gestión de drones**: CRUD (Crear, Leer, Actualizar, Eliminar) de drones.
- **Verificación de colisiones**: Al ejecutar órdenes de vuelo, se validan posibles colisiones entre drones.
- **Consultas avanzadas**: Listado de drones y detalles sobre sus posiciones en la matriz de vuelo.

## Tecnologías utilizadas

Este proyecto utiliza las siguientes tecnologías:

- **Java 21**: Lenguaje de programación principal.
- **Spring Boot**: Framework para la creación de aplicaciones Java.
- **Spring Data JPA**: Para la gestión de bases de datos con JPA.
- **Maven**: Herramienta de construcción para gestión de dependencias.
- **JUnit y Mockito**: Para pruebas unitarias.
- **SonarQube**: Para análisis estático del código.
- **LogBack**: Para logging.
- **Lombok**: Para reducción de código repetitivo.
- **OpenAPI 3**: Para la documentación de la API REST.
