package a01b.e1;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeSheetFactoryImpl implements TimeSheetFactory{

    private record TimeData(List<String> activities, List<String> days, BiFunction<String, String, Integer> getHours) implements TimeSheet {

        @Override
        public int getSingleData(String activity, String day) {
            if (activities.contains(activity) && days.contains(day)) {
                return getHours.apply(activity, day);
            }
            return 0;
        }

        @Override
        public Map<String, Integer> sumsPerActivity() {
           return activities.stream().map(act -> 
           new Pair<>(act, days.stream().map(day -> getHours.apply(act, day)).mapToInt(Integer::intValue).sum()))
           .collect(Collectors.toMap(pair -> pair.get1(), pair -> pair.get2()));
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            return days.stream().map(day -> 
            new Pair<>(day, activities.stream().map(act -> getHours.apply(act, day)).mapToInt(Integer::intValue).sum()))
            .collect(Collectors.toMap(Pair::get1, Pair::get2));
        }

    }

    @Override
    public TimeSheet flat(int numActivities, int numNames, int hours) {
        return new TimeData(Stream.iterate(1, i -> i + 1).map(i -> "act" + i).limit(numActivities).toList(),
        Stream.iterate(1, i -> i + 1).map(i -> "day" + i).limit(numNames).toList(), (a, d) -> hours);
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeData(activities, days, (a, d) -> data.get(activities.indexOf(a)).get(days.indexOf(d)));
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        List<String> activities = Stream.iterate(1, i -> i + 1).map(i -> "act" + i).limit(numActivities).toList();
        List<String> days = Stream.iterate(1, i -> i + 1).map(i -> "day" + i).limit(numDays).toList();
        return new TimeData(activities, days, 
        (a, d) -> (int)data.stream()
        .filter(pair -> pair.get1().equals(activities.indexOf(a)) && pair.get2().equals(days.indexOf(d))).count());
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return new TimeData(activities, days, (a ,d) -> data.getOrDefault(new Pair<>(a, d), 0));
    }

   

}
