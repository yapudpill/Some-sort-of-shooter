# Sprint Sheet #5

# Réalisations de la semaine

## Yacob:
- "Gestion des weapon drops"
    - Création d'une classe `WeaponEntity`, représentant une arme ramassable
    - Ajout de détection des collisions entre les `CombatUnits` et les `WeaponEntity` pour récupérer le Weapon qu'elle représente
    - Ajout d'une classe `EntitySpawner` à utiliser pour créer des entités, + une implémentation `RandomWeaponSpawner` qui place des `WeaponEntity` aléatoirement sur des emplacements **accessibles** de la carte
    - Affichage rudimentaire des WeaponEntity (un carré gris)


## Pour la semaine prochaine :
