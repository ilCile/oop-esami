package a03b.e1;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers {

    private <X> InfiniteIterator<X> generalInfiniteIterator(Stream<X> s) {
        return new InfiniteIterator<X>() {
            @Override
            public X nextElement() {
                Iterator<X> it = s.iterator();
                return it.next();
            }
        };
    }

    @Override
    public <X> InfiniteIterator<X> of(X x) {
        return () -> x;
    }

    @Override
    public <X> InfiniteIterator<X> cyclic(List<X> l) {
            return generalInfiniteIterator(Stream.generate(l::stream).flatMap(e -> e));
    }

    @Override
    public InfiniteIterator<Integer> incrementing(int start, int increment) {
        return new InfiniteIterator<Integer>() {
            private int counter = 0;
            @Override
            public Integer nextElement() {
                var output = start + increment * counter;
                counter++;
                return output;
            }
            
        };
    }

    @Override
    public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> i, InfiniteIterator<X> j) {
        return new InfiniteIterator<X>() {
            private int counter = 0;
            @Override
            public X nextElement() {
                counter++;
                if (counter % 2 != 0) {
                    return i.nextElement();
                } else {
                    return j.nextElement();
                }
            }
            
        };
    }

    @Override
    public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n) {
        return new InfiniteIterator<List<X>>() {

            @Override
            public List<X> nextElement() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'nextElement'");
            }
            
        };
    }

}
