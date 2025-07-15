
# System Bank Web

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![GitHub stars](https://img.shields.io/github/stars/Juanlegall/System-Bank-web?style=social)
![GitHub forks](https://img.shields.io/github/forks/Juanlegall/System-Bank-web?style=social)

Este repositorio contiene el código fuente de un **Sistema de Home Banking** desarrollado como proyecto académico utilizando **Java**, **JSP**, **Servlets**, y **JDBC**. Permite la gestión completa de clientes, cuentas y movimientos bancarios, con funcionalidades diferenciadas por rol.

---

## Tabla de Contenidos

* [Características Principales](#características-principales)
* [Tecnologías Utilizadas](#tecnologías-utilizadas)
* [Requisitos del Sistema](#requisitos-del-sistema)
* [Configuración](#configuración)
    * [Clonado del Repositorio](#clonado-del-repositorio)
    * [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
    * [Configuración de la Aplicación](#configuración-de-la-aplicación)
* [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
* [Uso](#uso)
* [Contribución](#contribución)
* [Licencia](#licencia)
* [Autor](#autor)

---

## Características Principales

* **Inicio de sesión seguro con control de acceso basado en roles (RBAC):**
    * Autenticación de usuarios y control de permisos según el tipo de cuenta (cliente o administrador).
* **Gestión de Clientes y Administradores:**
    * Alta, baja, modificación y listado (ABML).
* **Gestión de Cuentas Bancarias:**
    * Alta de cuentas, eliminación y actualizaciones.
    * Vinculación de cuentas con clientes.
* **Gestión de Transferencias:**
    * Realización de transferencias entre cuentas con validaciones.
    * Visualización de movimientos por cuenta.
* **Gestión de Préstamos (opcional):**
    * Alta y seguimiento de préstamos personales.
* **Entornos diferenciados por rol:**
    * Menú exclusivo para administradores.
    * Funcionalidades personalizadas para clientes.
* **Validaciones dinámicas en frontend y backend:**
    * Validación de datos, campos obligatorios, restricciones por tipo de usuario, etc.
* **Paginación de movimientos:**
    * Visualización paginada de movimientos por cuenta (10 por página).

---

## Tecnologías Utilizadas

* **Lenguaje de Programación:** Java (JDK 17)
* **Backend:**
    * Servlets
    * JDBC
    * Modelo en capas
* **Frontend:**
    * JSP
    * HTML
    * CSS
    * Bootstrap
* **Base de Datos:** MySQL
* **Servidor Web:** Apache Tomcat 10
* **IDE:** Eclipse
* **Control de versiones:** Git / GitHub

---

## Requisitos del Sistema

* Java JDK 17 o superior  
* Apache Tomcat 10  
* MySQL Server  
* Eclipse IDE (o compatible)  
* Navegador Web moderno

---

## Configuración

### Clonado del Repositorio

```bash
git clone https://github.com/Juanlegall/System-Bank-web.git

