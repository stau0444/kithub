package com.toyproject.kithub.service;

import com.toyproject.kithub.domain.Member;
import com.toyproject.kithub.exception.ExistMemberException;
import com.toyproject.kithub.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //스프링의 트렌젝셔널은 기본적으로 commit이 아니라
    // 롤백을 시키기 떄문에 rollback false 필요
    // 롤백을 유지하면서 인서트 쿼리를 확인하고 싶으면 강제로 flush() 해준다.
    @Rollback(value = false)
    void 회원가입() throws  Exception{
        //given
        Member member = new Member();
        member.setName("member1");

        //when
        Long savedId = memberService.join(member);

        //then
        Assertions.assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test
    void 중복_회원_예외() throws  Exception{
        //given
            Member member1 = new Member();
            member1.setName("member1");
            Member member2 = new Member();
            member2.setName("member1");
        //when
        Long savedMember1 = memberService.join(member1);
        //예외상황 발생을 테스트 할 시 아래처럼 하거나
        // @Test에 옵션으로 expected 를 junit4 까지는 줄 수 있었지만
        // junit5 부터는 assertThrow를 이용해서
        // exception 상황을 테스트 한다 .
        // assertThrows에 첫번째 파라미터로 예상하는 예외 타입과
        // 두번쨰 파라미터로 예외가 터질 것으로 예상하는 코드를 excutable로 넣어 준다.
        Assertions.assertThrows(ExistMemberException.class,
                ()->{ Long savedMember2 = memberService.join(member2);});
    }
}