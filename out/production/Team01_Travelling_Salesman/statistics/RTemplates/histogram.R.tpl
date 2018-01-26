cat(rep("\n",64))

setwd("Z:/_dms/lehre/dhbw/mosbach/lehrveranstaltungen/evolutionaere_algorithmen/2018/03_implementierung/03_training/01_aufgaben/statistical_analysis")
getwd()

s01 <- as.numeric(read.csv("daten/example_data_scenario_01.csv",header=FALSE))

pdf("plots/example_scenario01_histogram.pdf",height = 10,width = 10,paper = "A4r")

hist(s01,xlim=c(2500,5000),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280")

dev.off()