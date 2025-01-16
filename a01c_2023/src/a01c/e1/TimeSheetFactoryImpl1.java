package a01c.e1;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl1 implements TimeSheetFactory {

    public abstract class TimeSheetImpl implements TimeSheet {

        private final List<Pair<String, String>> data;

        public TimeSheetImpl(List<Pair<String, String>> data) {
            this.data = data;
        }

        @Override
        public Set<String> activities() {
            return data.stream().map(Pair::get1).collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return data.stream().map(Pair::get2).collect(Collectors.toSet());
        }

        @Override
        public int getSingleData(String activity, String day) {
            return (int)data.stream().filter(p -> p.get1().equals(activity) && p.get2().equals(day)).count();
        }

        public int sumPerActivity(String act) {
            return days().stream().map(day -> getSingleData(act, day)).mapToInt(Integer::intValue).sum();
        }

        public int sumPerDay(String day) {
            return activities().stream().map(act -> getSingleData(act, day)).mapToInt(Integer::intValue).sum();
        }

        public abstract boolean isValid();
    }

    @Override
    public TimeSheet ofRawData(List<Pair<String, String>> data) {
        return new TimeSheetImpl(List.copyOf(data)) {
            @Override
            public boolean isValid() {
                return true;
            }   
        };
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities) {
        return new TimeSheetImpl(List.copyOf(data)) {
            @Override
            public boolean isValid() {
                return boundsOnActivities.entrySet().stream().allMatch(e -> sumPerActivity(e.getKey()) <= e.getValue());
            }   
        };
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays) {
        return new TimeSheetImpl(List.copyOf(data)) {
            @Override
            public boolean isValid() {
                return boundsOnDays.entrySet().stream().allMatch(e -> sumPerDay(e.getKey()) <= e.getValue());
            }    
        };
    }

    @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities,
            Map<String, Integer> boundsOnDays) {
        return new TimeSheetImpl(data) {
            @Override
            public boolean isValid() {
                return boundsOnActivities.entrySet().stream().allMatch(e -> sumPerActivity(e.getKey()) <= e.getValue()) &&
                    boundsOnDays.entrySet().stream().allMatch(e -> sumPerDay(e.getKey()) <= e.getValue());
            } 
        };
    }

}
