CREATE TABLE Pizzeria_Pizza (
                                pizzeria_id INT NOT NULL,
                                pizza_id INT NOT NULL,
                                PRIMARY KEY (pizzeria_id, pizza_id),
                                FOREIGN KEY (pizzeria_id) REFERENCES Pizzeria(id),
                                FOREIGN KEY (pizza_id) REFERENCES Pizza(id)
);
