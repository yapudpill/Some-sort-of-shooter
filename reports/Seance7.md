# Feuille de Sprint #7

## Réalisations de la Semaine

### Mohamed :
- Implémentation du pathfinding en utilisant l'algorithme A*.
    - Création d'AStarGrid, AStarNode et AStarPathfinder.
    - Adaptation du gameModel et du walking enemy au nouveau pathfinder.
    - Modification du layer des effets pour une démonstration visuelle du pathfinding.
    - Adaptation du spawner des ennemis et du joueur pour la démo.
    - Personnalisation de l'algorithme pour répondre aux besoins du jeu (exclusion de certains cas supplémentaires dans le parcours des voisins).

### Yacob :
- Ajout d'une arme 'couteau' (branch feat/issue-26/knife-weapon)
  - Création de l'arme "couteau" permettant d'infliger des dégâts pendant un court instant dans une petite zone (taille de la zone à calibrer)
  - Les ennemis utilisent cette arme par défaut (on ne se fait plus instantanément tuer maintenant)

### Anthony :

### Clément :

## Tâches Reportées :
- Affichage d'un HUD pour indiquer les HP et l'arme possédée.

## Objectifs pour la Semaine Prochaine :

1. Ajout de 3 nouveaux ennemis, résolution du bug de la mise à jour du pathfinding : Mohamed.
2. Intégration d'un système de score débloquant de nouvelles cartes en fonction du highscore : Anthony.
3. 4. Affichage d'un HUD pour indiquer les HP et l'arme possédée.
4. Ajout de 2 nouvelles armes : Yacob.
5. Écran de Game Over et version basique des paramètres : Clément.

### Tâches Non Prioritaires :
6. Intégration du mode "Bullet Rain" : Yacob.
