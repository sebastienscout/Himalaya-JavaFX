Population parents = new Population();
Population children = new Population(lambda);

//Initialisation des solutions dans la population parents
initialization(parents);

//Evaluation des solutions initialisees
evalPop(parents);

//Execution d'un nombre maxGeneration de generation
int nbGeneration = 1;
while (nbGeneration < maxGeneration) {
	selection(parents, children);
	randomVariation(children);
	evalPop(children);
	replacement(parents, children);
	nbGeneration++;
}
