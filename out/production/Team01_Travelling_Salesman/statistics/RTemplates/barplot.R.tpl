pdf("plots/[FILENAME]",height = 10,width = 10,paper = "A4r")

s01 <- [SCENARIODESCRIPTION]

barplot(s01,ylim=c(0,100),col="black",ylab = "solution quality (%)",xlab = "iterations",width = 0.1,main = "Genetic Algorithms - TSP280 - [NAMES]")

dev.off()