import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author James Morand
 */
public class Bank {
    //scanner  class for user input
    Scanner userInput = new Scanner(System.in);
    ArrayList <BankAccount> bank = new ArrayList <>();

    public void run(){
        loadAccounts();
        while(true) {

            loginMenu();
        }

        //test to see bank objects
//        System.out.println(bank.get(0).getAccountName());
    }

    //Load the saved file to read the accounts
    public void loadAccounts(){
        try {
            Scanner sc = new Scanner(new File("accounts.txt"));
            String name, accountId, balance;

            sc.useDelimiter("[\n]");

            // checks .txt file for next line
            while (sc.hasNextLine()) {

                String temp = sc.next();

                String [] info = temp.split(":", 10);

                name = info [0];
                accountId = info [1];
                balance = info [2];

                BankAccount bankAccount = new BankAccount(Integer.parseInt(balance), name, accountId);
                bank.add(bankAccount);

            }
            sc.close();
        }
        catch (IOException e)
        {
            System.out.printf("Error: %s", e);
        }
    }

    public void fileAddNewAccount(){
        try {
            Writer w = new FileWriter("accounts.txt", true);
            // append to file
                w.append(bank.get(bank.size() - 1).getAccountName() + ":" + bank.get(bank.size() - 1).getAccountId() + ":" + bank.get(bank.size() - 1).getBalance());

        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }
    //Overwrite the file with the recent changes
    public void updateAccounts()
    {
        try {
            Writer w = new FileWriter("accounts.txt");
                for (BankAccount b: bank)
            w.write("\n"+b.getAccountName()+ ":" + b.getAccountId()+ ":" + b.getBalance()  );


            w.flush();
            w.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }


    public void loginMenu()
    {
        System.out.println("1.) Login\n" +
                "2.) Create Account\n" +
                "3.) Exit Program\n");
         selectLoginMenu();
    }

    // let user select an existing account or create new account
    public void selectLoginMenu()
    {
        String number;
        int choose;
        int digitCount = 0;
        boolean allNumbers = true;
        choose = userIntInput("What would you like to do : ");

        switch (choose){
            case 1:
                System.out.print("Please enter account number: ");
                number = userInput.next();

                //check to see if there are any non numbers
                for (int i=0; i< number.length(); i++)
                {

                    if (!Character.isDigit(number.charAt(i))){

                        digitCount++;
                    }
                }

                if(digitCount != 0)
                {
                    allNumbers = false;
                    System.out.println("Account number cannot contain letters");
                    break;
                }

                if( allNumbers == true) {

                    for (BankAccount k : bank) {
                        if (k.getAccountId().equalsIgnoreCase(number)) {
                            System.out.printf("Welcome %s", k.getAccountName());
                            accountMenu(k);
                        }

                    }
                }
                break;
            case 2:
                newAccount();
                loginMenu();
                break;
            case 3:
                System.exit(0);
                default:
                    System.out.println("Error please choose 1, 2, 3");
                break;
        }

    }

    // user menu
    public void accountMenu(BankAccount account)
    {int choose=0;

    // user might want to use access account more than once
        // loop till user escapes with 4.
        // update file and return to the login menu
    while (choose != 4) {
        System.out.printf("%n1.) Check Balance \n" +
                "2.) Deposit\n" +
                "3.) Withdraw\n" +
                "4.) Exit\n");
        choose = userInput.nextInt();
        selectAccountMenu(choose, account);
    }

    }

    public void selectAccountMenu (int x , BankAccount account)
    {int amount;
        switch (x)
        {
            case 1:
                System.out.printf("Your Balance is: %d", account.getBalance());
                break;
            case 2:
                amount = userIntInput("How much would you like to deposit: ");
                deposit(amount,account);
                updateAccounts();
                System.out.printf("%n Your new balance is %d", account.getBalance());
                break;
            case 3:
                amount = userIntInput("How much would you like to withdraw: ");
                withdraw(amount,account);
                updateAccounts();
                System.out.printf("%nYour new balance is %d", account.getBalance());
                break;
            case 4:
                loginMenu();
                break;
        }
    }

    //create a new user account
    public void newAccount()
    {
        boolean isDup = true;
        Random random = new Random();
        BankAccount newAccount = new BankAccount(0,"0", "0");
        System.out.print("Enter the name of the account: ");
        String name = userInput.next();
        newAccount.setAccountName(name);
        System.out.println("how much would you like to put in the account?: ");
        int init = userInput.nextInt();
        newAccount.setBalance(init);

        // randomly generate an account number
        do {
            newAccount.setAccountId(String.valueOf(random.nextInt(1000) + 1000));

            //check for a dup bank account
            for (BankAccount i : bank) {
                if (!i.getAccountId().contains(newAccount.getAccountId()))
                   isDup = false;
                break;
            }
        }while(isDup);
        System.out.println("your account number is: " + newAccount.getAccountId());

        bank.add(newAccount);
        //update .txt
        updateAccounts();
        fileAddNewAccount();
    }

    public void withdraw(int amount ,BankAccount account)
    {
        //check to see if user account has sufficient funds
        // subtract from account
        if (account.getBalance() != 0 || (account.getBalance() - amount) > 0)
            account.setBalance(account.getBalance()- amount);
        else
            System.out.println("Not Enough Funds ");

    }
    public void deposit(int amount,BankAccount account)
    {
        // check to see if its a positive number
        // add amount to account
        if (amount < 0)
            System.out.println("Can not deposit a value less than zero");
        else
        account.setBalance(account.getBalance() + amount);

    }
    public boolean intChecker( String number){

        try{
            Integer.parseInt(number);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public int userIntInput(String prompt){
        String number;
        System.out.print(prompt);
        while (true)
        {
           number = userInput.next();
           if(intChecker(number) == true)
               break;
            System.out.println("cannot contain letters.");
        }

        return Integer.parseInt(number);
    }
}


