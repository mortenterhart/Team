# Evolutionäre Algorithmen (EVA) - Travelling Salesman (TSP)

## Aufgabenstellung
**Aufgabe team05**: **BruteForce, Integration, QA**

Anforderungen an den BruteForce-Algorithmus:
- 4 Mrd. unterschiedliche Permutationen werden generiert
  (fortlaufend oder zufällig).
- Einstellungen für Evaluierung: **all**, **top25percent**,
  **last25percent**, **middle50percent**
  
## Features
**Integration**
* Einlesen der Szenarien und ihrer Voreinstellungen aus XML-Datei
  unter `configuration/scenarios.xml`
* Kapselung des genetischen Algorithmus und des Bruteforce in eigenen
  Ausführungsklassen
* Debug-Modus für zusätzliche Ausgabe von Fitnessminima, Crossover- und
  Mutationsereignissen
* Ausgabe einer Log-Datei `log/TSPCalculation.log` mit Ergebnissen aller
  ausgeführten Szenarien und des BruteForce-Algorithmus
* Berechnung des besten Fitness-Wertes aller Szenarien
* Speicherung aller Daten in einer HSQL-Datenbank

**BruteForce**
* Zufällige Generierung von `n` (`n <= Integer.MAX_VALUE`) unterschiedlichen
  Individuen (Touren)
* Segmentierung der Liste und Ausgabe der jeweils besten Fitness in einem
  Segment (all, top 25, middle 50, last 25)
* Ausgabe der besten Tour im jeweiligen Segment

## Wichtige Hinweise
* Bearbeitung der Aufgaben lokal auf den Rechnern und Nutzung der Templates.
* Verwendung geeigneter englischer Begriffe für Namen und Bezeichnungen.
* Implementierung einer einwandfrei lauffähigen Applikation in Java 8.
* Implementierung von Evolution: Gen, Chromosom/Individuum, Population,Selektion, Rekombination (Crossover), Mutation.
* Test der Implementierung mit JUnit und Gewährleistung der Funktionsweise.
* Vorrangige Nutzung von Standardfunktionalitäten in Java ggü. eigener Implementierung.
* Applikation terminiert: Maximale Anzahl der Iterationen erreicht oder keine Verbesserung innerhalb von x Iterationen.
* Erstellung einer vollständigen und verschlüsselten 7-Zip-Datei unter Beachtung des Prozedere für die Abgabe von Prüfungsleistungen und der Namenskonvention.
* Zeitansatz: 10 Stunden, Abgabetermin: Sonntag, 11.02.2018
* Bewertung: Testat
