package hmrc.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hmrc.shop.product.Product;
import static hmrc.shop.product.Product.APPLE;
import static hmrc.shop.product.Product.ORANGE;;

/**
 * Hello world!
 *
 */
public class ShoppingCart 
{
	
	private List<Product> itemList = new ArrayList<>();

	private boolean withOffer = false;
	
	/**
	 * @param withOffer
	 */
	public ShoppingCart(boolean withOffer) {
		super();
		this.withOffer = withOffer;
	}

	public ShoppingCart() {
		this.withOffer = false;
	}

	public void add(Product product) {
		itemList.add(product);
	}
	
	public long getCount() {
		return itemList.stream().count();
	}
	
	public double getPrice() {
		if (!withOffer)
			return itemList.stream().mapToDouble((i)->i.getPrice()).sum();
		else {
			double price = getAppleOfferPrice();
			return price;
		}
	}

	/**
	 * @return
	 */
	private double getAppleOfferPrice() {
		List<Product> appleList = itemList.stream().filter(i -> i == APPLE).collect(Collectors.toList());
		boolean apple1FreeToggle = true;
		double price = 0.0;
		for (Product apple : appleList) {
			if (apple1FreeToggle) {
				price += apple.getPrice();
				apple1FreeToggle = false;
			}else {
				apple1FreeToggle = true;
			}
		}
		return price;
	}
	private double getOrangeOfferPrice() {
		List<Product> appleList = itemList.stream().filter(i -> i == ORANGE).collect(Collectors.toList());
		boolean apple1FreeToggle = true;
		double price = 0.0;
		for (Product apple : appleList) {
			if (apple1FreeToggle) {
				price += apple.getPrice();
				apple1FreeToggle = false;
			}else {
				apple1FreeToggle = true;
			}
		}
		return price;
	}
	public boolean isWithOffer() {
		return withOffer;
	}
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
