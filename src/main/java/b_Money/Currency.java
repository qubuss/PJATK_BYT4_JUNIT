package b_Money;

public class Currency {
	private String name;
	private Double rate;
	
	/**
	 * New Currency
	 * The rate argument of each currency indicates that Currency's "universal" exchange rate.
	 * Imagine that we define the rate of each currency in relation to some universal currency.
	 * This means that the rate of each currency defines its value compared to this universal currency.
	 *
	 * @param name The name of this Currency
	 * @param rate The exchange rate of this Currency
	 */
	Currency (String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	/** Convert an amount of this Currency to its value in the general "universal currency"
	 * (As mentioned in the documentation of the Currency constructor)
	 * 
	 * @param amount An amount of cash of this currency.
	 * @return The value of amount in the "universal currency"
	 */
	public Integer universalValue(Integer amount) {
		Integer result = Double.valueOf(amount*this.getRate()).intValue();
		return result;
	}

	/** Get the name of this Currency.
	 * @return name of Currency
	 */
	public String getName() {
		return name;
	}
	
	/** Get the rate of this Currency.
	 * 
	 * @return rate of this Currency
	 */
	public Double getRate() {
		return rate;
	}
	
	/** Set the rate of this currency.
	 * 
	 * @param rate New rate for this Currency
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	/** Convert an amount from another Currency to an amount in this Currency
	 * 
	 * @param amount Amount of the other Currency
	 * @param othercurrency The other Currency
	*/
	public Integer valueInThisCurrency(Integer amount, Currency othercurrency) {
		Integer d = othercurrency.universalValue(amount); //universalValue
		Integer result = Double.valueOf(d/this.getRate()).intValue();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Currency)) return false;

		Currency currency = (Currency) o;

		if (getName() != null ? !getName().equals(currency.getName()) : currency.getName() != null) return false;
		return getRate() != null ? getRate().equals(currency.getRate()) : currency.getRate() == null;

	}

	@Override
	public int hashCode() {
		int result = getName() != null ? getName().hashCode() : 0;
		result = 31 * result + (getRate() != null ? getRate().hashCode() : 0);
		return result;
	}
}
