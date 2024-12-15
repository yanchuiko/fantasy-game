# Fantasy Game: Treasure Dungeon

# Project Overview
Welcome to the Treasure Dungeon – a fantasy adventure game where players explore levels, interact with rooms, collect items, and overcome obstacles. Guided by Gandalf, the player navigates through dungeons to find the One Ring of Power, solving challenges and managing their resources to succeed.

This project implements clean and structured code, following SOLID principles wherever possible, and showcases concepts like the Command Pattern, Factory Pattern, and Object-Oriented Design for scalability and maintainability.

# Features
Interactive Gameplay: Players can move, look around, pick up, use, eat, drop, drink, and open items.

Dynamic Levels: Levels are generated using ContentFactory, with randomized items and obstacles.

Clear Game Flow: Input processing, state handling, and rendering ensure a seamless game experience.

Resource Management: Players must manage power points, inventory, and use tools wisely to succeed.

Room Exploration: Rooms contain items (food, potions, spells, tools, boxes) and obstacles (traps, scientists).

# Architecture
The project follows the Model-View-Presenter (MVP) architecture

Model: Handles the core game logic, data, and services.

View: Displays the game state and handles user input (GameView).

Presenter: Acts as a bridge between the Model and View, processing user commands and updating the View (GamePresenter).

# Technologies Used
Java: Core programming language.

OOP Concepts: Classes, inheritance, polymorphism, and abstraction.

Design Patterns: Command Pattern, Factory Pattern, Singleton.

# Setup and Installation
Clone the Repository
git clone https://github.com/your-repository/fantasy-game.git
cd fantasy-game

Run the Application
src/main/java/org/fantasygame/app/Main.java
