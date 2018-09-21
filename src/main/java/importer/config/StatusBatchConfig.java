package importer.config;

import importer.model.Status;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class StatusBatchConfig {

    @Bean
    public Job statusJob(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Status> statusItemReader,
                   ItemProcessor<Status, Status> itemProcessor,
                   ItemWriter<Status> itemWriter
    ) {

        Step statusStep = stepBuilderFactory.get("ETL-status-file-load")
                .<Status, Status>chunk(100)
                .reader(statusItemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("ETL-status-load")
                .incrementer(new RunIdIncrementer())
                .start(statusStep)
                .build();
    }

    @Bean
    public FlatFileItemReader<Status> statusItemReader(@Value("${status-input}") Resource resource) {

        FlatFileItemReader<Status> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(3);
        flatFileItemReader.setLineMapper(statusLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Status> statusLineMapper() {

        DefaultLineMapper<Status> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"category", "part number", "name", "comments", "status", "status update"});


        BeanWrapperFieldSetMapper<Status> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Status.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}
