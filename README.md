# Distributed Style

## Descripción
Este proyecto es una aplicación web que gestiona una tienda de ropa en línea. Está diseñada siguiendo el patrón de arquitectura MVC (Modelo-Vista-Controlador) y utiliza la base de datos MySQL para almacenar toda la información de manera persistente. La interfaz web está desarrollada con el sistema de plantillas **Mustache**, ofreciendo una experiencia de usuario sencilla e intuitiva.

## Características principales
- **Entidades principales:**
  - **Product:** Información de los productos disponibles en la tienda.
  - **Shop:** Representa cada tienda que puede contener múltiples productos.
  - **Supplier:** Proveedores asociados a las tiendas.
  - **Composition:** Relación 1:1 con los productos que detalla su composición específica.
- **Relaciones entre entidades:**
  - **Shop-Product (1:N):** Una tienda puede tener varios productos.
  - **Shop-Supplier (N:N):** Las tiendas pueden asociarse con múltiples proveedores y viceversa.
  - **Product-Composition (1:1):** Cada producto puede tener una composición detallada asociada.
- **Operaciones CRUD completas:**
  - Crear, leer, actualizar y eliminar las entidades
- **Filtrado y ordenación dinámica:**
  - Ordenar productos por nombre o precio en ambas direcciones (ascendente/descendente).

## Implementación técnica
### Backend
- **Spring Boot:** Framework principal para la implementación del backend.
- **Controladores REST:**
  - Rutas diseñadas para interactuar tanto desde la interfaz web como desde herramientas externas como Postman.
- **Gestión de dependencias:** Uso de `@Autowired` para inyección de dependencias y organización modular.

### Base de datos
- **MySQL:** Base de datos relacional utilizada para el almacenamiento persistente.
- **Tablas relacionadas:**
  - Products, Shops, Suppliers, Compositions.
- **Consultas avanzadas:**
  - Filtros dinámicos implementados con `Spring Data` para ordenación y búsqueda en base a criterios específicos.

### Frontend
- **HTML, CSS, JavaScript**
- **Mustache:** Motor de plantillas utilizado para generar dinámicamente vistas HTML.
- **Interfaz sencilla y funcional**

## Ejemplos de endpoints
| Método | Endpoint                           | Descripción                                   |
|--------|------------------------------------|-----------------------------------------------|
| GET    | /shops/{idShop}/products           | Listar productos de una tienda específica.   |
| GET    | /shops/{idShop}/products/{idProduct} | Ver detalles de un producto.                 |
| GET    | /shops/{idShop}/newProduct         | Página para añadir un nuevo producto.        |
| POST   | /shops/{idShop}/products           | Crear un nuevo producto.                     |
| GET    | /shops/{idShop}/products/{id}/modifyProduct | Página para modificar un producto existente. |
| PUT    | /shops/{idShop}/products/{id}      | Actualizar la información de un producto.    |
| DELETE | /shops/{idShop}/products/{id}      | Eliminar un producto.                        |

## Ejemplo de navegación
1. **Listado de productos por tienda:**
   - Los usuarios pueden navegar por las tiendas disponibles y consultar sus productos.
2. **Detalles del producto:**
   - Cada producto muestra información detallada, incluyendo su precio, descripción, etc.
3. **Operaciones de administración:**
   - Crear nuevos productos, actualizar información existente o eliminar elementos.
4. **Ordenación:**
   - Los productos pueden ordenarse dinámicamente por nombre o precio.

## Autores
- https://github.com/ezy0
- https://github.com/a-martinma
- https://github.com/a-sisla
