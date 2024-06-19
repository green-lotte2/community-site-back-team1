package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCsService {

    private final CsRepository csRepository;

    // 고객 문의 현황 데이터를 계산하여 반환하는 메서드
    public Map<String, Integer> getCsStatus() {
        Map<String, Integer> csStatus = new HashMap<>();

        // 현재 시간에서 2일 전의 시간을 계산
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);

        // 최근 2일간의 신규 문의 수를 조회
        int newInquiries = csRepository.countByCsRdateAfter(twoDaysAgo);

        // 답변 대기 중인 글 수를 조회
        int pendingReplies = csRepository.countByCsReply(0);

        // 조회된 값을 맵에 추가
        csStatus.put("newInquiries", newInquiries);
        csStatus.put("pendingReplies", pendingReplies);

        return csStatus;
    }
}
