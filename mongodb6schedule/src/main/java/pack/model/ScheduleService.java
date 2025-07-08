package pack.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public List<Schedule> getAllSchedules() {
        return repository.findAll();
    }

    public Optional<Schedule> getScheduleById(String id) {
        return repository.findById(id);
    }

    public Schedule createSchedule(Schedule schedule) { // 추가
        return repository.save(schedule);
    }

    public Schedule updateSchedule(String id, Schedule schedule) { // 수정
        schedule.setId(id);
        return repository.save(schedule);
    }

    public void deleteSchedule(String id) { // 삭제
        repository.deleteById(id);
    }
}
