/**
 * @author James Morand
 */
public class BankAccount {
    private int balance;
    private String accountName;
    private String accountId;

    public BankAccount(int balance, String accountName, String accountId) {
        this.balance = balance;
        this.accountName = accountName;
        this.accountId = accountId;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String coustomersName) {
        this.accountName = coustomersName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
