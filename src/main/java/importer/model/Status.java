package importer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Status {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String category;
	private String partNumber;
	private String name;
	private String comments;
	private String status;
	private String statusUpdate;

	public Status(Integer id, String category, String partNumber, String name, String comments, String status, String statusUpdate) {
//		this.id = id;
		this.category = category;
		this.partNumber = partNumber;
		this.name = name;
		this.comments = comments;
		this.status = status;
		this.statusUpdate = statusUpdate;
	}

	public Status() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(String statusUpdate) {
        this.statusUpdate = statusUpdate;
    }
}