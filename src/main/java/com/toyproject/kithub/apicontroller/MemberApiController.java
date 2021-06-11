package com.toyproject.kithub.apicontroller;


import com.toyproject.kithub.domain.Member;
import com.toyproject.kithub.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long saveMemberId = memberService.join(member);
        return  new CreateMemberResponse(saveMemberId);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.name);

        Long saveMemberId = memberService.join(member);
        return  new CreateMemberResponse(saveMemberId);
    }

    //등록과 수정은 api 스펙이 다를 경우가 많기 떄문에
    //별도의 Dto를 생성하는 것이 좋다 .
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.updateMember(id,request.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(),member.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> getMemberList(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result getMemberList2(){

        List<Member> members = memberService.findMembers();
        List<MemberDto> memberDtoList = members.stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());
        Member member = new Member();
        member.setName("member");
        return new Result(memberDtoList,member);
    }

    @Data
    @AllArgsConstructor
    static class Result<T,G>{
        private T data;
        private G member;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberRequest{
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateMemberRequest{
        private String name;
    }
}
