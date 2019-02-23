
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.ecs.storage.Hash;
import org.json.JSONException;
import org.json.JSONObject;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Comment;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;

import javafx.geometry.Side;

@ServerEndpoint("/echo") // 監聽client連接的URL.
public class WebSocketTest {
	String accessToken = "";
	String pageAccessToken = "";
	String postId = "";

	// 收到client訊息便執行此方法.
	// 印出client所發送的訊息,傳送訊息給client.
	@SuppressWarnings("deprecation")
	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		if (message.length() > 30 && message.substring(0, message.lastIndexOf("?")).matches("^[A-Za-z0-9]+$")) {

			accessToken = message.substring(0, message.lastIndexOf("?"));
			System.out.println("Received: " + accessToken);

			session.getBasicRemote().sendText("Server has been received message.");

			int sentMessages = 1;
			long endTime = 0;
			boolean change = false;

			FacebookClient fbClient = new DefaultFacebookClient(accessToken);

			Connection<Page> pageConnection = fbClient.fetchConnection("me/accounts", Page.class,
					Parameter.with("limit", 10));
			for (List<Page> userPage : pageConnection) {
				for (Page page : userPage) {
					pageAccessToken = page.getAccessToken();
					System.out.println(pageAccessToken);

				}
			}
			FacebookClient fbPageClient = new DefaultFacebookClient(pageAccessToken);

			postId = message.substring(message.lastIndexOf("s/") + 2, message.lastIndexOf("/"));// "274179013141471";
			Set<String> headCount = new HashSet<String>();
			int count=0;
			while (true) {
				long startTime = new Date().getTime();
				Thread.sleep(1000);
				

				// System.out.println(postId);
				Connection<Comment> commentConnection = fbPageClient.fetchConnection(postId + "/comments",
						Comment.class, Parameter.with("limit", 10));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				JSONObject json = new JSONObject();

				for (List<Comment> commentPage : commentConnection) {
					for (Comment comment : commentPage) {
						Date date = comment.getCreatedTime();
						if (date.getTime() > endTime && date.getTime() < startTime && comment.getFrom() != null) {
							/*
							 * String image = "<img src = 'http://graph.facebook.com/" +
							 * comment.getFrom().getId() + "/picture?type=small'>";
							 */
							headCount.add(comment.getFrom().getName());
							// 把FB留言裝進json
							try {
								json.put("fb_order_time", sdf.format(comment.getCreatedTime()));
								json.put("fb_id", comment.getFrom().getId());
								json.put("fb_buyer", comment.getFrom().getName());
								json.put("fb_comment", comment.getMessage());
								json.put("fb_headCount", headCount.size());
								json.put("fb_comCount", ++count);

							} catch (JSONException e) {
								e.printStackTrace();
								// 將json往前端送
							}
							session.getBasicRemote().sendText(json.toString());

							System.out.println(json.toString());

							change = true;
						}

					}

				}
				if (change == true)
					endTime = startTime;

				/*
				 * session.getBasicRemote().sendText("Server is counting 1 to 3. Count: " +
				 * sentMessages);
				 */
				session.getBasicRemote().sendText("<h3>Total Lottery participants:  " + (sentMessages - 1) + "</h3>");
				session.getBasicRemote().sendText(
						"<h3>The Winner goes to: No. " + (int) (Math.random() * (sentMessages - 1) + 1) + "</h3>");
			}
		}
		// session.getBasicRemote().sendText("End");
		else {
			FacebookClient fbClient = new DefaultFacebookClient(pageAccessToken);
			fbClient.publish(postId + "/comments", FacebookType.class, Parameter.with("message", message));
			System.out.println("publish OK");
			session.getBasicRemote().sendText("<font color = 'red'>publish OK</font>");
		}
	}

	@OnOpen // client開啟連接.
	public void onOpen() {
		System.out.println("Client connected");
	}

	@OnClose // client關閉連接.
	public void onClose() {
		System.out.println("Connection closed");
	}
}
