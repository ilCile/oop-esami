package a01d.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimetableFactoryImpl implements TimetableFactory {


    @Override
    public Timetable empty() {
        return new TimeTableImpl();
    }

    private class TimeTableImpl implements Timetable {
        record Booking(String room, String course, Day day, int hour) {}

        private final List<Booking> list = new ArrayList<>();

        @Override
        public Set<String> rooms() {
            return list.stream().map(b -> b.room()).collect(Collectors.toSet());
        }

        @Override
        public Set<String> courses() {
            return list.stream().map(b -> b.course()).collect(Collectors.toSet());
        }

        @Override
        public List<Integer> hours() {
            return list.stream().map(b -> b.hour).toList();
        }

        @Override
        public Timetable addBooking(String room, String course, Day day, int hour, int duration) {
            list.addAll(Stream.iterate(hour, i->i+1).limit(duration).map(h -> new Booking(room, course, day, h)).collect(Collectors.toList()));
            return this;
        }

        @Override
        public Optional<Integer> findPlaceForBooking(String room, Day day, int duration) {
            return hours().stream().filter(h -> Stream.iterate(h, i->i+1).limit(duration)
            .noneMatch(hh -> list.stream().filter(b -> b.room.equals(room) && b.day.equals(day)).map(Booking::hour)
            .toList().contains(hh))).findFirst();
        }

        @Override
        public Map<Integer, String> getDayAtRoom(String room, Day day) {
            return list.stream().filter(b -> b.room.equals(room) && b.day.equals(day))
            .collect(Collectors.toMap(Booking::hour, Booking::course));
        }

        @Override
        public Optional<Pair<String, String>> getDayAndHour(Day day, int hour) {
            return list.stream().filter(b -> b.day.equals(day) && b.hour == hour).map(b -> new Pair<>(b.course, b.room)).findAny();
        }

        @Override
        public Map<Day, Map<Integer, String>> getCourseTable(String course) {
            return list.stream().filter(b -> b.course.equals(course)).collect(Collectors.groupingBy(
                Booking::day , Collectors.toMap(Booking::hour, Booking::room)));
        }
    }

}
