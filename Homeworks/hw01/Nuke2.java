import java.io.*;

class Nuke2 {
	public static void main(String[] args) throws Exception{
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String s;
		s = keyboard.readLine();

		if(s.length() == 2){
			System.out.println(s.substring(0,1));
		}
		else{
			System.out.println(s.substring(0,1) + s.substring(2, s.length()));
		}
	}
}