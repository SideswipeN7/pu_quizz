package whynot.com.Interface;

import java.util.List;
import java.util.function.Function;

import whynot.com.dto.DtoCategory;

public interface OnRecivedData {
    public void listner(Function<Void, List<DtoCategory>> function);
}
