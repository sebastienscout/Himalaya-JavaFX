//Initialisation de la fitness a 0
fitness = 0;

//Realisation des 6 premieres actions
Pour (i allant de 0 a 6) {
	executerAction(i);
}

//Ajout du malus
Si(Le joueur a complete une commande){
	Si(Le joueur a pris moins de 2 recompenses){
		fitness = fitness - malus;
	}
}

//Realisation des dernieres actions
Pour (i allant de 6 a nombreActionMax) {
	executerAction(i);
}

//Calcul de la fitness
fitness += (nombre de ressource obtenues);
fitness += (nombre de stupa posees * coefficient religieux);
fitness += (nombre de delegations posees * coefficient politique);
fitness += (nombre de troc effectues * coefficient econnomique);
