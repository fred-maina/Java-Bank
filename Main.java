import  java.util.Scanner;
import java.util.Random;

class Account{
    String accountHolder;
    int accountNumber;
    int accountBalance;
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
}
class Bank{
    Account currentAccount;
   String bankName;

    public Bank(String bankName){
        //constructor method
        this.bankName=bankName;
    }
    public String CreateAccount(int accountNumber, String accountName,int initialAmount){
        currentAccount =new Account(accountName,accountNumber,initialAmount);
        return "Welcome "+accountName+" you have successfully opened an account your balance is kes "+currentAccount.accountBalance;
    }
    public String performTransaction(int accountNumber, String transactionType, int amount){
        if (accountNumber == currentAccount.accountNumber){
            switch (transactionType){
                case ("deposit"):
                    return(currentAccount.deposit(amount));
                case ("withdraw"):
                    return (currentAccount.withdraw((amount)));
                default:
                    return "Kindly specify a correct transaction type";
            }

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
        do{
        System.out.println("Choose any of the following options \n 1. Deposit \n 2. Withdraw \n 3. Check Balance \n 4. Exit\n");
         int choice = scanner.nextInt();
        switch (choice){
            case (1):
                System.out.println("Please Enter the amount of money you wish to deposit: ");
                 int amount = scanner.nextInt();
                System.out.println(bank.performTransaction(accountNumber,"deposit",amount)+"\n");
                break;
            case(2):
                System.out.println("Please enter the amount of money you wish to withdraw");
                int depo_amount =scanner.nextInt();
                System.out.println(bank.performTransaction(accountNumber,"withdraw",depo_amount)+"\n");
                break;
            case(3):
                System.out.println(bank.Balance(accountNumber)+"\n");
                break;


        }

        }while (true);







    }
}



