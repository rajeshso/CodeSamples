package hmrc.shop.product;

public enum Product {
	APPLE(0.6),ORANGE(0.25);
	private double price;
	Product(double price) {
		this.price = price;
	}
	public double getPrice() {
		return price;
	}
/*	APPLE(0.6),ORANGE(0.25),MANGO(9.0) {
		@Override
		public double getPrice() {
			return 10.0;
		}	
	};*/
}
