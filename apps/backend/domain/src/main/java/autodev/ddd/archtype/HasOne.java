package autodev.ddd.archtype;

public interface HasOne<E extends Entity<?, ?>> {
    E get();
}
