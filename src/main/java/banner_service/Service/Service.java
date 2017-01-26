package banner_service.Service;

import dao.implementation.ProductDaoJbdc;
import model.Product;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by svindler on 10.01.2017.
 */

public class Service {

    private static Service instance;
    private static String basic_banner_HTML = "<div class=\"container\">" +
            "<div class=\"col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3\">" +
            "<a href=\"https://codecool.hu/\"><img src=\"http://www.auplod.com/u/opladu8f6e7.gif\" border=\"1\">" +
            "</a></div></div>";

    private static String customer_HTML = "<div class=\"container\">" +
            "<div class=\"col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3\">" +
            "<a href=\"https://codecool.hu/\"><img src=\"http://www.auplod.com/u/aoludp8f70b.gif\" border=\"1\">" +
            "</a></div></div>";

    private Service() {
    }



    public static Service getInstance(){
        if (instance == null){
            instance = new Service();
        }
        return instance;
    }

    public JSONObject getBanner(){
        JSONObject obj = new JSONObject();
        obj.put("status","done");
        obj.put("Advertisement", basic_banner_HTML);
        return obj;
    }

    public JSONObject getBanner(String user) {
        ProductDaoJbdc productDaoJbdc = ProductDaoJbdc.getInstance();
        Product product = productDaoJbdc.selectMostBoughtItem(user);
        JSONObject obj = new JSONObject();

        obj.put("Advertisement", createHTML(product));
        obj.put("status", "done");
        return obj;
    }

    public JSONObject getBanner(String user, JSONArray cart) {
        ProductDaoJbdc productDaoJbdc = ProductDaoJbdc.getInstance();
        for(int i = 0; i < cart.length(); i++) {
            JSONObject product = cart.getJSONObject(i);

            if(productDaoJbdc.checkIfProductIsInDatabase(product.getString("name"))) {
                productDaoJbdc.updateQuantity(productDaoJbdc.getQuantity(product.getString("name")) + product.getInt("quantity")
                        , product.getString("name"), user);
            }else {
                Product actualProduct = new Product(user, product.getString("name"),
                        product.getString("category"), product.getString("defaultprice"), product.getInt("quantity"));
                productDaoJbdc.add(actualProduct);
            }
        }
        Product product = productDaoJbdc.selectMostBoughtItem(user);

        System.out.println(product.getUser() + product.getQuantity());
        JSONObject obj = new JSONObject();

        obj.put("Advertisement", createHTML(product));
        obj.put("status", "done");
        return obj;
    }

    public String createHTML(Product product){
        return "<style>#suggestion {height: 100px;width: 500px;background-color: pink;border-style: dotted;text-align: center;}p{font-style: oblique;color: silver;}</style>" +
                "<div id=\"suggestion\" class=\"container\">" +
                "<div class=\"col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3\">" +
                "<p>Suggestion:"+product.getCategory() +" "+ product.getName() +" "+product.getPrice()+"</p>" +
                "</a></div></div>";
    }
}
