package importer.batch;

import importer.model.Status;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StatusProcessor implements ItemProcessor<Status, Status> {

	@Override
	public Status process(Status status) throws Exception {
		String partNumber = status.getPartNumber();
		if (!partNumber.isEmpty()) {
			String newStatus = status.getStatusUpdate();
			if (newStatus.equalsIgnoreCase("Inactivate")) {
				newStatus = "Inactive";
			} else if (newStatus.equalsIgnoreCase("Discontinue")) {
				newStatus = "Discontinued";
			}
			if (status.getStatus().equalsIgnoreCase("Inactive") && newStatus.equalsIgnoreCase("Discontinued")) {
				status.setStatusSql(String.format("update attributes set value = '%s' where attribute_id = 'TFM_ProductStatus' and option_id = ' [%s] ');", newStatus, partNumber));
			} else if (status.getStatus().equalsIgnoreCase("Active") && (newStatus.equalsIgnoreCase("Inactive") || newStatus.equalsIgnoreCase("Discontinued"))) {
				status.setStatusSql(String.format("insert into attributes values ('TFM_ProductStatus',' [%s] ','null-option','%s');", partNumber, newStatus));
				status.setStatusTranslationOptionSql(String.format("insert into attributes values ('TFM_ProductStatusTranslationOption',' [%s] ','null-option','none');", partNumber));
				status.setStatusReasonSql(String.format("insert into attributes values ('TFM_ProductStatusReason',' [%s] ','null-option','Product Management Decision');", partNumber));
			}
		}
		return status;
	}
}