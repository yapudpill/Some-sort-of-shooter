# Feuille de Sprint #11

## Réalisations de la Semaine

### Yacob :
- Ajout de classes pour gérer les animations.
    - `AnimationManager` pour gérer plusieurs animations en même temps.
    - `Animation` pour représenter une animation
    - `AnimationCache` pour charger les animations à partir de fichier XML.
- Ajout de gestion de "scénarios"
    - Un scénario est une suite d'entités à créer à un moment donné (one shot) ou bien leur fréquence d'apparition chaque seconde
    - À faire: les charger à partir d'un fichier XML et les charger depuis l'écran d'accueil

### Mohamed :
- Ajout de murs cassables
- Modification du Gestion des tuiles
- Amélioration du moteur de physique (détection de collisions entres les immobiles)
- Débogage du `ExplodingEnemy`



## Objectifs pour la Semaine Prochaine :
1. Ajout des pièges sur la carte en tant que tuiles, affectant le joueur (potentiellement même tâche que pour les murs cassables dans la map) (Yacob)
2. Chargement de scénarios à partir de fichiers XML + Intégration dans le menu d'accueil + Menu de victoire quand un scénario est terminé (Yacob)
3. Ajout de mode marathon: un scénario dynamique qui augmente en difficulté au fur et à mesure (Yacob)
4. Implémenter une IA qui utilise les ricochets (Mohamed)
5. Améliorer l'apparence du menu (Mohamed)