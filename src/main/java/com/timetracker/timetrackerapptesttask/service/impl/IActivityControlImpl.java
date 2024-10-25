package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.entity.Activity;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.*;
import java.util.*;

/**
 * Сервис для управления активностями пользователей.
 */
@Service
public class IActivityControlImpl implements IActivityControl {

    @Autowired
    private ActivityRepository repository; //интерфейс репозитория для взаимодействия с таблице activity из бд

    /**
     * Создает новую активность и сохраняет ее в репозитории.
     *
     * @param name    Название активности.
     * @param project Проект, к которому относится активность.
     * @return Идентификатор сохраненной активности.
     */
    @Override
    public Long createActivity(String name, Project project) {

        Activity activity = Activity.builder()
                .name(name)
                .project(project)
                .build();



        return repository.save(activity).getId();
    }
    /**
     * Начинает выполнение активности и сохраняет время начала в репозитории.
     *
     * @param activityDto DTO с информацией об активности (идентификатор и идентификатор проекта).
     * @return Время начала активности в виде строки.
     * @throws ActivityNotFoundException если активность не найдена.
     */
    @Override
    public String startActivity(ActivityDto activityDto) {

        Activity Activity = repository.findById(activityDto.getId(), activityDto.getProjectId()).orElseThrow(ActivityNotFoundException::new);

        Activity.setStarted(Timestamp.valueOf(LocalDateTime.now()));

        repository.save(Activity);

        return Activity.getStarted().toString();
    }

    /**
     * Завершает активность и возвращает строковое представление времени завершения.
     * @param activityDto DTO активности
     * @return Время завершения в виде строки
     */
    @Override
    public String finishActivity(ActivityDto activityDto) {
        // Находим активность по ID и ID проекта
        Activity activity = repository.findById(activityDto.getId(), activityDto.getProjectId()).orElseThrow(ActivityNotFoundException::new);
// Устанавливаем время завершения на текущее время
        activity.setFinished(Timestamp.valueOf(LocalDateTime.now()));
        // Сохраняем изменения в репозитории
        repository.save(activity);
        // Возвращаем строковое представление времени завершения
        return activity.getFinished().toString();
    }

    /**
     * Удаляет активность по её идентификатору.
     * @param id Идентификатор активности
     */
    @Override
    public void deleteActivity(Long id) {
// Находим активность по ID или выбрасываем исключение, если активность не найдена
        repository.delete(repository.findById(id).orElseThrow(ActivityNotFoundException::new));

    }

    /**
     * Возвращает список DTO активностей для указанного проекта.
     * @param projectId Идентификатор проекта
     * @return Список DTO активностей
     */
    @Override
    public List<ActivityDto> getAllByProject(Long projectId) {
        // Получаем список активностей для указанного проекта или выбрасываем исключение, если проект не найден
        List<Activity> activityList = repository.getAllByProject(projectId).orElseThrow(ProjectNotFoundException::new);
        // Создаём список DTO для активностей
        var activityDtoList = new ArrayList<ActivityDto>();
        // Преобразуем каждую активность в DTO и добавляем в список
        activityList.forEach(item->{
            activityDtoList.add(ActivityDto.builder()
                            .id(item.getId())
                            .name(item.getName())
                            .startAt(item.getStarted()!=null ? item.getStarted().toString() : null)
                            .finishedAt(item.getFinished()!=null ? item.getFinished().toString() : null)
                            .projectId(item.getProject().getId())
                    .build());
        });

        return activityDtoList;
    }
}
