# Sprint Sheet #6

# Réalisations de la semaine

## Mohamed:
- "Gestion du Game Over (retour au menu d'accueil)"
  - Adaptation du contrôleur principal pour permettre la gestion du game over.
  - Réinitialisation du contrôleur à chaque game over pour éviter les problèmes d'inputs résiduels.

- "Disparition des projectiles lorsqu'ils touchent un mur"
  - Centralisation du modèle pour faciliter les opérations sur les entités et les mises à jour.
  - Adaptation du contrôleur selon la nouvelle architecture.
  - Implémentation de la disparition des projectiles lorsqu'ils touchent un mur.
  - Ajout du champ "isMoving" pour détecter quand les projectiles s'arrêtent.

- Autres tâches réalisées :
  - Résolution des bugs liés au spawning des ennemis (spawns proches d'un mur).
  - Implémentation du despawn des armes.
  - Amélioration du timer personnalisé créé par Yacob.

## Yacob:
- Corriger les collisions pour le ramassage d'armes

## Tâches reportées:
- Affichage d'un HUD pour indiquer les HP et l'arme possédée


## Objectifs pour la semaine prochaine :
1. Intégration d'autres types d'ennemis et d'armes plus variés : Mohamed
2. Intégration d'un menu pour la sélection de la carte : Anthony
3. Implémentation d'un système de score : Clément
4. Ajout d'une arme "couteau" permettant d'infliger des dégâts pendant un court instant dans une petite zone: Yacob
5. Affichage d'un HUD pour indiquer les HP et l'arme possédée: Yacob

#### Tâches non prioritaires:
6. Intégration du mode "Bullet Rain" : Yacob