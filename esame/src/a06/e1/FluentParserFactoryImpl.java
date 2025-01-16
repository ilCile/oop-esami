package a06.e1;

import java.util.List;
import java.util.function.UnaryOperator;

public class FluentParserFactoryImpl implements FluentParserFactory{

    @Override
    public FluentParser<Integer> naturals() {
        return new FluentParserAbstract<Integer>(){
            @Override
            public boolean isOk(Integer value) {
                int tmp = this.prev.get();
                return value.equals(++tmp);
            }
        };
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return new FluentParserAbstract<List<Integer>>() {
            @Override
            public boolean isOk(List<Integer> value) {
                if (value.size() != (this.prev.get().size() + 1)) {
                    return false;
                }
                if (this.prev.get().isEmpty() && value.getLast() == 0) {
                    return true;
                }
                for (int i = 0; i < this.prev.get().size(); i++) {
                    if (value.get(i) != this.prev.get().get(i)) {
                        return false;
                    }
                }
                return value.getLast() == (this.prev.get().getLast() + 1);
            }     
        };
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'repetitiveIncrementalNaturals'");
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'repetitiveIncrementalStrings'");
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'incrementalPairs'");
    }

}
