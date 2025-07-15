package autodev.ddd.archtype;

public interface Entity<Identity, Description> {
    Identity getIdentity();

    Description getDescription();
}
