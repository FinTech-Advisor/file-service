package org.advisor.board.controllers;

import lombok.RequiredArgsConstructor;
import org.advisor.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class BoardAdminController {

    /**
     * 게시판 설정 등록, 수정 처리
     * @return
     */
    @PostMapping("/config")
    public JSONData save(){return null;};

    /**
     * 게시판 설정 등록
     * @return
     */
    @PatchMapping("/config")
    public JSONData list(){
        return null;
    }

    public JSONData delete(@RequestParam List<String> bids){
        return null;
    }
}
