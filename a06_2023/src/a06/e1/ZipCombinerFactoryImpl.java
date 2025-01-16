package a06.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory {

    private abstract class ZipCombinerAbs<X,Y,Z> implements ZipCombiner<X,Y,Z> {
        public final List<Z> out = new ArrayList<>();
        @Override
        public List<Z> zipCombine(List<X> l1, List<Y> l2) {
            Iterator<Y> it = l2.iterator();
            for (int i = 0; i < l1.size(); i++) {
                calculate(i, l1, l2, it);
            }
            return new ArrayList<>(out);
        }
        abstract void calculate(int i, List<X> l1, List<Y> l2, Iterator<Y> it);
    }

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return new ZipCombinerAbs<X,Y,Pair<X,Y>>() {
            @Override
            void calculate(int i, List<X> l1, List<Y> l2, Iterator<Y> it) {
                out.add(new Pair<X,Y>(l1.get(i), l2.get(i)));
            }  
        };
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return new ZipCombinerAbs<X,Y,Z>() {
            @Override
            void calculate(int i, List<X> l1, List<Y> l2, Iterator<Y> it) {
                if (predicate.test(l1.get(i))) {
                    out.add(mapper.apply(l2.get(i)));
                }
            }
        };
    }

    @Override
    public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
        return new ZipCombinerAbs<Integer,Y,List<Y>>() {
            @Override
            void calculate(int i, List<Integer> l1, List<Y> l2, Iterator<Y> it) {
                out.add(new ArrayList<>());
                for (int j = 0; j < l1.get(i); j++) {
                    out.getLast().add(it.next());
                }
            }
        };
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return new ZipCombinerAbs<X,Integer,Pair<X,Integer>>() {
            @Override
            void calculate(int i, List<X> l1, List<Integer> l2, Iterator<Integer> it) {
                int counter = 0;
                while (it.next() != 0) {
                    counter++;
                }
                out.add(new Pair<X,Integer>(l1.get(i), counter));
            }
        };
    }

}
