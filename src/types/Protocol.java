package types;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Protocol<T> implements Serializable {
  public enum MessageType {
    HEARTBEAT, GAME
  }

  private MessageType type;
  private T message;
}
