package whynot.com.Interface;

import java.util.List;
import java.util.function.Function;

import whynot.com.dto.DtoCategory;

public interface OnCategoriesRecived {
    void received(List<DtoCategory> data);
    void failed();
}
