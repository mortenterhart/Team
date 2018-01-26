cat(rep("\n",64))

setwd("[DATADIR]")
getwd()

#s01 <- as.numeric(read.csv("daten/example_data_scenario_01.csv",header=FALSE))
[SCENARIODESCRIPTION]

pdf("plots/[FILENAME]",height = 10,width = 10,paper = "A4r")

[SCENARIOHISTOGRAM]

dev.off()