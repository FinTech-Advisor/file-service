package org.advisor.board.controllers;

import lombok.RequiredArgsConstructor;
import org.advisor.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    public JSONData config(@PathVariable("bid") String bid){
        return null;
    }

    @PostMapping("/save")
    public JSONData save(){
        return null;
    }

    /**
     * 게시글 한개 조회
     * -글보기 글 수정 시에 활용될 수 있음(프론트엔드)
     *
     * @param seq
     * @return
     */
    @PostMapping("/view/{seq}")
    public JSONData view(@PathVariable("seq") Long seq){
        return null;
    }

    /**
     * 게시글 목록 조회
     * @param bid
     * @return
     */
    @GetMapping("/list/{bid}")
    public JSONData list(@PathVariable("bid") String bid){
        return null;
    }

    /**
     * 게시글 한 개 삭제
     * @param seq
     * @return
     */
    @DeleteMapping("/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq){
        return null;
    }
}

