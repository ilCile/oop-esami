package a06.e1;

import java.util.Optional;

public abstract class FluentParserAbstract<T> implements FluentParser<T>{

    Optional<T> prev = Optional.empty();

    public FluentParser<T> accept(T value) {
        if (this.prev.isEmpty() || isOk(value)) {
            this.prev = Optional.of(value);
            return this;
        }
        throw new IllegalStateException();
    }

    public abstract boolean isOk(T value);
}
