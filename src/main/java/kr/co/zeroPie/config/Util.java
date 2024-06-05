package kr.co.zeroPie.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.zeroPie.dto.ChatMessageDTO;
import org.springframework.web.socket.TextMessage;


public class Util {
    public static class Chat {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        public static TextMessage resolveTextMessage(ChatMessageDTO message) {
            try {
                return new TextMessage(objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        public static ChatMessageDTO resolvePayload(String payload) {
            try {
                return objectMapper.readValue(payload, ChatMessageDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
