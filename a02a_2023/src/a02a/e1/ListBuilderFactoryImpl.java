package a02a.e1;

import java.util.ArrayList;
import java.util.List;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private class ListBuilderImpl<T> implements ListBuilder<T> {

        private final List<T> l;

        public ListBuilderImpl(List<T> list) {
            l = new ArrayList<>(list);
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            List<T> l2 = new ArrayList<>(l);
            l2.addAll(list);
            return new ListBuilderImpl<>(new ArrayList<>(l2));
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            List<T> l2 = new ArrayList<>(l);
            l2.addAll(lb.build());
            return new ListBuilderImpl<>(new ArrayList<>(l2));
        }

        @SuppressWarnings("unchecked")
        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            List<T> l2 = new ArrayList<>(l);
            l2 = (List<T>) l2.stream().mapMulti((e, out) -> {
                if (e.equals(t)) {
                    out.accept(lb.build());
                }
            }).toList();
            return new ListBuilderImpl<>(new ArrayList<>(l2));
        }

        @Override
        public ListBuilder<T> reverse() {
            List<T> l2 = new ArrayList<>(l);
            for (int i = l.size() - 1; i >= 0; i--) {
                l2.add(l.get(i));
            }
            return new ListBuilderImpl<>(l2);
        }

        @Override
        public List<T> build() {
            return new ArrayList<>(l);
        }
        
    }

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl<>(List.of());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return new ListBuilderImpl<>(List.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'join'");
    }

}
