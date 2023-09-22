

Projet de Session – Rapport Final
Alarmes Android Next Generation (AAND)

Michael Munger - MUNM07039500
Departement d'Informatique et Mathématique
Université du Québec à Chicoutimi
8INF257 : Informatique mobile
Chargé de Cours: Eric Dallaire
1 Mai 2022

Rapport Final: 
Alarmes Android Next Generation (AAND)

Distribution du projet
	Nous sommes d’accord à ce que le projet soit partagé aux futurs étudiants du cours. (2)

Information technique (version, IDE, environnements testés, etc)
    • Tester sur Pixel 2 XL, API 27, Android 8.1
    • Min API : 22
    • Compile API : 32

Description du Projet (300-500 caractères)
	Le but du projet était d'amélioré les alarmes par défaut d'Android. Bien que les fonctionalités d'alarmes, d'horloge, de chronomètre et de minuteurs ont été implémenté, certaines petites parties telle que la persistence et des fonctionnalité mineures n'ont pas vu le jour du à des contraintes de temps et de main d'oeuvre. Cependant, le résultat final reflète la vision initiale et des structures sont présentes pour faciliter une mise à jour avec les parties manquantes.

Facettes Principales
    • Viewpager comme interface principale avec des tab dynamiques (MainActivity.java:94)
    • Panneau Latéral comme menu principal (MainActivity.java:121)
    • Couleurs Dynamiques et posibilité de changer les couleurs (MainActivity.java:153)
    • Fragments Dynamiques (MainActivity.java:219)
    • Alarmes crée dynamiquement (AlarmClass.java:56)
    • Alarmes avec options cachées (indv_alarms_layout.xml)
    • Nouvelles alarmes crée dynamiquement (AlarmClass.java:439)
    • Deux utilisations différentes de TextClock (clock_layout.xml)
    • Implementation d'un chrono avec un Handler et un Runnable(StopWatchClass.java:79)
    • Implementation d'un minuteur avec un Handler et un Runnable (TimerClass.java:100)
    • Minuteur avec chrono caché (timer_layout.xml)
    • Un très grand nombre de vecteurs comme images qui changent de couleur avec le thème
