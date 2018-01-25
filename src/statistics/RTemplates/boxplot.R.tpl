cat(rep("\n",64))

#SCHEMA
#setwd("Z:/_dms/lehre/dhbw/mosbach/lehrveranstaltungen/evolutionaere_algorithmen/2018/03_implementierung/03_training/01_aufgaben/statistical_analysis")
setwd("[DATADIR]")

getwd()

#SCHEMA
#s01 <- as.numeric(read.csv("daten/example_data_scenario_01.csv",header=FALSE))
#s02 <- as.numeric(read.csv("daten/example_data_scenario_02.csv",header=FALSE))
[SCENARIODESCRIPTION]

#SCHEMA
#pdf("plots/example_boxplot_scenario_01_02.pdf",height = 10,width = 10,paper = "A4r")
pdf("[DATADIR]/plots/[FILENAME]",height = 10,width = 10,paper = "A4r")

#SCHEMA
#boxplot(s01,s02,ylab = "distance",names=c("Scenario 01","Scenario 02"),main = "Genetic Algorithms - TSP280")
boxplot([SCENARIOSSHORT],ylab = "distance",names=c([NAMES]),main = "Genetic Algorithms - TSP280")

dev.off()