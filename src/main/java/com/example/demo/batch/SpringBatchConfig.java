package com.example.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Personne;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private ItemReader<Personne> itemReader;
	@Autowired
	private ItemWriter<Personne> itemWriter;
	@Autowired
	private ItemProcessor<Personne, Personne> itemProcessor;
	@Autowired
	private Tasklet tasklet;

	/*
	 * @Bean public Job job(Step step1) { return
	 * jobBuilderFactory.get("Recrutement") // le nom de notre job
	 * .start(step1).build(); }
	 */
	@Bean
	public Job job(@Qualifier("step1") Step step1, @Qualifier("deleteFile") Step deleteFile) {
		return jobBuilderFactory.get("Recrutement").start(step1).next(deleteFile).build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("première étape: chargement de fichier").<Personne, Personne>chunk(2)
				.reader(itemReader).processor(itemProcessor).writer(itemWriter).build();
	}
	
	@Bean
	public Step deleteFile() {
		return this.stepBuilderFactory.get("deuxième étape : suppression du fichier CSV").tasklet(tasklet).build();
	}
	@Bean
	public FlatFileItemReader<Personne> reader(@Value("${inputFile}") Resource resource) {
		return new FlatFileItemReaderBuilder<Personne>().name("personItemReader").resource(resource).linesToSkip(1)
				.delimited().names(new String[] { "nom", "prenom", "salaire" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Personne>() {
					{
						setTargetType(Personne.class);
					}
				}).build();
	}

}
