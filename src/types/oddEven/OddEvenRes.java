package types.oddEven;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OddEvenRes implements Serializable {

  private Choice choice;
  private int number;
}
