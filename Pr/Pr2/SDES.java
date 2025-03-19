public class SDES {
    static int[] P10={3,5,2,7,4,10,1,9,8,6},P8={6,3,7,4,8,5,10,9},IP={2,6,3,1,4,8,5,7},EP={4,1,2,3,2,3,4,1},I_={4,1,3,5,7,2,8,6},P4={2,4,3,1};
    static int[][] S0={{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}},S1={{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}};
    
    public static void main(String[] x){
        String p="10010111",k="1010000010";
        String[] K=k(k);
        System.out.println("K1 "+K[0]);
        System.out.println("K2 "+K[1]);
        System.out.println("encrypted "+e(p,K[0],K[1]));
    }
    public static String e(String p, String k1, String k2){
        String t=t(p,IP),l=t.substring(0,4),r=t.substring(4),f=fk(l,r,k1),s=f.substring(4)+f.substring(0,4);
        return t(fk(s.substring(0,4),s.substring(4),k2),I_);
    }
    public static String fk(String l,String r,String k){
        String e=t(r,EP),x=x(e,k),s=s(x),p=t(s,P4);
        return x(l,p)+r;
    }
    public static String s(String i){
        String a=i.substring(0,4),b=i.substring(4);
        int r0=Integer.parseInt(""+a.charAt(0)+a.charAt(3),2),c0=Integer.parseInt(""+a.charAt(1)+a.charAt(2),2);
        int r1=Integer.parseInt(""+b.charAt(0)+b.charAt(3),2),c1=Integer.parseInt(""+b.charAt(1)+b.charAt(2),2);
        String s0="0"+Integer.toBinaryString(S0[r0][c0]),s1="0"+Integer.toBinaryString(S1[r1][c1]);
        return s0.substring(s0.length()-2)+s1.substring(s1.length()-2);
    }
    public static String[] k(String k){
        String p=t(k,P10),l=sh(p.substring(0,5),1)+sh(p.substring(5),1),l2=sh(l.substring(0,5),2)+sh(l.substring(5),2),k1=t(l,P8),k2=t(l2,P8);
        return new String[] {k1,k2};
    }
    public static String x(String a, String b){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)==b.charAt(i)){
                sb.append('0');
            }else{
                sb.append('1');
            }
        }
        return sb.toString();
    }
    public static String t(String s, int[] p){
        StringBuilder sb= new StringBuilder();
        for(int i:p){
            sb.append(s.charAt(i-1));
        }
        return sb.toString();
    }
    public static String sh(String s,int n){
        return s.substring(n)+s.substring(0,n);
    }

}