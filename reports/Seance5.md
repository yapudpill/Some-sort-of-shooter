# Sprint Sheet #5

# Réalisations de la semaine

## Yacob:
- "Gestion des weapon drops"
    - Création d'une classe `WeaponEntity`, représentant une arme ramassable
    - Ajout de détection des collisions entre les `CombatUnits` et les `WeaponEntity` pour récupérer le Weapon qu'elle représente
    - Ajout d'une classe `EntitySpawner` à utiliser pour créer des entités, + une implémentation `RandomWeaponSpawner` qui place des `WeaponEntity` aléatoirement sur des emplacements **accessibles** de la carte
    - Affichage rudimentaire des WeaponEntity (un carré gris)

## Mohamed:
- "Gestion des entités"
    - Création de la classe `EnemySpawner` pour placer des `WalkingEnemy` de manière aléatoire sur des emplacements **accessibles** de la carte.
    - Gestion de la mort d'une créature, c'est-à-dire l'exclure de la logique et de l'affichage.
    - Amélioration du système de tir (utilisation du vecteur entre le joueur et le curseur).
    - Mise en œuvre du pattern composite pour afficher toutes les entités tout en respectant l'encapsulation.
    - Amélioration de la qualité graphique du jeu : ajout d'une barre de santé, utilisation d'images pour représenter les ennemis et les tuiles.

## Objectifs pour la semaine prochaine :
    - Gestion du GameOver.
    - Ajout de plusieurs cartes.
    - Intégration d'un menu.
    - Intégration d'un menu pour la selection de map.
    - Intégration d'autres types d'ennemis et d'armes.