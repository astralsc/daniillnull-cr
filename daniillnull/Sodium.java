package daniillnull;

public class Sodium {
   public static Sodium sodium = new Sodium();

   private Sodium() {
      System.loadLibrary("lib");
   }

   public native byte[] genericHash(byte[] var1);

   public native byte[] openPublicBox(byte[] var1, byte[] var2, byte[] var3, byte[] var4);

   public native byte[] createPublicBox(byte[] var1, byte[] var2, byte[] var3, byte[] var4);

   public native byte[] openBox(byte[] var1, byte[] var2, byte[] var3);

   public native byte[] createBox(byte[] var1, byte[] var2, byte[] var3);

   public native void incr2x(byte[] var1);
}
