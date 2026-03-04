package daniillnull.javacr.messages;

import daniillnull.javacr.encryption2v.Crypt;
import daniillnull.javacr.messages.client.ChangeAlliance;
import daniillnull.javacr.messages.client.CreateAlliance;
import daniillnull.javacr.messages.client.ExecuteCmd;
import daniillnull.javacr.messages.client.GetAllianceData;
import daniillnull.javacr.messages.client.GetHomeData;
import daniillnull.javacr.messages.client.GetJoinableAlliances;
import daniillnull.javacr.messages.client.GetProfileData;
import daniillnull.javacr.messages.client.JoinAlliance;
import daniillnull.javacr.messages.client.KeepAlive;
import daniillnull.javacr.messages.client.LeaveAlliance;
import daniillnull.javacr.messages.client.Login;
import daniillnull.javacr.messages.client.PromoteAllianceMember;
import daniillnull.javacr.messages.client.SendAllianceMessage;
import daniillnull.javacr.messages.client.SessionReq;
import daniillnull.javacr.messages.client.SetName;
import daniillnull.javacr.messages.client.StartMission;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MessageInputStream {
   public DataInputStream is;
   public Crypt cr;

   public MessageInputStream(InputStream is, Crypt cr) {
      this.is = new DataInputStream(is);
      this.cr = cr;
   }

   public Packet read() throws IOException {
      int type = this.is.readChar();
      int length = this.is.read() << 16 | this.is.read() << 8 | this.is.read();
      char version = this.is.readChar();
      byte[] data = new byte[length];
      this.is.read(data);
      data = this.cr.decrypt(data, type);
      Packet p = null;
      switch(type) {
      case '❴':
         p = new SessionReq();
         break;
      case '❵':
         p = new Login();
         break;
      case '❻':
      case '⪙':
         return this.read();
      case '❼':
         p = new KeepAlive();
         break;
      case '⟤':
      case '㤈':
         p = new SetName();
         break;
      case '㜕':
         p = new GetHomeData();
         break;
      case '㜖':
         p = new ExecuteCmd();
         break;
      case '㜘':
         p = new StartMission();
         break;
      case '㜡':
         p = new GetProfileData();
         break;
      case '㟝':
         p = new CreateAlliance();
         break;
      case '㟞':
         p = new GetAllianceData();
         break;
      case '㟟':
         p = new GetJoinableAlliances();
         break;
      case '㟡':
         p = new JoinAlliance();
         break;
      case '㟢':
         p = new PromoteAllianceMember();
         break;
      case '㟤':
         p = new LeaveAlliance();
         break;
      case '㟫':
         p = new SendAllianceMessage();
         break;
      case '㟬':
         p = new ChangeAlliance();
         break;
      default:
         System.out.println("Unhandled: " + type);
         System.out.println(Arrays.toString(data));
         return this.read();
      }

      ((Packet)p).id = type;
      ((Packet)p).version = version;
      ((Packet)p).data = data;
      ((Packet)p).process();
      return (Packet)p;
   }

   public void close() throws IOException {
      this.is.close();
   }
}
