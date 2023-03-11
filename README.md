# Pizza Menu Service
## Project Overview
Our goal is to develop a RESTful service for online pizza ordering. Users can choose from various types of pizzas and toppings, place an order, and pay for it online. Administrators can add new pizzas and toppings, view and process orders.
## Functional Requirements
### User Stories for Anonymous Users
As an anonymous user, I want to **view** the menu (list of pizzas) with images and descriptions.<br/>
As an anonymous user, I want to **search** the menu for pizzas by name.<br/>
As an anonymous user, I want to **view** a list of pizza places with addresses.<br/>
As an anonymous user, I want to **search** for a pizza place by address.<br/>
As an anonymous user, I want to view the **assortment** of pizzas at a specific pizza place.
### User Stories for Administrators
As an administrator, I want to **manage** the menu of pizzas (CRUD operations) with images and descriptions.<br/>
As an administrator, I want to **manage** the list of pizza places with addresses.<br/>
As an administrator, I want to **manage** the assortment of pizzas at a specific pizza place.<br/>
All actions should be performed via a **REST API**.

## Containers Diagram
![Containers Diagram](https://github.com/krajniy/PizzaService/blob/master/img/Containers%20Diagram.png)
### Description
The containers diagram shows the high-level components of the system and their interactions. 
The system consists of two main containers:<br/>

**Application API** - The entry point for all requests to the system. 
It handles authentication, routing, business logic and interacts with the database.<br/>
**Database** - The persistence layer that stores all data related to pizzas and pizza places.


## Components Diagram
![Components Diagram](https://github.com/krajniy/PizzaService/blob/master/img/Components%20Diagram.png)
### Description

The components diagram shows the internal components of the Application API. 
Here, the External Browser component represents the REST API that receives and responds to requests. 
This is the outside component. </br>
**PizzaController**: A controller component responsible for handling HTTP requests related to pizzas, 
such as getting the menu, searching for a pizza, and managing pizzas (CRUD operations) for the administrator.</br>
**PizzeriaController**: A controller component responsible for handling HTTP requests related to pizzerias, 
such as getting a list of pizzerias, searching for a pizzeria by address, and managing pizzerias (CRUD operations) for the administrator.</br>
**AnonymousController**: A controller component responsible for handling HTTP requests that do not require 
authentication, such as getting the menu, searching for a pizza, getting a list of pizzerias, 
searching for a pizzeria by address, and getting pizza assortment for a specific pizzeria.</br>
**PizzaService**: A service component responsible for business logic related to pizzas, 
such as retrieving pizzas from the PizzaRepository and manipulating pizzas based on the request 
received from the PizzaController or AdministratorController.</br>
**PizzeriaService**: A service component responsible for business logic related to pizzerias, 
such as retrieving pizzerias from the PizzeriaRepository and manipulating pizzerias based 
on the request received from the PizzeriaController or AdministratorController.</br>
**PizzaRepository**: A repository component responsible for managing the storage and retrieval 
of pizza-related data, such as pizza details and pizza assortment in specific pizzerias. 
It communicates with the PizzaService to perform CRUD operations on pizzas.</br>
**PizzeriaRepository**: A repository component responsible for managing the storage and retrieval 
of pizzeria-related data, such as pizzeria details and pizza assortment available in specific pizzerias. 
It communicates with the PizzeriaService to perform CRUD operations on pizzerias.</br>

These components are organized such that the PizzaController and AnonymousController component depends on the PizzaService 
and PizzeriaService components, which in turn depend on the PizzaRepository and PizzeriaRepository components. </br>
This layered architecture allows for separation of concerns and easier maintenance and testing of the application.

## Entity Relations Diagram
![ER Diagram](https://github.com/krajniy/PizzaService/blob/master/img/ER%20Diagram.png)
### Description
The diagram shows three entities: **Pizza**, **Pizzeria**, and **Pizzeria_Pizza**. </br>
The **Pizza** entity has attributes: id, name, description, price, and image_url. 
The id attribute serves as the primary key of the entity.</br>
The **Pizzeria** entity has attributes: id, name, and address. 
The id attribute serves as the primary key of the entity.</br>
The **Pizzeria_Pizza** entity has attributes: pizzeria_id and pizza_id, which serve as foreign keys 
that establish a many-to-many relationship between Pizza and Pizzeria. 
This entity is used as a junction table to connect the two entities, with pizzeria_id referencing 
the Pizzeria entity and pizza_id referencing the Pizza entity.</br>

The design appears to be in third normal form (3NF), as there are no repeating groups 
or transitive dependencies in the entities. Each entity contains only atomic values, 
and relationships between entities are based on their primary and foreign keys.