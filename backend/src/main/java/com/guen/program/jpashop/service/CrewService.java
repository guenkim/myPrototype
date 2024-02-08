package com.guen.program.jpashop.service;


import com.guen.program.jpashop.model.entity.Crew;
import com.guen.program.jpashop.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Crew crew) {

        validateDuplicateMember(crew); //중복 회원 검증
        crewRepository.save(crew);
        return crew.getId();
    }

    private void validateDuplicateMember(Crew crew) {
        List<Crew> findMembers = crewRepository.findByName(crew.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Crew> findMembers() {
        return crewRepository.findAll();
    }

    public Crew findOne(Long memberId) {
        return crewRepository.findOne(memberId);
    }

}
