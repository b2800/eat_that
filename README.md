# eat_that
- Originally made for a school project in Ruby on Rails

Cette application vous permet de vous aider a choisir quoi cuisiner si, comme moi, vous passez plus de temps a chercher quoi faire plutot que de réellement préparer de quoi manger. 

## Résumé

Ce repository contient la partie mobile de l'application ainsi que le web service en ruby on rails. L'application fonctionne de cette façon : Elle maintient une liste d'ingrédients, qui appartiennent a une ou plusieurs catégories. Lorsque l'utilisateur demande un plat, elle choisit entre 2 et 4 catégories complétementaires, puis, choisis aléatoirement des ingrédients qui correspondent a ces catégories. Pour chaque ingrédient, un mode de préparation est choisi. Les résultats ressemblent alors a ça : 
Recette | 
Catégorie | Ingrédient choisi | Type de préparation
Base | Pommes de terres | Sautées
Viande | Steack | Grillé
Légume | Carrotes | En salade 

Si l'utilisateur a besoin de plus d'informations sur comment péparer son ingrédient, il peut accéder aux détails du type de préparation pour y consulter les étapes a suivre. 


### Web service
+ Permet : 
  - La création des ingrédients
  - La création des types de préparation et les étapes associées
+ Ne permet pas :
  - La création des catgégories
  - L'association des catégories aux aliments
  - Ne fourni pas l'API JSON 
  - N'est pas hébergée sur Heroku ( Incompatibilité de SQLite3 ) 

### Application (TBD)
+ Permet : 
  - La création des ingrédients et l'association avec des catégories pré enregistrées
  - La génération des recettes aléatoires
  - Un fonctionnement 100% offline avec persistence des informations renseignées 
    + Utilise un SQLiteOpenHelper pour gérer une base de donnée locale sur le device
  - Utilise plusieurs recycler view comme demandé
+ Ne permet pas: 
  - La récupération des ingrédients depuis le web service
  - La création des types de préparation
  - La modification des éléments déja rensegnés.
