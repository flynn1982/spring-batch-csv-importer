package importer.batch;

import importer.model.Status;
import importer.repository.StatusRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusDBWriter implements ItemWriter<Status> {

	@Autowired
	private StatusRepository statusRepository;

	@Override
	public void write(List<? extends Status> statuses) throws Exception {

		System.out.println("Data Saved for Statuses: " + statuses);
		statusRepository.save(statuses);
	}
}