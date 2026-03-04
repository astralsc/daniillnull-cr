package daniillnull.javacr.encryption2v;

import daniillnull.Sodium;
import daniillnull.util.Helpers;
import java.util.Arrays;

public class Crypt {
   public static final byte[] sk = Helpers.parseHexBinary("1891d401fadb51d25d3a9174d472a9f691a45b974285d47729c45c6538070d85");
   public static final byte[] pk = Helpers.parseHexBinary("72f1a4a4c48e44da0c42310f800e96624e6dc6a641a9d41c3b5039d8dfadc27e");
   public byte[] clientKey = null;
   public byte[] sharedKey = null;
   public byte[] decryptNonce;
   public byte[] encryptNonce;

   public Crypt() {
      this.encryptNonce = Sodium.sodium.genericHash("hashedpass".getBytes());
   }

   public byte[] decrypt(byte[] packet, int id) {
      if (id == 10100) {
         return packet;
      } else if (id == 10101) {
         this.clientKey = Arrays.copyOf(packet, 32);
         this.sharedKey = Arrays.copyOf(packet, 32);
         byte[] nonce = Sodium.sodium.genericHash(Helpers.concat(this.clientKey, pk));
         byte[] chipherText = Arrays.copyOfRange(packet, this.clientKey.length, packet.length);
         byte[] message = Sodium.sodium.openPublicBox(chipherText, nonce, this.clientKey, sk);
         this.decryptNonce = Arrays.copyOfRange(message, 24, 48);
         return Arrays.copyOfRange(message, 48, message.length);
      } else {
         Sodium.sodium.incr2x(this.decryptNonce);
         return Sodium.sodium.openBox(packet, this.decryptNonce, this.sharedKey);
      }
   }

   public byte[] encrypt(byte[] packet, int id) {
      if (id != 20100 && (id != 20103 || this.clientKey != null)) {
         byte[] nonce;
         if (id != 20103 && id != 20104) {
            Sodium.sodium.incr2x(this.encryptNonce);
            nonce = Sodium.sodium.createBox(packet, this.encryptNonce, this.sharedKey);
            return nonce;
         } else {
            nonce = Sodium.sodium.genericHash(Helpers.concat(this.decryptNonce, this.clientKey, pk));
            byte[] message = Helpers.concat(this.encryptNonce, this.sharedKey, packet);
            byte[] chipherText = Sodium.sodium.createPublicBox(message, nonce, this.clientKey, sk);
            return chipherText;
         }
      } else {
         return packet;
      }
   }
}
