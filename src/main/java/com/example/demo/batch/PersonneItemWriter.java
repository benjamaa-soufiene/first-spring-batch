package com.example.demo.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.dao.PersonneRepository;
import com.example.demo.model.Personne;

@Component
public class PersonneItemWriter implements ItemWriter<Personne> {
	@Autowired
	private PersonneRepository personneRepository;

	@Override
	public void write(List<? extends Personne> items) throws Exception {
		System.out.println(items);
		personneRepository.saveAll(items);
	}
}