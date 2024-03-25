# Feuille de Sprint #8

## Réalisations de la Semaine

### Yacob :
- Amélioration du moteur de physique du jeu
    - On peut maintenant (et les ennemis aussi) se déplacer en longeant un mur lorsque l'on se dirige vers l'intérieur, plutôt que de s'arrêter brutalement
- Désactivé le ramassage automatique d'armes, il faut maintenant appuyer sur la touche E en se déplaçant vers une arme
- Ajout d'un viseur visuel (pour l'instant affichée pour _toutes_ les entités possédant une arme à visée)

## Mohamed:
- Ajout d'un `Shotgun`
- Adaptation du floodfill pour rester dans la ligne de vue du joueur (cela prend en compte le fait que les projectiles peuvent passer à travers l'eau)
- Ajout d'une `RubberWeapon` avec des `RubberProjectiles`, capables de rebondir
- Ajout de `HealthAidKits` pour récupérer de la vie
- Implémentation du mouvement circulaire pour les `SmartEnemies`
- Implémentation du ciblage pour les `SmartEnemies`
- Implémentation d'un `dash` pour le joueur
- Amélioration des BlockedMovementEvent proposé par Yacob


## Objectifs pour la Semaine Prochaine :
1. Ajout de pièges : une version implantée sur la map et affectant le joueur, et une version disponible pour le joueur pour piéger les ennemis : Yacob
2. Amélioration des animations du jeu (pas de détails graphiques précis, mais au moins permettre des animations utilisant des images, pour les explosions par exemple): Yacob
3. Ajout d'un événement de boss déclenché après un certain nombre de kills : Mohamed
4. Personnalisation des boss pour chaque carte : Mohamed
