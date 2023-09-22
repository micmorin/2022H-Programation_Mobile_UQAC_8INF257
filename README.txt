

Projet de Session � Rapport Final
Alarmes Android Next Generation (AAND)

Michael Munger - MUNM07039500
Departement d'Informatique et Math�matique
Universit� du Qu�bec � Chicoutimi
8INF257�: Informatique mobile
Charg� de Cours: Eric Dallaire
1 Mai 2022

Rapport Final: 
Alarmes Android Next Generation (AAND)

Distribution du projet
	Nous sommes d�accord � ce que le projet soit partag� aux futurs �tudiants du cours. (2)

Information technique (version, IDE, environnements test�s, etc)
    � Tester sur Pixel 2 XL, API 27, Android 8.1
    � Min API�: 22
    � Compile API�: 32

Description du Projet (300-500 caract�res)
	Le but du projet �tait d'am�lior� les alarmes par d�faut d'Android. Bien que les fonctionalit�s d'alarmes, d'horloge, de chronom�tre et de minuteurs ont �t� impl�ment�, certaines petites parties telle que la persistence et des fonctionnalit� mineures n'ont pas vu le jour du � des contraintes de temps et de main d'oeuvre. Cependant, le r�sultat final refl�te la vision initiale et des structures sont pr�sentes pour faciliter une mise � jour avec les parties manquantes.

Facettes Principales
    � Viewpager comme interface principale avec des tab dynamiques (MainActivity.java:94)
    � Panneau Lat�ral comme menu principal (MainActivity.java:121)
    � Couleurs Dynamiques et posibilit� de changer les couleurs (MainActivity.java:153)
    � Fragments Dynamiques (MainActivity.java:219)
    � Alarmes cr�e dynamiquement (AlarmClass.java:56)
    � Alarmes avec options cach�es (indv_alarms_layout.xml)
    � Nouvelles alarmes cr�e dynamiquement (AlarmClass.java:439)
    � Deux utilisations diff�rentes de TextClock (clock_layout.xml)
    � Implementation d'un chrono avec un Handler et un Runnable(StopWatchClass.java:79)
    � Implementation d'un minuteur avec un Handler et un Runnable (TimerClass.java:100)
    � Minuteur avec chrono cach� (timer_layout.xml)
    � Un tr�s grand nombre de vecteurs comme images qui changent de couleur avec le th�me
