package a01c.e1;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl implements TimeSheetFactory{

    private record TimeData(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> getHours,
    Map<String, Integer> bound, Function<Map<String, Integer>, Boolean> fun) implements TimeSheet{

        @Override
        public int getSingleData(String activity, String day) {
            return getHours.apply(activity, day);
        }

        @Override
        public boolean isValid() {
           return fun.apply(bound);
        }

        public static Set<String> createActivities(List<Pair<String, String>> data) {
            return data.stream().map(Pair::get1).collect(Collectors.toSet());
        }

        public static Set<String> createDays(List<Pair<String, String>> data) {
            return data.stream().map(Pair::get2).collect(Collectors.toSet());
        }
    }

    @Override
    public TimeSheet ofRawData(List<Pair<String, String>> data) {
        Set<String> activities = TimeData.createActivities(data);
        Set<String> days = TimeData.createDays(data);
        return new TimeData(Set.copyOf(activities), Set.copyOf(days), (a, d) -> 
        (int)data.stream().filter(p -> p.get1().equals(a) && p.get2().equals(d)).count(), null, null);
    }

    private int getHours(String a, String d, List<Pair<String, String>> data) {
        return (int)data.stream().filter(p -> p.get1().equals(a) && p.get2().equals(d)).count();
    }

    private int sumPerActivity(String act, Set<String> days, List<Pair<String, String>> data) {
        return days.stream().map(day -> getHours(act, day, data)).mapToInt(Integer::intValue).sum();
    }

    private int sumPerDay(String day, Set<String> activities, List<Pair<String, String>> data) {
        return activities.stream().map(act -> getHours(act, day, data)).mapToInt(Integer::intValue).sum();
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities) {
        Set<String> activities = TimeData.createActivities(data);
        Set<String> days = TimeData.createDays(data);
        return new TimeData(Set.copyOf(activities), Set.copyOf(days), (a, d) ->
        (int)data.stream().filter(p -> p.get1().equals(a) && p.get2().equals(d)).count(), boundsOnActivities,
        m -> m.entrySet().stream().allMatch(e -> sumPerActivity(e.getKey(), days, data) <= e.getValue()));
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays) {
        Set<String> activities = TimeData.createActivities(data);
        Set<String> days = TimeData.createDays(data);
        return new TimeData(activities, days, (a, d) -> 
        (int)data.stream().filter(p -> p.get1().equals(a) && p.get2().equals(d)).count(), boundsOnDays,
        m -> m.entrySet().stream().allMatch(e -> sumPerDay(e.getKey(), activities, data) <= e.getValue()));
    }

    @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities,
            Map<String, Integer> boundsOnDays) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withBounds'");
    }
}
