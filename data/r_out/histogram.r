cat(rep("\n",64))

setwd("/Users/Theresa/Documents/Development/softwareengineering/eva/Team01_Travelling_Salesman/data")
getwd()

s1 <- as.numeric(read.csv("data/data_scenario_1.csv",header=FALSE)) 
s2 <- as.numeric(read.csv("data/data_scenario_2.csv",header=FALSE)) 
s3 <- as.numeric(read.csv("data/data_scenario_3.csv",header=FALSE)) 
s4 <- as.numeric(read.csv("data/data_scenario_4.csv",header=FALSE)) 
s5 <- as.numeric(read.csv("data/data_scenario_5.csv",header=FALSE)) 
s6 <- as.numeric(read.csv("data/data_scenario_6.csv",header=FALSE)) 
s7 <- as.numeric(read.csv("data/data_scenario_7.csv",header=FALSE)) 
s8 <- as.numeric(read.csv("data/data_scenario_8.csv",header=FALSE)) 
s9 <- as.numeric(read.csv("data/data_scenario_9.csv",header=FALSE)) 
s10 <- as.numeric(read.csv("data/data_scenario_10.csv",header=FALSE)) 
s11 <- as.numeric(read.csv("data/data_scenario_11.csv",header=FALSE)) 
s12 <- as.numeric(read.csv("data/data_scenario_12.csv",header=FALSE)) 
s13 <- as.numeric(read.csv("data/data_scenario_13.csv",header=FALSE)) 
s14 <- as.numeric(read.csv("data/data_scenario_14.csv",header=FALSE)) 
s15 <- as.numeric(read.csv("data/data_scenario_15.csv",header=FALSE)) 
s16 <- as.numeric(read.csv("data/data_scenario_16.csv",header=FALSE)) 
s17 <- as.numeric(read.csv("data/data_scenario_17.csv",header=FALSE)) 
s18 <- as.numeric(read.csv("data/data_scenario_18.csv",header=FALSE)) 
s19 <- as.numeric(read.csv("data/data_scenario_19.csv",header=FALSE)) 
s20 <- as.numeric(read.csv("data/data_scenario_20.csv",header=FALSE)) 
s21 <- as.numeric(read.csv("data/data_scenario_21.csv",header=FALSE)) 
s22 <- as.numeric(read.csv("data/data_scenario_22.csv",header=FALSE)) 
s23 <- as.numeric(read.csv("data/data_scenario_23.csv",header=FALSE)) 
s24 <- as.numeric(read.csv("data/data_scenario_24.csv",header=FALSE)) 
s25 <- as.numeric(read.csv("data/data_scenario_25.csv",header=FALSE)) 


minimum <- min(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25)
maximum <- max(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25)


pdf("plots/histogram_s1.pdf",height = 10,width = 10,paper = "A4r")
hist(s1,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s1")
dev.off()

pdf("plots/histogram_s2.pdf",height = 10,width = 10,paper = "A4r")
hist(s2,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s2")
dev.off()

pdf("plots/histogram_s3.pdf",height = 10,width = 10,paper = "A4r")
hist(s3,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s3")
dev.off()

pdf("plots/histogram_s4.pdf",height = 10,width = 10,paper = "A4r")
hist(s4,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s4")
dev.off()

pdf("plots/histogram_s5.pdf",height = 10,width = 10,paper = "A4r")
hist(s5,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s5")
dev.off()

pdf("plots/histogram_s6.pdf",height = 10,width = 10,paper = "A4r")
hist(s6,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s6")
dev.off()

pdf("plots/histogram_s7.pdf",height = 10,width = 10,paper = "A4r")
hist(s7,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s7")
dev.off()

pdf("plots/histogram_s8.pdf",height = 10,width = 10,paper = "A4r")
hist(s8,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s8")
dev.off()

pdf("plots/histogram_s9.pdf",height = 10,width = 10,paper = "A4r")
hist(s9,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s9")
dev.off()

pdf("plots/histogram_s10.pdf",height = 10,width = 10,paper = "A4r")
hist(s10,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s10")
dev.off()

pdf("plots/histogram_s11.pdf",height = 10,width = 10,paper = "A4r")
hist(s11,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s11")
dev.off()

pdf("plots/histogram_s12.pdf",height = 10,width = 10,paper = "A4r")
hist(s12,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s12")
dev.off()

pdf("plots/histogram_s13.pdf",height = 10,width = 10,paper = "A4r")
hist(s13,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s13")
dev.off()

pdf("plots/histogram_s14.pdf",height = 10,width = 10,paper = "A4r")
hist(s14,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s14")
dev.off()

pdf("plots/histogram_s15.pdf",height = 10,width = 10,paper = "A4r")
hist(s15,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s15")
dev.off()

pdf("plots/histogram_s16.pdf",height = 10,width = 10,paper = "A4r")
hist(s16,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s16")
dev.off()

pdf("plots/histogram_s17.pdf",height = 10,width = 10,paper = "A4r")
hist(s17,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s17")
dev.off()

pdf("plots/histogram_s18.pdf",height = 10,width = 10,paper = "A4r")
hist(s18,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s18")
dev.off()

pdf("plots/histogram_s19.pdf",height = 10,width = 10,paper = "A4r")
hist(s19,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s19")
dev.off()

pdf("plots/histogram_s20.pdf",height = 10,width = 10,paper = "A4r")
hist(s20,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s20")
dev.off()

pdf("plots/histogram_s21.pdf",height = 10,width = 10,paper = "A4r")
hist(s21,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s21")
dev.off()

pdf("plots/histogram_s22.pdf",height = 10,width = 10,paper = "A4r")
hist(s22,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s22")
dev.off()

pdf("plots/histogram_s23.pdf",height = 10,width = 10,paper = "A4r")
hist(s23,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s23")
dev.off()

pdf("plots/histogram_s24.pdf",height = 10,width = 10,paper = "A4r")
hist(s24,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s24")
dev.off()

pdf("plots/histogram_s25.pdf",height = 10,width = 10,paper = "A4r")
hist(s25,xlim=c(minimum,maximum),ylim=c(0,200),xlab = "distance",breaks=100,main = "Genetic Algorithms - TSP280 - s25")
dev.off()


