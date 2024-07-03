package com.web.bookservice.controller.api;

import com.web.bookservice.domain.Member;
import com.web.bookservice.dto.UserDTO;
import com.web.bookservice.dto.UserUpdateDTO;
import com.web.bookservice.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/users/{loginId}")
    @ResponseBody
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request, @PathVariable("loginId") String loginId) {

        Member findMember = memberService.findByLoginId(loginId);

        UserDTO userDTO = new UserDTO();
        userDTO.setCity(findMember.getCity());
        userDTO.setName(findMember.getName());
        userDTO.setJoinDate(findMember.getJoinDate());
        userDTO.setLoginId(findMember.getLoginId());

        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/api/users/{loginId}")
    @ResponseBody
    public  ResponseEntity<?> profileUpdate(@PathVariable("loginId") String loginId,
                                            @RequestBody UserUpdateDTO userUpdateDTO, HttpServletRequest request) {

        Member member = (Member) request.getSession(false).getAttribute("user");

        String password = userUpdateDTO.getPassword();

        if(!memberService.validatePassword(password, member)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        String name = userUpdateDTO.getName();
        String city = userUpdateDTO.getCity();

        memberService.profileUpdate(member, name, city);

        return ResponseEntity.ok("정보가 수정되었습니다.");
    }

}
