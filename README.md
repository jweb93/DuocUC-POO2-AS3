![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
# 🧠 Actividad Sumativa 3 – Desarrollo Orientado a Objetos II

## 💻 Proyecto: SpeedFast
## 👤 Autor del proyecto
- **Nombre completo:** Javier Rojas
- **Sección:** PRY2203-001A
- **Carrera:** Analista Programador Computacional
- **Sede:** Online

---

## 📘 Descripción general del sistema
Este proyecto da respuesta a la Actividad Sumativa 3 de la asignatura
*Desarrollo Orientado a Objetos II*

Se desarrolló un programa que permite la gestión persistente (CRUD)
de Pedidos, Repartidores y Entregas mediante interfaz visual. En esta entrega
no se solicitó ni abordaron validaciones sofisticadas como:
- Consistencia de los estados de los pedidos respaldada por entregas 
(pedido Entregado pero sin registro de Entrega)
- Bloquear edición directa de la fecha y hora de entrega pudiéndose seleccionar una fecha futura
- Eliminación en cascada de Pedidos o Repartidores para eliminar sus entregas asociadas.
- Edición sin reales cambios en Pedido, Repartidor o Entrega.

El sistema creado se organiza en paquetes, aplica principios de
composición (clase Dirección), encapsulamiento (atributos privados y
métodos getter/setter) y mantiene documentación de código usando Javadocs.


---

## 🧱 Estructura general del proyecto

```plaintext
docs
└── index.html
speedfast_db.sql
src
├── main
│   ├── java
│   │   ├── Main.java
│   │   ├── controlador
│   │   │   ├── ControladorEntregas.java
│   │   │   ├── ControladorPedidos.java
│   │   │   └── ControladorRepartidores.java
│   │   ├── dao
│   │   │   ├── EntregaDAO.java
│   │   │   ├── PedidoDAO.java
│   │   │   ├── RepartidorDAO.java
│   │   │   └── impl
│   │   │       ├── EntregaDAOImpl.java
│   │   │       ├── PedidoDAOImpl.java
│   │   │       └── RepartidorDAOImpl.java
│   │   ├── modelo
│   │   │   ├── Direccion.java
│   │   │   ├── Entrega.java
│   │   │   ├── EstadoPedido.java
│   │   │   ├── Mes.java
│   │   │   ├── Pedido.java
│   │   │   ├── Repartidor.java
│   │   │   └── TipoPedido.java
│   │   ├── util
│   │   │   └── ConexionBD.java
│   │   └── vista
│   │       ├── VentanaGestionPedidos.form
│   │       ├── VentanaGestionPedidos.java
│   │       ├── VentanaGestionRepartidores.form
│   │       ├── VentanaGestionRepartidores.java
│   │       ├── VentanaPrincipal.java
│   │       ├── VentanaGestionEntregas.form
│   │       └── VentanaGestionEntregas.java
│   └── resources
└── test
    └── java
````

---


## ⚙️ Instrucciones para clonar y ejecutar el proyecto

1. Clone el repositorio desde GitHub:

```bash
git clone https://github.com/jweb93/DuocUC-POO2-AS3.git
```
2. Ejecute el script speedfast_db.sql en una bbdd mysql

3. Abra el proyecto en IntelliJ IDEA.

4. Actualice la conexión a bbdd editando la clase ConexionBD del paquete util

5. Ejecute el archivo `Main.java` desde la ruta src/main/java.

6.  Puede revisar la documentación del código accediendo al
   archivo `docs/index.html`

---

**Repositorio GitHub:** https://github.com/jweb93/DuocUC-POO2-AS3
**Fecha de entrega:** \[05/10/2026]

---

© Duoc UC | Escuela de Informática y Telecomunicaciones 




