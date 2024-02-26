# Sprint Sheet #5

# Réalisations de la semaine

## Yacob:
- "Gestion des weapon drops" (objectif 3)
    - Création d'une classe `WeaponEntity`, représentant une arme ramassable
    - Ajout d'une classe `EntitySpawner` à utiliser pour créer des entités, + une implémentation `RandomWeaponSpawner` qui place des `WeaponEntity` aléatoirement sur des emplacements **accessibles** de la carte
    - Affichage rudimentaire des WeaponEntity (un carré gris)
- Ramassage des armes (objectif 4, tâche originellement attribuée à Mohamed, mais je l'ai faite car proche de l'objectif 3)
    - Ajout de détection des collisions entre les `CombatUnits` et les `WeaponEntity` pour récupérer le Weapon qu'elle représentgit@gaufre.informatique.univ-paris-diderot.fr:benelmos/2023-sr1a-f.gite

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
1. Gestion du GameOver.
2. Création d'une variété de maps
3. Intégration du menu (déjà existant) avec le reste du jeu
4. Intégration d'un menu pour la selection de map.
5. Intégration d'autres types d'ennemis et d'armes.
