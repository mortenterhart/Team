cat(rep("\n",64))

#SCHEMA
setwd("[DATADIR]")

getwd()

#SCHEMA
[SCENARIODESCRIPTION]

#SCHEMA
pdf("plots/[FILENAME]",height = 10,width = 10,paper = "A4r")

#SCHEMA
boxplot([SCENARIOSSHORT],ylab = "distance",names=c([NAMES]),main = "Genetic Algorithms - Travelling Salesman Problem")

dev.off()