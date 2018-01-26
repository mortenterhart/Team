cat(rep("\n",64))

setwd("[DATADIR]")
getwd()

[SCENARIODESCRIPTION]

# median
c(median(s01),median(s02))
[MEDIAN]

# mean
#c(round(mean(s01),digits = 2),round(mean(s02),digits = 2))
[MEAN]

# sd
#c(round(sd(s01),digits = 2),round(sd(s02),digits = 2))
[SD]

# quantile
#c(quantile(s01,0.25),quantile(s02,0.25))

#quantile(s01,probs = c(0.25,0.75))
#quantile(s02,probs = c(0.25,0.75))

#quantile(s01,probs = c(0.25,0.50,0.75))
#quantile(s02,probs = c(0.25,0.50,0.75))

# range
#c(max(s01) − min(s01))
#c(max(s02) - min(s02))
[RANGE]

# interquartile range
#c(quantile(s01,0.75) - quantile(s01,0.25),quantile(s02,0.75) - quantile(s02,0.25))
[INTERQUARTILERANGE]
