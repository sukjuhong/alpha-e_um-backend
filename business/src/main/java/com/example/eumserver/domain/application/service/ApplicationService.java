package com.example.eumserver.domain.application.service;

import com.example.eumserver.domain.announcement.team.domain.TeamAnnouncement;
import com.example.eumserver.domain.announcement.team.repository.TeamAnnouncementCustomRepositoryImpl;
import com.example.eumserver.domain.announcement.team.repository.TeamAnnouncementRepository;
import com.example.eumserver.domain.application.dto.MyApplicationResponse;
import com.example.eumserver.domain.application.entity.ApplicationState;
import com.example.eumserver.domain.application.entity.TeamApplication;
import com.example.eumserver.domain.application.repository.ApplicationRepository;
import com.example.eumserver.domain.resume.ResumeRepository;
import com.example.eumserver.domain.resume.entity.Resume;
import com.example.eumserver.domain.user.User;
import com.example.eumserver.domain.user.UserRepository;
import com.example.eumserver.global.error.exception.CustomException;
import com.example.eumserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private ApplicationRepository applicationRepository;
    private TeamAnnouncementRepository teamAnnouncementRepository;
    private UserRepository userRepository;
    private ResumeRepository resumeRepository;
    public Page<MyApplicationResponse> getMyApplications(Long user_id, ApplicationState state) {
        Pageable paging = PageRequest.of(0, 10);
        Page<MyApplicationResponse> list = applicationRepository
                .getMyApplicationsWithPaging(user_id, state, paging);

        return list;
    }

    public void applyTeam(long userId, long announcementId, Long resumeId) {
        TeamAnnouncement announcement = teamAnnouncementRepository.findById(announcementId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_ANNOUNCEMENT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Resume resume = null;
        if(resumeId != null){
            resume = resumeRepository.findById(resumeId)
                    .orElseThrow(() -> new CustomException(ErrorCode.RESUME_NOT_FOUND));
        }

        TeamApplication application = TeamApplication.builder()
                .announcement(announcement)
                .user(user)
                .resume(resume)
                .state(ApplicationState.PENDING)
                .build();
    }
}
