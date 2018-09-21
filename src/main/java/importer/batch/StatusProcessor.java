package importer.batch;

import importer.model.Status;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StatusProcessor implements ItemProcessor<Status, Status> {

	@Override
	public Status process(Status status) throws Exception {
		return status;
	}
	
}