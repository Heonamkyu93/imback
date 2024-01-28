package com.refore.our.websocketChat.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.refore.our.member.jwt.TokenService;
import com.refore.our.websocketChat.dto.WsChatDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final TokenService tokenService;


    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();


    @Override  // 클라이언트가 접속에 성공했을경우
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
     //   List<String> subProtocols = session.getHandshakeHeaders().get("Sec-WebSocket-Protocol");
     //   String token = subProtocols.get(0);
     //   System.out.println("token = " + token);
                System.out.println("연결성공");
        sessions.add(session);
    }


    @Override  // 메시지를 보냈을때
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();

        WsChatDto wsChatDto = mapper.readValue(payload, WsChatDto.class);
        Long chatRoomId = wsChatDto.getChatRoomId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if (!chatRoomSessionMap.containsKey(chatRoomId)) {
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        // message 에 담긴 타입을 확인한다.
        // 이때 message 에서 getType 으로 가져온 내용이
        // ChatDTO 의 열거형인 MessageType 안에 있는 ENTER 과 동일한 값이라면
//        if (wsChatDto.getMessageType().equals(WsChatDto.MessageType.ENTER)) {
//            // sessions 에 넘어온 session 을 담고,
//            chatRoomSession.add(session);
//        }
//        if (chatRoomSession.size() >= 3) {
//            removeClosedSession(chatRoomSession);
//        }
//        sendMessageToChatRoom(wsChatDto, chatRoomSession);

    }

    @Override   // 끊겼을 때
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(WsChatDto wsChatDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, wsChatDto));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
