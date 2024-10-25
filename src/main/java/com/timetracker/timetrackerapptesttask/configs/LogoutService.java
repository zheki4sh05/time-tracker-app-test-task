package com.timetracker.timetrackerapptesttask.configs;

import com.timetracker.timetrackerapptesttask.repository.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;

/**
 * Сервис для обработки выхода пользователя из системы.
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    //репозиторий для доступа к таблице token бд
    private final TokenRepository tokenRepository;

    /**
     * Выход пользователя из системы.
     *
     * @param request        HTTP-запрос.
     * @param response       HTTP-ответ.
     * @param authentication Информация об аутентификации пользователя.
     */
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        // Получаем заголовок "Authorization" из запроса
        final String authHeader  = request.getHeader("Authorization");
        final String jwt;
// Если заголовок отсутствует или не начинается с "Bearer ", завершаем метод
        if(authHeader == null || !authHeader.startsWith("Bearer ")){

            return;
        }
        // Извлекаем токен из заголовка
        jwt = authHeader.substring(7);
        // Поиск токена в репозитории
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if(storedToken!=null){
            // Отмечаем токен как просроченный и отозванный
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }

    }
}
