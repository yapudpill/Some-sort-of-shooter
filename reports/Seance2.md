## 06/02: Deuxième réunion

### Travail fait:
- Mohamed BEN EL MOSTAPHA :
    - Structuration du modèle, propositions de deux versions d'un diagramme UML.
    - Implémentation d'un système de mouvement uniforme en utilisant un vecteur directeur et un coefficient.
    - Implémentation d'une classe qui gère le système de coordonnées.
    - Proposition d'une méthode pour gérer les projectiles et les armes (utilisation du pattern Factory).
- Clément BLAVY:
    - Création d'un format de stockage de carte (+ documentation).
    - Chargement des cartes à partir de fichiers.
    - Création de Tiles par défaut.
    - Refactoring léger
- Anthony FERNANDES:
    - Menu d'accueil affiché
    - Controleur principal
- Yacob ZITOUNI:
    - Boucle de jeu basique utilisant les timers Swing.
    - Début d'une structure pour afficher une partie en cours.
    - Proposition de structure de paquets.

### Points discutés
- Revue de la structure de modèle actuel
- Clarification du controleur
- Clarification de la nécessité de FactoryPatterns

### Objectifs
1. Modélisation complète d'un joueur
    - Déplacement, HP
2. Modélisation des armes/projectiles
    - Déplacement des projectiles, affecter les HP d'autres entités, et cooldown avant de pouvoir tirer à nouveau
3. Contrôleur pour bouger le joueur
    - Pour l'instant, clavier pour déplacement, clique souris pour tirer (dans la direction de déplacement seulement)
4. Affichage des cartes avec des tuiles de types différents
    - -> Pas forcément implémenter des tuiles et des textures (mais faire en sorte le permettre dans le futur), des couleurs différentes pour l'instant suffisent
5. Affichage d'un joueur
6. Implémentation d'un système de collisions
