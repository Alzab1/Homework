import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Wall {
    private String token;

    public JSONArray getWall(String token) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.get?");
        builder.setParameter("access_token"
                , token)
                .setParameter("owner_id", "503105711")
                .setParameter("v", "5.103");
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        String json = EntityUtils.toString(response.getEntity());
        System.out.println(json);
        JSONArray wallJsonObjects = JsonPath.read(json, "$.response.items");
        return wallJsonObjects;
    }
    public boolean messageIsPosted(String postText, String token) throws IOException, URISyntaxException {
        String postId = wallPost(token, postText);
        JSONArray wallJsonObjects = getWall(token);
        return wallJsonObjects.stream().anyMatch(i -> JsonPath.read(i, "$.id").equals(Integer.parseInt(postId))
                    && JsonPath.read(i, "$.text").equals(postText));
    }
    public boolean messageIsEdited(String token, String postText, String editText) throws IOException, URISyntaxException {
        String postId = wallPost(token, postText);
        wallEditPost(token, editText, postId);
        JSONArray wallJsonObjects = getWall(token);
        return wallJsonObjects.stream().anyMatch(i -> JsonPath.read(i, "$.id").equals(Integer.parseInt(postId))
                && JsonPath.read(i, "$.text").equals(editText));
    }
    public boolean postIsDeleted(String token, String postText) throws IOException, URISyntaxException {
        String postId = wallPost(token, postText);
        wallDeletePost(postId, token);
        JSONArray wallJsonObjects = getWall(token);
        return wallJsonObjects.stream().noneMatch(i -> JsonPath.read(i, "$.id").equals(Integer.parseInt(postId))
                && JsonPath.read(i, "$.text").equals(postText));
    }

    public String wallPost(String token, String postText) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.post?");
        builder.setParameter("access_token"
                , token)
                .setParameter("owner_id", "503105711")
                .setParameter("message", postText)
                .setParameter("v", "5.103");
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        String str = EntityUtils.toString(response.getEntity());
        System.out.println(str);
        String postId = str.replaceAll("\\D", "");
        return postId;
    }

    public void wallEditPost(String token, String editText, String postId) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.edit?");
        builder.setParameter("access_token"
                , token)
                .setParameter("owner_id", "503105711")
                .setParameter("message", editText)
                .setParameter("v", "5.103")
                .setParameter("post_id", postId);
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    public void wallDeletePost(String postId, String token) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.delete?");
        builder.setParameter("access_token"
                , token)
                .setParameter("owner_id", "503105711")
                .setParameter("v", "5.103")
                .setParameter("post_id", postId);
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}