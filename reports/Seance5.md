# Sprint Sheet #5

# Réalisations de la semaine

## Yacob:
- "Gestion des weapon drops" (objectif 3)
    - Création d'une classe `WeaponEntity`, représentant une arme ramassable
    - Ajout d'une classe `EntitySpawner` à utiliser pour créer des entités, + une implémentation `RandomWeaponSpawner` qui place des `WeaponEntity` aléatoirement sur des emplacements **accessibles** de la carte
    - Affichage rudimentaire des WeaponEntity (un carré gris)
- Ramassage des armes (objectif 4, tâche originellement attribuée à Mohamed, mais je l'ai faite car proche de l'objectif 3)
    - Ajout de détection des collisions entre les `CombatUnits` et les `WeaponEntity` pour récupérer le Weapon qu'elle représente (**comporte actuellement un bug, les collisions ne se font pas toujours**)

## Mohamed
- "Gestion des entités"
    - Création de la classe `EnemySpawner` pour placer des `WalkingEnemy` de manière aléatoire sur des emplacements **accessibles** de la carte. (objectif 1)
    - Gestion de la mort d'une créature, c'est-à-dire l'exclure de la logique et de l'affichage. (objectif 1)
    - Amélioration du système de tir (utilisation du vecteur entre le joueur et le curseur).
    - Mise en œuvre du pattern composite pour afficher toutes les entités tout en respectant l'encapsulation.
    - Amélioration de la qualité graphique du jeu : ajout d'une barre de santé, utilisation d'images pour représenter les ennemis et les tuiles. (objectif 2)

## Tâches reportées:
5. Gestion des parties (tâche à diviser)
6. création d'une variété de maps


## Objectifs pour la semaine prochaine :
1. Gestion du GameOver (retour au menu d'accueil)
2. Corriger les collisions pour le ramassage d'armes
3. Disparition des projectiles lorsqu'ils touchent un mur
4. Création d'une variété de maps
5. Intégration du menu (déjà existant) pour lancer le reste du jeu.

Non prioritaire:
6. Intégration d'autres types d'ennemis et d'armes plus variés.
7. Intégration d'un menu pour la selection de map.
8. Affichage d'un HUD pour indiquer les HP et l'arme possédée
