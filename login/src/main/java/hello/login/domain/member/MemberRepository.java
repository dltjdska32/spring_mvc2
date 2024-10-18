package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: memeber={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(long id) {
        return store.get(id);
    }

    public List<Member> findAll(){
        // 맵의 밸류를 리스트로 변환
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId) {
       /* List<Member> all = findAll();
        for (Member member : all) {
            if(member.getLoginId().equals(loginId)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();*/

        return findAll().stream()
                .filter(member ->
                    member.getLoginId().equals(loginId)
                ).findFirst();
    }


    public void clearStore() {
        store.clear();
    }
}
