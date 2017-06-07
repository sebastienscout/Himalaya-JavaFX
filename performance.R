library(ggplot2)

main = function(){
  blue <- read.table("fitness_Bleu.csv", header = TRUE, sep = ",")
  red <- read.table("fitness_Rouge.csv", header = TRUE, sep = ",")
  green <- read.table("fitness_Vert.csv", header = TRUE, sep = ",")
  
  ggplot(blue, aes(x=generation, y=avg)) +  facet_wrap(~turn) +
    geom_line( colour = "blue") +
    geom_line(data=red, aes(x=generation, y = avg, colour="red")) +
    geom_line(data=green, aes(x=generation, y = avg, colour="green")) +
    theme(legend.position="none")
}

blanc <- read.table("fitness_Rouge.csv", header = TRUE, sep = ",")
ggplot(blanc, aes(x=generation, y=avg)) + facet_wrap(~turn)+ geom_line( colour = "black")# + ylim(-15, 40)
