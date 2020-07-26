package com.puffinpowered.batch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JobLaunchController {

    JobLauncher jobLauncher;

    Job importMovie;
    Job importClothes;
    Job importParticipant;

    public JobLaunchController(JobLauncher jobLauncher, Job importMovie, Job importClothes) {
        this.jobLauncher = jobLauncher;
        this.importMovie = importMovie;
        this.importClothes = importClothes;
    }


    @RequestMapping("/import/movies")
    public String importMovies() throws Exception {
      try {
          JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                  .toJobParameters();
          jobLauncher.run(importMovie, jobParameters);
      } catch (Exception e){
          log.info(e.getMessage());
      }
      return "Done importing movies";
    }

    @RequestMapping("/import/clothes")
    public String importClothes() throws Exception {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(importClothes, jobParameters);
        } catch (Exception e){
            log.info(e.getMessage());
        }
        return "Done importing clothes";
    }
}
