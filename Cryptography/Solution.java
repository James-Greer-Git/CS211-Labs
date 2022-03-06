import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Solution {
    public static void main (String args[])
    {

        Scanner scanner = new Scanner(System.in);
        //Alice's Public Key
        long p = scanner.nextLong();                //modulus
        long g = scanner.nextLong();                //generator number
        long gPowXModp = scanner.nextLong();        //g^x modulo p

        long x = findPower(g, p, gPowXModp);

        //Bob's Cipher
        long gPowYModp = scanner.nextLong();        //g^y modulo p
        long m_gPowXYModp = scanner.nextLong();     //m*g^xy modulo p

        long c1 = modPow(gPowYModp, p - 1 - x, p);

        long m = (m_gPowXYModp*c1)%p;
        
        scanner.nextLine();

        String payload = scanner.nextLine();
        scanner.close();

        System.out.println(decrypt(m, payload));

        
    }
    public static long findPower(long number, long modulus, long result){
        for(int i = 0; i < modulus; i++){
            if(modPow(number, i, modulus) == result){
                return i;
            }
        }
        return 0;
    }








    public static String decrypt(long sharedkey, String bytelist){
//send this method the shared key and bytelist read from the third input line and it will decrypt using DES and return the decrypted String
        try{
            byte[] keyBytes= new byte[8];
            byte[] ivBytes= new byte[8];
            for (int i = 7; i >= 0; i--) {
                keyBytes[i] = (byte)(sharedkey & 0xFF);
                ivBytes[i] = (byte)(sharedkey & 0xFF);
                sharedkey >>= 8;
            }
            // wrap key data in Key/IV specs to pass to cipher
            SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            // create the cipher with the algorithm you choose
            // see javadoc for Cipher class for more info, e.g.
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            String[] process=bytelist.split(", ");
            int enc_len=process.length;
            byte[] encrypted= new byte[cipher.getOutputSize(enc_len)];
            for(int i=0;i<process.length;i++){
                encrypted[i]=(byte)(Integer.parseInt(process[i]));
            }
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
            int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
            cipher.doFinal(decrypted, dec_len);
            return (new String(decrypted, "UTF-8").trim());  
        }catch(Exception e){return "Error: "+e;}
    }
    
    
    public static long modPow(long number, long power, long modulus)
    {
//raises a number to a power with the given modulus
//when raising a number to a power, the number quickly becomes too large to handle
//you need to multiply numbers in such a way that the result is consistently moduloed to keep it in the range
//however you want the algorithm to work quickly - having a multiplication loop would result in an O(n) algorithm!
//the trick is to use recursion - keep breaking the problem down into smaller pieces and use the modMult method to join them back together
        if(power==0)
        {
            return 1;
        }
        else if (power%2==0)
        {
            long halfpower=modPow(number, power/2, modulus);
            return modMult(halfpower,halfpower,modulus);
        }
        else
        {
            long halfpower=modPow(number, power/2, modulus);
            long firstbit = modMult(halfpower,halfpower,modulus);
            return modMult(firstbit,number,modulus);
        }
    }
    
    public static long modMult(long first, long second, long modulus)
    {
//multiplies the first number by the second number with the given modulus
//a long can have a maximum of 19 digits. Therefore, if you're multiplying two ten digits numbers the usual way, things will go wrong
//you need to multiply numbers in such a way that the result is consistently moduloed to keep it in the range
//however you want the algorithm to work quickly - having an addition loop would result in an O(n) algorithm!
//the trick is to use recursion - keep breaking down the multiplication into smaller pieces and mod each of the pieces individually
        if(second==0)
        {
            return 0;
        }
        else if (second%2==0)
        {
            long half=modMult(first, second/2, modulus);
            return (half+half)%modulus;
        }
        else
        {
            long half=modMult(first, second/2, modulus);
            return (half+half+first)%modulus;
        }
    }
}
