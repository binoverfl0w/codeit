package hexarch;

import java.util.List;

public class AbstractPageImpl<T> extends AbstractPage<T> {
    public AbstractPageImpl(long totalItems, int totalPages, int currentPage, List<T> items) {
        super(totalItems, totalPages, currentPage, items);
    }
}
