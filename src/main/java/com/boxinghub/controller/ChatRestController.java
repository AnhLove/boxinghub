package com.boxinghub.controller.api;

import com.boxinghub.dto.ChatMessageDTO;
import com.boxinghub.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/member/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDTO>> getHistory(
            @RequestParam String email,
            Principal principal) {

        String currentUserEmail = principal.getName();
        List<ChatMessageDTO> history = chatService.getChatHistory(currentUserEmail, email);

        return ResponseEntity.ok(history);
    }
}