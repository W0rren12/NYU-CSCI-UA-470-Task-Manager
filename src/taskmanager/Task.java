package taskmanager;

public class Task {
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private String status;
    private String tag;

    public Task(String title, String description, String dueDate, String priority, String status, String tag) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getTag() {
        return tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Due Date: " + (dueDate.isEmpty() ? "" : dueDate) + "\n" +
                "Priority: " + (priority.isEmpty() ? "" : priority) + "\n" +
                "Status: " + (status.isEmpty() ? "" : status) + "\n" +
                "Tag: " + (tag.isEmpty() ? "" : tag);
    }
}
