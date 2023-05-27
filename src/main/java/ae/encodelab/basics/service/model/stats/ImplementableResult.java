package ae.encodelab.basics.service.model.stats;

import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

public class ImplementableResult<T> {
    private T value;

    public ImplementableResult(Supplier<T> supplier) {
        try {
            this.value = supplier.get();
        } catch (NotImplementedException e){
            this.value = null;
        }
    }

    public static<T> ImplementableResult<T> of(Supplier<T> supplier){
        return new ImplementableResult<>(supplier);
    }

    public T get() {
        return value;
    }

    public T getOrElse(T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
