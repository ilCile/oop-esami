package a02b.e1;

import java.util.List;

public class RulesEngineFactoryImpl implements RulesEngineFactory {

    @Override
    public <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyRule'");
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'singleRuleEngine'");
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> baseRule, Pair<T, List<T>> cascadeRule) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cascadingRulesEngine'");
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conflictingRulesEngine'");
    }
}
