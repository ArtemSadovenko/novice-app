package com.novice.debatenovice.aspects;

import com.novice.debatenovice.enums.USER_ROLE;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.TournamentRepository;
import com.novice.debatenovice.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminOnlyAccessAspect extends SecurityHandlerAspect {

    private final TournamentService tournamentService;
    private final TournamentRepository tournamentRepository;

    @Before("@annotation(annotation)")
    public void checkMembership(JoinPoint joinPoint, com.novice.debatenovice.annotations.AdminOnlyAccess annotation) {
        if (getCurrentUser().getRoles().stream().anyMatch(e -> e.equals(USER_ROLE.ADMIN))) {
            System.out.println("Admin Only Access");
        } else {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }


    }


}
