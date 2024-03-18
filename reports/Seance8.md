# Feuille de Sprint #8

## Réalisations de la Semaine

### Yacob : (en attente de merge)
- Ajout d'une arme 'rocket launcher' tirant des fusées déclanchant une explosion à l'impact (branche feat/rocker-launcher)
   - L'explosion peut toucher plusieurs entités situées dans un certain rayon
- Ajout d'un HUD basique en haut à gauche de l'écran indiquant l'arme actellement possédée (branche feat/game-hud)
- Les armes disparaissent une fois ramassées maintenant

### Mohamed :
- Ajout d'un ennemi `SmartEnemy`.
- Implémentation de 2 Flood Fill visant à piéger le joueur.
- Débogage du game over (réinitialisation de la carte lors du game over).
- Amélioration du spawner en créant `RandomSpawnerModel` et en faisant en sorte qu'il utilise `ModelTimer`.
- Adaptation du modèle aux modifications.
- Ajout d'un sprite pour le nouvel ennemi.


## Objectifs pour la Semaine Prochaine :

1. Rajouter une projectile à tête chercheuse : Mohamed.
2. Intégration du mode "Bullet Rain" (probablement après les projectiles à tête chercheuse): Yacob
3. Retravail du moteur de physique pour permettre des deplacements "en glissant" contre les murs: Yacob