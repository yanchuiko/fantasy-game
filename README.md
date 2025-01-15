# Fantasy Game: Treasure Dungeon

## Project Overview

Welcome to **Treasure Dungeon** – a fantasy adventure game where players explore levels, interact with rooms, collect items, and overcome obstacles. Guided by Gandalf, players navigate through dungeons in search of the **One Ring of Power**. Along the way, they must solve challenges and manage resources to succeed.

This project emphasizes clean and structured code, following **SOLID** principles wherever possible. It incorporates various design patterns such as **Command Pattern**, **Factory Pattern**, and **Singleton Pattern** to demonstrate object-oriented design practices aimed at ensuring scalability, maintainability, and flexibility.

## Features

- **Interactive Gameplay**: Players can perform various actions, including moving, looking around, picking up, using, eating, dropping, drinking, and opening items.
- **Resource Management**: Players must manage power points, inventory, and tools wisely to progress.
- **Room Exploration**: Rooms are filled with items (e.g., food, potions, spells, tools, boxes) and obstacles (e.g., traps, scientists).
- **Clear Game Flow**: Input processing, state management, and rendering ensure a seamless and engaging game experience.

## Architecture

The game follows the **Model-View-Presenter (MVP)** architecture pattern, ensuring a clean separation of concerns:

- **Model**: Responsible for the core game logic, data, and services.
- **View**: Displays the game state and handles user input.
- **Presenter**: Acts as a mediator between the Model and View, processing user commands and updating the View.

## Technologies Used

This project is built using **Java** and follows **Object-Oriented Programming (OOP)** principles like inheritance, polymorphism, and abstraction. The design of the game utilizes several key **Design Patterns** to enhance scalability and maintainability:

- **Command Pattern**: Used to encapsulate the player’s actions (e.g., move, pick up items, use tools) as objects, allowing the system to decouple the invocations of operations from the object that performs them. This makes it easy to manage user commands and extend new behaviors.
  
- **Factory Pattern**: Employed in the generation of content, such as rooms, items, and obstacles. The `ContentFactory` is responsible for creating these game components at runtime, ensuring each game session feels unique and engaging with randomized elements.
  
- **Singleton Pattern**: Applied to ensure that key components (such as the game engine or settings manager) have only one instance throughout the game. This ensures that these components are accessible globally and are not duplicated, promoting consistent behavior and resource management.

## Setup and Installation

### Clone the Repository

To get started, first clone the repository to your local machine:

```bash
git clone https://github.com/yanchuiko/FantasyGame.git
cd FantasyGame
