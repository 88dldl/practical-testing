package sample.cafekiosk.spring.repository.history.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;

public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
