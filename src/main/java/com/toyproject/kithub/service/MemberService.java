package com.toyproject.kithub.service;

import com.toyproject.kithub.domain.Member;
import com.toyproject.kithub.exception.ExistMemberException;
import com.toyproject.kithub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor// final이 붙은 필드만 갖고 생성자를 만들어 준다.
@Transactional
public class MemberService {

    //1. 필드주입
    // 테스트시에는 주입 받는 객체를 변경해서 사용하거나 해야하는데
    // autowired는 불가능 하다.

    //@Autowired
    //private MemberRepository memberRepository;

    //2. setter 주입
    // 테스트시에 mock객체를 테스트에 맞게 만들어서  주입해 줄 수 있다.
    // setter를 거쳐서 주입된다.

    //@Autowired
    //public void setMemberRepository(MemberRepository memberRepository){
    //    this.memberRepository = memberRepository;
    //}


    //3.생성자 주입 (롬복이 생성자를 만들어 준다.)
    //생성 시점에 객체가 주입되기 떄문에 혹시나 수정되거나 하는 것을 막을 수 있고
    //생성시점에 파라미터가 없으면 컴파일 에러가 뜨기 때문에 좀더 안전하다.
    //생성자가 하나만 있을 경우 스프링이 알아서 오토와이어 해준다.
    //변경을 막기위해 final로 선언하는게 좋다 .

    private final MemberRepository memberRepository;

    //회원 가입
    public Long join(Member member){
        validateDuplicateMember(member);
        //여러개 was가 존재한다면
        //같은 이름의 멤버가 해당 메서드에 동시에 접근할 수도 있다
        //멀티쓰레드를 고려해서 데이터베이스에 해당 유니크 제약조건을 걸어주는 것이 좋다.
        memberRepository.save(member);
        return member.getId();
    }
    @Transactional(readOnly = true)
    private void validateDuplicateMember(Member member) {
        List<Member> byName = memberRepository.findByName(member.getName());
        if (!byName.isEmpty()){
            throw new ExistMemberException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    //readOnly = true 조회하는 곳에서는 더티체킹이나 플러쉬가 안 일어난다
    //성능이 최적화 되어있다.
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    public void updateMember(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
