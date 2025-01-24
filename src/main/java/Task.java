public class Task {
  private String description;
  private boolean isDone;

  public Task(String description) {
    this.description = description;
    this.isDone = false;
  }

  public void mark() {
    isDone = true;
  }

  public void unmark() {
    isDone = false;
  }

  public String getStatusIcon() {
    return isDone ? "[X]" : "[ ]";
  }

  @Override
  public String toString() {
    return getStatusIcon() + " " + description;
  }
}
