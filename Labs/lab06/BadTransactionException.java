public class BadTransactionException extends Exception{
	public int BadTransactionMoney;

	public BadTransactionException(int money){
		super("Tried to deposit less than 0: " + money);

		BadTransactionMoney = money;
	}
}