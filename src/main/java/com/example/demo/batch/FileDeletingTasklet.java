package com.example.demo.batch;

import java.io.File;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileDeletingTasklet implements Tasklet {
	@Value("${inputFile}")
	private Resource resource;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File file = resource.getFile();
		boolean deleted = file.delete();
		if (!deleted) {
			throw new UnexpectedJobExecutionException("Impossible de supprimer le fichier " + file.getPath());
		}
		System.out.println("Fichier supprimé : " + file.getPath());
		return RepeatStatus.FINISHED;
	}
}