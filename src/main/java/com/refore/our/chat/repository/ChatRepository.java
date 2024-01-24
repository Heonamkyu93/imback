package com.refore.our.chat.repository;

import com.refore.our.chat.dto.ChatDto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository <ChatDto,String> {
    @Query("{sender: ?0,receiver:?1}")
    Flux<ChatDto> mFindBySender(String sender,String receiver); // flux 계속 데이터를 받겠다 response를 유지하면서 데이터를 흘려 보내기
}
