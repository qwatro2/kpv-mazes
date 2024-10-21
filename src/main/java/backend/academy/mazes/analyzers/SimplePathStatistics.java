package backend.academy.mazes.analyzers;

public class SimplePathStatistics implements PathStatistics {
    private int numberOfGoodPassages;
    private int numberOfBadPassages;

    public SimplePathStatistics() {
        this(0, 0);
    }

    private SimplePathStatistics(int numberOfGoodPassages, int numberOfBadPassages) {
        this.numberOfGoodPassages = numberOfGoodPassages;
        this.numberOfBadPassages = numberOfBadPassages;
    }

    public void incrementGoodPassages() {
        ++this.numberOfGoodPassages;
    }

    public void incrementBadPassages() {
        ++this.numberOfBadPassages;
    }

    @Override
    public String constructString() {
        return "Number of good passages: " + this.numberOfGoodPassages + "\n"
            + "Number of bad passages: " + this.numberOfBadPassages;
    }
}
