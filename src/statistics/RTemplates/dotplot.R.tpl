cat(rep("\n",64))

setwd("Z:/_dms/lehre/dhbw/mosbach/lehrveranstaltungen/evolutionaere_algorithmen/2018/03_implementierung/03_training/01_aufgaben/statistical_analysis")
getwd()

s01 <- as.numeric(read.csv("daten/example_data_scenario_01.csv",header=FALSE))
s02 <- as.numeric(read.csv("daten/example_data_scenario_02.csv",header=FALSE))

pdf("plots/example_scenario01_dotplot.pdf",height = 10,width = 10,paper = "A4r")

plot(s01,col="black",ylab = "distance",xlab = "iterations",cex = 0.1,main = "Genetic Algorithms - TSP280 - Scenario 01")

dev.off()