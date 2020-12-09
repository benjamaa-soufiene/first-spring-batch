package com.example.demo.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.example.demo.model.Personne;

import lombok.var;

@Component
public class PersonneItemProcessor implements ItemProcessor<Personne, Personne> {
	@Override
	public Personne process(final Personne personne) throws Exception {
		var nom = personne.getNom().toUpperCase();
		personne.setNom(nom);
		var prenom = personne.getPrenom();
		prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase();
		personne.setPrenom(prenom);
		return personne;
	}
}