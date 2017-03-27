
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) {
		File file = new File("proxy.txt");

		Map<Integer, String> map = new HashMap();
		String port = null;
		String ip = null;
		String url = null;
		Integer nView = 0;
		
		App app = new App();
		map = app.readFile(file, map);
		Integer i = 0;
		ip = map.get(1);
		port = map.get(2);
		url = map.get(3);
		nView = Integer.parseInt(map.get(4));
		
	
		app.run(ip,Integer.parseInt(port),url,nView);
		port = null;
		ip = null;
		
		
	}

	public Map<Integer, String> readFile(File file, Map<Integer, String> map) {
		BufferedReader reader = null;
		String ip = null;
		String port = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			Integer i = 0;
			while ((text = reader.readLine()) != null) {

				map.put(++i, text);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
		return map;

	}

	public void run(String ip, int port, String _url, Integer max) {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("output.txt"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		Integer minimum1 = 10000;
		Integer maximum1 = 13000;
		Integer minimum2 = 10000;
		Integer maximum2 = 15000;

		// 112.64.185.73", 80
		URL url;
		HttpURLConnection uc = null;
		for (int i = 0; i < max; i++) {
			try {
				Integer randomNum1 = minimum1 + (int) (Math.random() * maximum1);

				Thread.currentThread().sleep(randomNum1);
				url = new URL(_url);

				uc = (HttpURLConnection) url.openConnection(proxy);
				uc.setConnectTimeout(7000);
				uc.connect();

				Integer randomNum2 = minimum2 + (int) (Math.random() * maximum2);
				
				StringBuilder page = new StringBuilder();
				StringBuffer tmp = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					page.append(line + "\n");
				}
				System.out.println(page);
				uc.disconnect();
				Thread.currentThread().sleep(randomNum2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				uc.disconnect();
			
				System.out.println(e.getMessage());
				System.out.println(i);
				
				
				
				out.print(e.getMessage());
				
				System.setOut(out);
			}
		}
	}

}
