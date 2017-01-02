package hmrc.shop;

import hmrc.shop.product.Product;
import static hmrc.shop.product.Product.APPLE;
import static hmrc.shop.product.Product.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for simple ShoppingCart.
 */
public class ShoppingCartTestCase 
{
    /**
     * Rigourous Test :-)
     */
	@Test
    public void testApp()
    {
        assertTrue( true );
    }
    
    @Test
    public void checkIfCountWorks() {
    	ShoppingCart cart = new ShoppingCart();
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	cart.add(Product.ORANGE);
    	assertThat(cart.getCount()).isEqualTo(3);
    }
    
    @Test
    public void priceOfTwoApplesShouldWork() {
    	ShoppingCart cart = new ShoppingCart();
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	assertThat(cart.getCount()).isEqualTo(2);
    	assertThat(cart.getPrice()).isEqualTo(1.2);
    }
    
    @Test
    public void priceOfTwoApplesAndOneOrangeShouldWork() {
    	ShoppingCart cart = new ShoppingCart();
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	cart.add(Product.ORANGE);
    	assertThat(cart.getCount()).isEqualTo(3);
    	assertThat(cart.getPrice()).isEqualTo(1.45);
    }
    @Test
    public void priceOfTwoApplesWithoutOffer() {
    	ShoppingCart cart = new ShoppingCart();
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	assertThat(cart.getCount()).isEqualTo(2);
    	assertThat(cart.getPrice()).isEqualTo(1.2);
    }
    @Test
    public void priceOfTwoApplesWithOffer() {
    	ShoppingCart cart = new ShoppingCart(true);
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	assertThat(cart.getCount()).isEqualTo(2);
    	assertThat(cart.getPrice()).isEqualTo(0.6);
    }
    @Test
    public void priceOfThreeApplesWithOffer() {
    	ShoppingCart cart = new ShoppingCart(true);
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	cart.add(Product.APPLE);
    	assertThat(cart.getCount()).isEqualTo(3);
    	assertThat(cart.getPrice()).isEqualTo(1.2);
    }
    @Test
    public void priceOfThreeOrangesWithOffer() {
    	ShoppingCart cart = new ShoppingCart(true);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	assertThat(cart.getCount()).isEqualTo(3);
    	assertThat(cart.getPrice()).isEqualTo(0.5);
    }
    @Test
    public void priceOfTwoApplesThreeOrangesWithOffer() {
    	ShoppingCart cart = new ShoppingCart(true);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(APPLE);
    	cart.add(APPLE);
    	assertThat(cart.getCount()).isEqualTo(5);
    	assertThat(cart.getPrice()).isEqualTo(1.1);
    }
    @Test
    public void priceOfThreeApplesSevenOrangesWithOffer() {
    	ShoppingCart cart = new ShoppingCart(true);
    	cart.add(APPLE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(ORANGE);
    	cart.add(APPLE);
    	cart.add(APPLE);
    	assertThat(cart.getCount()).isEqualTo(10);
    	assertThat(cart.getPrice()).isEqualTo(2.45);
    }
}
