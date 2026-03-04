package daniillnull.javacr.messages;

import daniillnull.javacr.encryption2v.Crypt;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MessageOutputStream {
   public DataOutputStream is;
   public Crypt cr;

   public MessageOutputStream(OutputStream is, Crypt cr) {
      this.is = new DataOutputStream(is);
      this.cr = cr;
   }

   public void write(Packet p) throws IOException {
      p.process();
      p.data = this.cr.encrypt(p.data, p.id);
      this.is.writeChar((char)p.id);
      this.is.write(p.data.length >>> 16);
      this.is.write(p.data.length >>> 8);
      this.is.write(p.data.length >>> 0);
      this.is.writeChar(p.version);
      this.is.write(p.data);
   }

   public void close() throws IOException {
      this.is.close();
   }
}
