package b_Money;

import java.math.BigDecimal;

public class Money implements Comparable {
    private int amount;
    private Currency currency;

    /**
     * New Money
     *
     * @param amount   The amount of money
     * @param currency The currency of the money
     */
    Money(Integer amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Return the amount of money.
     *
     * @return Amount of money in Double type.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Returns the currency of this Money.
     *
     * @return Currency object representing the currency of this Money
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Returns the amount of the money in the string form "(amount) (currencyname)", e.g. "10.5 SEK".
     * Recall that we represent decimal numbers with integers. This means that the "10.5 SEK" mentioned
     * above is actually represented as the integer 1050
     *
     * @return String representing the amount of Money.
     */
    @Override
    public String toString() {
        return "Money{" +
                "amount=" + new BigDecimal(amount / 100).setScale(2) +
                ", currency=" + currency.getName() +
                '}';
    }

    /**
     * Gets the universal value of the Money, according the rate of its Currency.
     *
     * @return The value of the Money in the "universal currency".
     */
    public Integer universalValue() {
        Integer result = this.getCurrency().universalValue(this.getAmount());
        return result;
    }

    /**
     * Check to see if the value of this money is equal to the value of another Money of some other Currency.
     *
     * @param other The other Money that is being compared to this Money.
     * @return A Boolean indicating if the two monies are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Money)) return false;

        Money money = (Money) other;

        if (!getAmount().equals(money.getAmount())){
            return false;
        }

        return getCurrency() != null ? getCurrency().equals(money.getCurrency()) : money.getCurrency() == null;
    }

    /**
     * Adds a Money to this Money, regardless of the Currency of the other Money.
     *
     * @param other The Money that is being added to this Money.
     * @return A new Money with the same Currency as this Money, representing the added value of the two.
     * (Remember to convert the other Money before adding the amounts)
     */
    public Money add(Money other) {

//        Integer otherAmount = this.getCurrency().valueInThisCurrency(other.getAmount(), other.getCurrency());
//        Integer integer = this.getAmount();
//
//        Integer result =  integer + otherAmount;
//        Currency currency = this.getCurrency();
//
//        Money resultMoney = new Money(result, currency);
//        return resultMoney;

        Integer otherAmount = currency.valueInThisCurrency(other.amount, other.currency);
        Money tmpWallet = new Money(this.getAmount() + otherAmount, this.currency);

        return tmpWallet;
    }

    /**
     * Subtracts a Money from this Money, regardless of the Currency of the other Money.
     *
     * @param other The money that is being subtracted from this Money.
     * @return A new Money with the same Currency as this Money, representing the subtracted value.
     * (Again, remember converting the value of the other Money to this Currency)
     */
    public Money sub(Money other) {
//        Integer otherAmount = this.getCurrency().valueInThisCurrency(other.getAmount(), other.getCurrency());
//        Integer integer = this.getAmount();
//
//        Integer result =  integer - otherAmount;
//        Currency currency = this.getCurrency();
//
//        Money resultMoney = new Money(result, currency);
//        return resultMoney;

        Integer otherAmount = currency.valueInThisCurrency(other.amount, other.currency);
        Money tmpWallet = new Money(this.getAmount().intValue() - otherAmount, this.currency);

        return tmpWallet;
    }

    /**
     * Check to see if the amount of this Money is zero or not
     *
     * @return True if the amount of this Money is equal to 0.0, False otherwise
     */
    public Boolean isZero() {
        if (this.getAmount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Negate the amount of money, i.e. if the amount is 10.0 SEK the negation returns -10.0 SEK
     *
     * @return A new instance of the money class initialized with the new negated money amount.
     */
    public Money negate() {
        int newAmount = new BigDecimal(this.getAmount()).negate().intValue();
        return new Money(newAmount, this.getCurrency());
    }

    /**
     * Compare two Monies.
     * compareTo is required because the class implements the Comparable interface.
     * (Remember the universalValue method, and that Integers already implement Comparable).
     * Also, since compareTo must take an Object, you will have to explicitly downcast it to a Money.
     *
     * @return 0 if the values of the monies are equal.
     * A negative integer if this Money is less valuable than the other Money.
     * A positive integer if this Money is more valuiable than the other Money.
     */
    public int compareTo(Object other) {
        Money otherMoney = (Money) other;
        Integer thisAmount = this.universalValue();
        Integer otherAmount = otherMoney.universalValue();
        if(thisAmount.equals(otherAmount)){
            return 0;
        }else if(thisAmount > otherAmount){
            return 1;
        }else {
            return -1;
        }

    }


    @Override
    public int hashCode() {
        int result = getAmount();
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        return result;
    }
}