package hmrc.shop;

import java.util.ArrayList;
import java.util.List;

import hmrc.shop.product.Product;

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
		return itemList.stream().mapToDouble((i)->i.getPrice()).sum();
	}

	public boolean isWithOffer() {
		return withOffer;
	}
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
