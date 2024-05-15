# Feuille de Sprint #10

## Réalisations de la Semaine

### Yacob :
- Ajout de pièges basiques en tant qu'entités, placés par le joueur, affectant les ennemis
- Ajout de traces de pas apparaissant sous les entités et qui disparaissent progressivement
- Début du travail sur un gestionnaire d'animation, qui permettra de gérer les animations des entités de manière plus propre (feat/animations)

### Mohamed :
- Ajout d'un ennemi qui s'explose au contact.
- Adaptation du flood fill pour que les ennemis aient un comportement différent.
- Les projectiles en caoutchouc disparaissent après 4 contacts.
- Amélioration de l'algo raycast

### Anthony
- Meilleure lecture des cartes custom (plus besoin de manip cheloues à base de
  compilation des resources)
- Meilleure gestion des mauvais fichiers de maps
- Ajout du point de spawn du joueur sur le fichier de map (pas merge, j'aime pas
  comment ça a rendu, je vais le refaire)

## Objectifs pour la Semaine Prochaine :
1. Ajout des pièges sur la carte en tant que tuiles, affectant le joueur (Yacob)
2. Continuer la gestion des animations (Yacob)
3. Rendre le spawn des armes/ennemis paramétrable dans la map, par exemple faire spawn pendant X secondes seulement certains ennemis/armes (Yacob)
4. Rajout des murs cassables (Mohamed).
5. Rajout des murs déplaçables (Mohamed).
6. Rajout de quelques Boss (Mohamed).


## Objectifs moins prioritaires :
4. Réfléchir à des pièges plus élaborés, voire peut-être une IA plaçant des pièges (Yacob)