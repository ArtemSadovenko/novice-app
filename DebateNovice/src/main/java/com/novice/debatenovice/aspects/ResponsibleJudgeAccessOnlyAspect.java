package com.novice.debatenovice.aspects;

import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.room.RoomDetailsDTO;
import com.novice.debatenovice.enums.USER_ROLE;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ResponsibleJudgeAccessOnlyAspect extends SecurityHandlerAspect {
    private final RoomService roomService;

    @Before("@annotation(annotation)")
    public void checkMembership(JoinPoint joinPoint, com.novice.debatenovice.annotations.ResponsibleJudgeAccessOnly annotation) {
        Long roomDetailsIdParam = extractPathVariable(annotation.roomIdParam());
        UserDTO currentUser = getCurrentUser();
        RoomDetailsDTO roomDetails = roomService.getRoomDetails(roomDetailsIdParam);
        if (
                !(currentUser.getRoles().stream().anyMatch(role -> role == USER_ROLE.ADMIN) ||
                        roomDetails.getJudges().stream()
                                .map(e -> e.getId())
                                .anyMatch(id -> id == currentUser.getId())
                )) {
            throw new CustomException("only the judge responsible of the room can give an assessment", HttpStatus.FORBIDDEN);
        }
    }
}
