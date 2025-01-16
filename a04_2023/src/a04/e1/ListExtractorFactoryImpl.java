package a04.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListExtractorFactoryImpl implements ListExtractorFactory {

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return new ListExtractor<X, Optional<X>>() {
            @Override
            public Optional<X> extract(List<X> list) {
                if (list.size() != 0) {
                    return Optional.of(list.getFirst());
                }
                return Optional.empty();
            }
            
        };
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
        return new ListExtractor<X,List<Y>>() {
            @Override
            public List<Y> extract(List<X> list) {
                List<Y> cache = new ArrayList<>();
                for (var elem : list) {
                    if (stopCondition.test(elem)) {
                        return new ArrayList<>(cache);
                    }
                    cache.add(mapper.apply(elem));
                }
                return new ArrayList<>(cache);
            }
            
        };
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
        return new ListExtractor<X,List<List<X>>>() {
            @Override
            public List<List<X>> extract(List<X> list) {
                boolean finded = false;
                List<List<X>> output = new ArrayList<>();
                for (var elem : list) {
                    if (startCondition.test(elem) || finded) {
                        finded = true;
                        if (!output.isEmpty()) {
                            output.add(new ArrayList<>(output.getLast()));
                        } else {
                            output.add(new ArrayList<>());
                        }  
                        output.getLast().add(elem);
                    }
                }
                return new ArrayList<>(output);
            }
            
        };
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x) {
        return new ListExtractor<X,Integer>() {
            @Override
            public Integer extract(List<X> list) {
                int counter = 0;
                boolean sem = true;
                for (var elem : list) {
                    if (elem == x) {
                        sem = true;
                    } else {
                        if (counter != 0) {
                            return counter;
                        }
                        sem = false;
                    }
                    if (sem) {
                        counter++;
                    }
                }
                return counter;
            }
            
        };
    }

}
