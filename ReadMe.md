#Readme Adjuvo KB41: Github & SDK 
##Introductie
Deze repository is een van de resultaat van de minor KB-41 (VT1 2016) van de de Haagse Hogeschool (HHS). Het project is een samenwerking tussen Adjuvo en de HHS. Het onderzoek bestaat uit meerdere delen, dit is het eerste resultaat van een reeks van onderzoeken. Het doel van deze ‘Readme’ is het uitleggen de code waardoor het voor andere projectgroepen het gemakkelijker wordt om te begrijpen en toe te passen.


Aanpassingen door andere groepen dient ook uitgelegd te worden in de Readme, dit zodat het ook toegankelijk en begrijpbaar was voor aankomende projectgroepen.
##Instructies
De SDK inclusief de Netbeans build-files zijn beschikbaar via de GitHub repository genaamd “adjuvo-project”. Als deze gekloond is naar een computer, kan deze meteen worden geopend in Netbeans door naar de repository te navigeren en de netbeans-project map te openen. Alle dependency-paden naar de benodigde .dll bestanden worden ook automatisch geïmporteerd vanuit de Netbeans build, waardoor de SDK meteen gebruikt kan worden.
##Locatie in mappenstructuur
De SDK bestaat uit een aantal mappen waarin verschillende onderdelen van de software zijn opgeslagen. Deze mappen zijn als volgt:

###“build”, “dist” & “nbproject”
Deze mappen worden automatisch door Netbeans aangemaakt en bevatten informatie over de bouw van het project en de aangegeven dependencies. De “dist” map bevat een jar-executable van het java bestand met de actieve “main” methode.

###“bin”
In deze map bevinden zich de dll-bestanden die de software nodig heeft om met de Emotiv Headset te kunnen communiceren. Als in de software een bepaalde methode wordt aangeroepen die informatie probeert af te lezen van de headset, wordt dit via deze dll-bestanden gedaan. De huidige Netbeans build is al ingesteld om deze bestanden automatisch aan de library toe te voegen, maar mocht dit toch opnieuw moeten gebeuren, kan dit handmatig gedaan worden door te rechtsklikken op “Libaries” en vervolgens naar de map met de dll-bestanden te navigeren en deze toe te voegen.

###“doc”
Hierin staan verschillende gebruiksaanwijzingen voor het opzetten en gebruiker van een IDE project dat gebruik maakt van de Emotiv SDK. Deze stappen zijn reeds ondernomen voor het opgezette Netbeans project maar deze kunnen nog geraadpleegd worden mocht er iets fout gaan.

###“etc”
Binnen deze map bevindt zich een bestand die nodig is voor het correct aansluiten van de Emotiv headset. Deze zou niet aangepast moeten worden.

###“examples”
Deze map bevat een aantal runnable voorbeelden van SDK functionaliteiten in verschillende programmeertalen. Deze kunnen als goede leidraad dienen voor het begrijpen van de mogelijkheden van de SDK.

###“include”
Dit is de belangrijkste map van de SDK. Hierin staan de kern Java en C# bestanden die de software gebruikt om te communiceren met de Emotiv headset. Deze kunnen in beperkte mate aangepast worden, omdat de meeste functionaliteit van de SDK verborgen zit in de .dll bestanden. De bestanden in deze map bieden voornamelijk een manier om toegang te krijgen tot deze functies.

###“lib”
Hier bevinden zich enkele bestanden die nodig zijn voor het ontwikkelen van software die aansluit op de SDK, waaronder de Java Native Access library waarmee de functies in de .dll files kunnen worden aangeroepen.

###“tools”
Deze map bevat twee applicaties, XavierComposer en XavierEmoKey. XavierComposer kan gebruikt worden om headset-statussen, mentale commando’s of gezichtsuitdrukkingen te emuleren

##Notes
* Tijdens het opzetten van de SDK voor gebruik kwamen we enkele problemen naar boven om het werkend te krijgen dit was gerelateerd aan de DLLs van de SDK. Het betreft de volgende DDLs; vul in.
Dit is opgelost door de volgende handelingen te doen;

##Gerelateerde links
+ [Adjuvo](http://www.myadjuvo.com/)
+ [Emotiv Community](https://github.com/Emotiv/community-sdk)
+ [Emotiv Advanced](https://github.com/Emotiv/community-sdk)
+ [Emotiv.com](https://Emotiv.com)
