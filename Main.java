import  java.util.Scanner;
import java.util.Random;

class Account{
    private String accountHolder;
    int accountNumber;
    private int  accountBalance;
    public Account(String accountHolder, int accountNumber, int accountBalance){
        this.accountBalance=accountBalance;
        this.accountHolder=accountHolder;
        this.accountNumber =accountNumber;
    }
    String accountInformation(){
        return "account name: "+this.accountHolder +"\n" +"account Balance: "+this.accountBalance;
    }
    String deposit(int amount){
        this.accountBalance += amount;
        return "Successfully deposited kes "+amount+" New balance is Kes "+this.accountBalance;
    }
    String withdraw (int amount){
        if (this.accountBalance < amount){
            return "Failed Insufficient Balance in your account to withdraw kes "+this.accountBalance;
        }
        this.accountBalance -= amount;
        return "Successfully Withdrawn kes "+amount+" New balance is Kes "+this.accountBalance;
    }
    String checkBalance(){
        return "Your balance is kes "+ this.accountBalance;
    }
    int getBalance(){
        return this.accountBalance;
    }
    String setAccountHolder(String name){
        this.accountHolder =name;
        return "Successfully Changed the Account Holders name";
    }
}


class Bank{ Account currentAccount;
   String bankName;

    public Bank(String bankName){
        //constructor method
        this.bankName=bankName;
    }
    public String CreateAccount(int accountNumber, String accountName,int initialAmount){
        currentAccount =new Account(accountName,accountNumber,initialAmount);
        return "Welcome "+accountName+" you have successfully opened an account your balance is kes "+currentAccount.getBalance();
    }
    public String performTransaction(int accountNumber, String transactionType, int amount){
        if (accountNumber == currentAccount.accountNumber){
            return switch (transactionType) {
                case ("deposit") -> (currentAccount.deposit(amount));
                case ("withdraw") -> (currentAccount.withdraw((amount)));
                default -> "Kindly specify a correct transaction type";
            };

        }
        else {
            return "Account does not exist create account first";
        }
    }
    public String Balance(int accountNumber){
        if (currentAccount.accountNumber==accountNumber) {
            return  (currentAccount.checkBalance());
        }
        else{
            return " Account does not exist kindly create account first";
        }

    }
    public String account_info(){
        return currentAccount.accountInformation();
    }
}
public class Main{
    public static void main(String[] args){
        Scanner scanner= new Scanner(System.in);
        Random random = new Random();
        Bank bank =new Bank("JavaBank");

        System.out.println("Welcome to "+bank.bankName +" Please Create an account to continue");
        System.out.println("Provide your full Names");
        String accountName= scanner.nextLine();
        int accountNumber = 1000000+random.nextInt(900000);//Generate a random six-digit number that I can use as the account Number
        int initialBalance=0;
        System.out.println(bank.CreateAccount(accountNumber,accountName,initialBalance));
        boolean exit =false;
        do{
        System.out.println("Choose any of the following options \n 1. Deposit \n 2. Withdraw \n 3. Check Balance \n 4. Get account information \n 5. Exit\n");
         int choice = scanner.nextInt();
        switch (choice){
            case (1):
                boolean valid =false;
                while (! valid){
                System.out.println("Please Enter the amount of money you wish to deposit: ");

                try {
                    int amount = scanner.nextInt();
                    System.out.println(bank.performTransaction(accountNumber,"deposit",amount)+"\n");
                    valid=true;

                }
                catch (Exception e){
                    System.out.println("Please enter an Integer");
                    scanner.next();
                }
                }
            break;
            case(2):
                System.out.println("Please enter the amount of money you wish to withdraw");
                int depositAmount =scanner.nextInt();
                System.out.println(bank.performTransaction(accountNumber,"withdraw",depositAmount)+"\n");
                break;
            case(3):
                System.out.println(bank.Balance(accountNumber)+"\n");
                break;
            case (4):
                System.out.println(bank.account_info());
            case(5):
                exit=true;
                break;
        }

        }while (! exit);
    }
}



