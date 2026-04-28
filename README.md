# RetechIA

> MVP tecnico inspirado en el proyecto ganador de hackathon **Retech.IA**.

RetechIA es una propuesta de aplicacion para ayudar a planificar la red fisica y logica de edificios nuevos: empresas, hospitales, centros educativos o sedes con necesidades tecnicas complejas.

La idea original combina **IA, redes, servidores y arquitectura de sistemas** para analizar planos, calcular subnetting, proponer salas tecnicas, estimar equipamiento y generar una vista logica de la instalacion.

Este repositorio contiene una **base backend en Java + Spring Boot** pensada como ejemplo profesional para portfolio.

---

## Objetivo del MVP

Este codigo base se centra en tres partes demostrables:

- Calculo de subnetting a partir de una red base y departamentos.
- Generacion de una propuesta inicial de infraestructura por edificio.
- API REST preparada para integrarse con una futura app web, movil o modulo de IA.

---

## Stack

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-Tests-25A162?style=for-the-badge&logo=junit5&logoColor=white)

---

## Funcionalidades incluidas

- API para calcular subredes.
- Validacion de requisitos por departamento.
- Recomendacion basica de switches, racks y puntos de red.
- Modelo de edificios, plantas, salas tecnicas y departamentos.
- Tests unitarios de la logica de subnetting.

---

## Endpoints

### Calcular subredes

```http
POST /api/network/subnets
```

Ejemplo de peticion:

```json
{
  "baseNetwork": "192.168.10.0/24",
  "departments": [
    { "name": "Administracion", "hosts": 28 },
    { "name": "Urgencias", "hosts": 52 },
    { "name": "Servidores", "hosts": 12 }
  ]
}
```

Ejemplo de respuesta:

```json
{
  "baseNetwork": "192.168.10.0/24",
  "subnets": [
    {
      "department": "Urgencias",
      "networkAddress": "192.168.10.0",
      "cidr": 26,
      "usableHosts": 62
    }
  ]
}
```

### Generar propuesta de edificio

```http
POST /api/planning/building-proposal
```

---

## Como ejecutarlo

```bash
mvn spring-boot:run
```

Despues, la API estara disponible en:

```text
http://localhost:8080
```

---

## Por que este proyecto es interesante

RetechIA demuestra una combinacion muy valiosa para un perfil junior:

- Desarrollo backend con Java.
- Conocimientos reales de redes y sistemas.
- Pensamiento de producto.
- Capacidad para transformar una idea de hackathon en un MVP tecnico.
- Base preparada para crecer hacia IA, app movil o dashboard web.

---

## Roadmap

- Subida de planos y extraccion de metadatos.
- Integracion con un modulo de IA para recomendaciones.
- Vista grafica de la red logica.
- Estimacion de presupuesto por componentes.
- Version de mantenimiento para clientes.
- App movil para consultar incidencias y documentacion de red.

---

## Autor

**Abraham Amar Bentolila**  
Desarrollador en formacion DAM, con base en sistemas, redes e infraestructura.

- GitHub: [ItzAbra26](https://github.com/ItzAbra26)
- LinkedIn: [Abraham Amar Bentolila](https://www.linkedin.com/in/abraham-amar-bentolila-61bb75277/)
- Email: [abrahamamar26@gmail.com](mailto:abrahamamar26@gmail.com)
