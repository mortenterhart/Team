cat(rep("\n",64))

#SCHEMA
setwd("/Users/Theresa/Documents/Development/softwareengineering/eva/Team01_Travelling_Salesman/data")

getwd()

#SCHEMA
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


#SCHEMA
pdf("plots/boxplot_s_1_2_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25.pdf",height = 10,width = 10,paper = "A4r")

#SCHEMA
boxplot(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25,ylab = "distance",names=c("Szenario 1","Szenario 2","Szenario 3","Szenario 4","Szenario 5","Szenario 6","Szenario 7","Szenario 8","Szenario 9","Szenario 10","Szenario 11","Szenario 12","Szenario 13","Szenario 14","Szenario 15","Szenario 16","Szenario 17","Szenario 18","Szenario 19","Szenario 20","Szenario 21","Szenario 22","Szenario 23","Szenario 24","Szenario 25"),main = "Genetic Algorithms - Travelling Salesman Problem")

dev.off()