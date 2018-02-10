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

## Vordefinierte Szenarien
TSP mit 280 Städten, d.h. 280! mögliche Permutationen:
```text
280! = 16772277799452185316008559642481690996154162653443224216229144166533010495138176552175779628900866154937655467080893509725117402094786797430449965992591855047863335070498197537053948882096262565780315504934744482331080425680552490971597062111349307447058139219943834846427081475234675780303186241495364354473220247981334182737896146496569057168205263172422932268545207065230644665792052186286503978375114413827309599287477421264601465575912229816923404173201977337878379210932930098887603481739264000000000000000000000000000000000000000000000000000000000000000000000
```
**Szenarien**

| **Szenario** |  **Crossover**   | **Crossover-Rate** | **Mutation** | **Mutation-Rate** |   **Selection**  |
|:------------:|:----------------:|:------------------:|:------------:|:-----------------:|:----------------:|
|     01       | PartiallyMatched |        0.8         |   Exchange   |       0.005       |   RouletteWheel  |
|     02       | PartiallyMatched |        0.7         |   Exchange   |       0.005       |   RouletteWheel  |
|     03       | PartiallyMatched |        0.6         |   Exchange   |       0.005       |   RouletteWheel  |
|     04       | PartiallyMatched |        0.8         |   Exchange   |       0.0005      |   RouletteWheel  |
|     05       | PartiallyMatched |        0.7         |   Exchange   |       0.0005      |   RouletteWheel  |
|     06       | PartiallyMatched |        0.6         |   Exchange   |       0.0005      |   RouletteWheel  |
|     07       | PartiallyMatched |        0.7         | Displacement |       0.0005      |   RouletteWheel  |
|     08       | PartiallyMatched |        0.7         | Displacement |       0.0005      |     Tournament   |
|     09       | PartiallyMatched |        0.7         |   Heuristic  |       0.0005      |   RouletteWheel  |
|     10       | PartiallyMatched |        0.7         |   Insertion  |       0.0005      |   RouletteWheel  |
|     11       | PartiallyMatched |        0.7         |   Inversion  |       0.0005      |   RouletteWheel  |
|     12       |      Cycle       |        0.7         |   Exchange   |       0.005       |   RouletteWheel  |
|     13       |      Cycle       |        0.7         |   Exchange   |       0.0005      |   RouletteWheel  |
|     14       |      Cycle       |        0.7         |   Exchange   |       0.0005      |     Tournament   |
|     15       |    Heuristic     |        0.7         |   Exchange   |       0.0005      |   RouletteWheel  |
|     16       |    Heuristic     |        0.7         |   Insertion  |       0.0005      |   RouletteWheel  |
|     17       |    Heuristic     |        0.7         |   Inversion  |       0.0005      |   RouletteWheel  |
|     18       |     Ordered      |        0.7         |   Exchange   |       0.0005      |     Tournament   |
|     19       |     Ordered      |        0.7         | Displacement |       0.0005      |   RouletteWheel  |
|     20       |     Ordered      |        0.7         |   Insertion  |       0.0005      |   RouletteWheel  |
|     21       |  PositionBased   |        0.7         |   Exchange   |       0.0005      |     Tournament   |
|     22       |  PositionBased   |        0.7         |   Heuristic  |       0.0005      |   RouletteWheel  |
|     23       |  PositionBased   |        0.7         |   Inversion  |       0.0005      |     Tournament   |
|     24       | SubTourExchange  |        0.7         |   Exchange   |       0.0005      |     Tournament   |
|     25       | SubTourExchange  |        0.7         | Displacement |       0.0005      |   RouletteWheel  |
