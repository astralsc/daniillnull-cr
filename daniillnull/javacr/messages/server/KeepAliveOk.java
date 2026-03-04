package daniillnull.javacr.messages.server;

import daniillnull.javacr.messages.Packet;
import java.io.IOException;

public class KeepAliveOk extends Packet {
   public KeepAliveOk() {
      this.id = 20108;
   }

   public void process() throws IOException {
   }
}
