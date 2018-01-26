cat(rep("\n",64))

setwd("Z:/_dms/lehre/dhbw/mosbach/lehrveranstaltungen/evolutionaere_algorithmen/2018/03_implementierung/03_training/01_aufgaben/statistical_analysis")
getwd()

s01 <- as.numeric(read.csv("daten/example_data_scenario_01.csv",header=FALSE))

# most frequent fitness 
sort(table(s01),decreasing=TRUE)[1]
