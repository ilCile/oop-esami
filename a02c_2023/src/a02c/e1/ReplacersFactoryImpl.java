package a02c.e1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReplacersFactoryImpl implements ReplacersFactory {

    private <T> List<T> replaceAtPosition(List<T> input, int index, List<T> target) {
        List<T> list = new ArrayList<>(input);
        list.remove(index);
        for (var elem : target) {
            list.add(index, elem);
        }
        return new ArrayList<>(list);
    }

    private <T> Replacer<T> generalReplace(List<T> target, 
        Function<List<Integer>, List<Integer>> where) {
            return new Replacer<T>() {
                @Override
                public List<List<T>> replace(List<T> input, T t) {
                    List<Integer> whereToReplace = where.apply(Stream.iterate(0, i->i+1)
                        .limit(input.size()).filter(i -> input.get(i).equals(t)).toList());
                    return whereToReplace.stream().map(index -> replaceAtPosition(input, index, target)).toList();
                }
            };
            
    }

    @Override
    public <T> Replacer<T> noReplacement() {
        return generalReplace(List.of(), l -> List.of());
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
        return generalReplace
    }

    @Override
    public <T> Replacer<T> translateLastWith(List<T> target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'translateLastWith'");
    }

    @Override
    public <T> Replacer<T> removeEach() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEach'");
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(List<T> sequence) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replaceEachFromSequence'");
    }

    

}
