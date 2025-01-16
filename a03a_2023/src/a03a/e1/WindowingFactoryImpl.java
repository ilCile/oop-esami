package a03a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WindowingFactoryImpl implements WindowingFactory {

    private abstract class WindowingImpl<X, Y> implements Windowing<X, Y> {
        public final List<X> lastValues = new ArrayList<>();
        @Override
        public Optional<Y> process(X x) {
            lastValues.addLast(x);
            if (condition()) {
                return Optional.empty();
            } else {
                Y output = calculateOutput();
                if (optionalOperation()) {
                    lastValues.removeFirst();
                }
                return Optional.of(output);
            }
        }

        public abstract Y calculateOutput();
        public abstract boolean condition();
        public abstract boolean optionalOperation();
    }

    @Override
    public <X> Windowing<X, X> trivial() {
        return new WindowingImpl<X,X>() {
            @Override
            public X calculateOutput() {
                return lastValues.getLast();
            }
            @Override
            public boolean condition() {
                return false;
            }
            @Override
            public boolean optionalOperation() {
                return true;
            }
        };
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        return new WindowingImpl<X,Pair<X,X>>() {
            @Override
            public Pair<X, X> calculateOutput() {
                return new Pair<>(lastValues.getFirst(), lastValues.getLast());
            }
            @Override
            public boolean condition() {
                return lastValues.size() < 2;
            }
            @Override
            public boolean optionalOperation() {
                return true;
            }
        };
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        return new WindowingImpl<Integer,Integer>() {
            @Override
            public Integer calculateOutput() {
                return lastValues.stream().mapToInt(Integer::intValue).sum();
            }
            @Override
            public boolean condition() {
                return lastValues.size() < 4;
            }
            @Override
            public boolean optionalOperation() {
                return true;
            } 
        };
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return new WindowingImpl<X,List<X>>() {
            @Override
            public List<X> calculateOutput() {
                return new ArrayList<>(lastValues);
            }

            @Override
            public boolean condition() {
                return lastValues.size() < n;
            }

            @Override
            public boolean optionalOperation() {
                return true;
            }
        };
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return new WindowingImpl<Integer,List<Integer>>() {

            @Override
            public List<Integer> calculateOutput() {
                return new ArrayList<>(lastValues);
            }

            @Override
            public boolean condition() {
                return lastValues.stream().mapToInt(Integer::intValue).sum() < n;
            }

            @Override
            public boolean optionalOperation() {
                return lastValues.stream().mapToInt(Integer::intValue).sum() >= n;
            }
            
        };
    }

}
