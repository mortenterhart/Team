cat(rep("\n",64))

setwd("Z:/_dms/lehre/dhbw/mosbach/lehrveranstaltungen/evolutionaere_algorithmen/2018/03_implementierung/03_training/01_aufgaben/statistical_analysis")
getwd()

s01 <- as.numeric(read.csv("daten/example_data_scenario_01.csv",header=FALSE))
s02 <- as.numeric(read.csv("daten/example_data_scenario_02.csv",header=FALSE))

# median
c(median(s01),median(s02))

# mean
c(round(mean(s01),digits = 2),round(mean(s02),digits = 2))

# sd
c(round(sd(s01),digits = 2),round(sd(s02),digits = 2))

# quantile
c(quantile(s01,0.25),quantile(s02,0.25))

quantile(s01,probs = c(0.25,0.75))
quantile(s02,probs = c(0.25,0.75))

quantile(s01,probs = c(0.25,0.50,0.75))
quantile(s02,probs = c(0.25,0.50,0.75))

# range
c(max(s01) − min(s01))
c(max(s02) - min(s02))

# interquartile range
c(quantile(s01,0.75) - quantile(s01,0.25),quantile(s02,0.75) - quantile(s02,0.25))
