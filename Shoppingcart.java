import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    public static void main(String[] args) {
        List<Map<String, Object>> basket = new ArrayList<>();
        basket.add(createProduct("Leather Jacket", 1100, 18, 1));
        basket.add(createProduct("Umbrella", 900, 12, 4));
        basket.add(createProduct("Cigarette", 200, 28, 3));
        basket.add(createProduct("Honey", 100, 0, 2));

        Map<String, Object> maxGSTProduct = findMaxGSTProduct(basket);
        System.out.println("Product with maximum GST amount: " + maxGSTProduct.get("Product"));

        double totalAmount = calculateTotalAmount(basket);
        double discountedAmount = calculateDiscountedAmount(basket);
        double finalAmount = totalAmount - discountedAmount;
        System.out.println("Total amount to be paid: " + finalAmount);
    }

    private static Map<String, Object> createProduct(String name, double price, int gst, int quantity) {
        Map<String, Object> product = new HashMap<>();
        product.put("Product", name);
        product.put("Price", price);
        product.put("GST", gst);
        product.put("Quantity", quantity);
        return product;
    }

    private static Map<String, Object> findMaxGSTProduct(List<Map<String, Object>> basket) {
        return basket.stream()
                .max((p1, p2) -> Double.compare((Double) p1.get("Price") * (Integer) p1.get("GST") / 100,
                        (Double) p2.get("Price") * (Integer) p2.get("GST") / 100))
                .orElse(null);
    }

    private static double calculateTotalAmount(List<Map<String, Object>> basket) {
        return basket.stream()
                .mapToDouble(p -> (Double) p.get("Price") * (1 + (Integer) p.get("GST") / 100) * (Integer) p.get("Quantity"))
                .sum();
    }

    private static double calculateDiscountedAmount(List<Map<String, Object>> basket) {
        return basket.stream()
                .filter(p -> (Double) p.get("Price") >= 500)
                .mapToDouble(p -> (Double) p.get("Price") * 0.05 * (Integer) p.get("Quantity"))
                .sum();
    }
}
