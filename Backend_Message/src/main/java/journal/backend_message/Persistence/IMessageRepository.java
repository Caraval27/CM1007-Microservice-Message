package journal.backend_message.Persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findByThreadId(int threadId, Sort sort);

    @Query("SELECT m FROM MessageEntity m WHERE (m.sender.id = :id OR m.receiver.id = :id) " +
            "AND m.date = (SELECT MAX(subM.date) FROM MessageEntity subM WHERE subM.threadId = m.threadId " +
            "AND (subM.sender.id = :id OR subM.receiver.id = :id))" +
            "ORDER BY m.date DESC")
    List<MessageEntity> findLatestMessagesByUserInThread(@Param("id") String id);

    @Query("SELECT COALESCE(MAX(m.threadId), 0) FROM MessageEntity m")
    int findMaxThreadId();

    @Transactional
    @Modifying
    @Query("UPDATE MessageEntity m SET m.read = true WHERE m.threadId = :threadId AND m.receiver.id = :receiverId")
    int updateMessageIsRead(String threadId, String receiverId);
}